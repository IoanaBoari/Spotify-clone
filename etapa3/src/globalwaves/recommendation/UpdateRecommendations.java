package globalwaves.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.commands.Command;

public final class UpdateRecommendations implements Command {
    public UpdateRecommendations() {
    }

    /**
     * Processes a user's request to update recommendations based on different recommendation types
     * If the user exists and is a normal user, updates recommendations based on the specified type
     * (random_song, random_playlist, or fans_playlist) and generates a success
     * or no new recommendations message.
     * If the user is not found or is not a normal user, an appropriate message is provided.
     *
     * @param action The ActionInput containing information necessary for executing the command.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                break;
            }
        }
        if (currentUser == null) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            if (!currentUser.getType().equals("user")) {
                object.put("message", action.getUsername() + " is not a normal user.");
            } else {
                new UpdateStats().doUpdateCurrent(action);
                if (action.getRecommendationType().equals("random_song")) {
                    boolean result = new RandomSong().updateRecommendations(action);
                    if (result) {
                        object.put("message", "The recommendations for user " + action.getUsername()
                                + " have been updated successfully.");
                    } else {
                        object.put("message", "No new recommendations were found");
                    }
                } else if (action.getRecommendationType().equals("random_playlist")) {
                    boolean result = new RandomPlaylist().updateRecommendations(action);
                    if (result) {
                        object.put("message", "The recommendations for user " + action.getUsername()
                                + " have been updated successfully.");
                    } else {
                        object.put("message", "No new recommendations were found");
                    }
                } else if (action.getRecommendationType().equals("fans_playlist")) {
                    boolean result = new FansPlaylist().updateRecommendations(action);
                    if (result) {
                        object.put("message", "The recommendations for user " + action.getUsername()
                                + " have been updated successfully.");
                    } else {
                        object.put("message", "No new recommendations were found");
                    }
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
