package globalwaves.monetization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.commands.Command;
import globalwaves.player.LoadResults;

public final class AdBreak implements Command {
    public AdBreak() {
    }

    /**
     *
     * @param action The action input containing information necessary for executing the command.
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
            new UpdateStats().doUpdateCurrent(action);
            for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
                if (loadResults.getUsername().equals(currentUser.getUsername())
                        && (loadResults.getLoadedSong() != null
                        || loadResults.getLoadedPlaylist() != null
                        || loadResults.getLoadedAlbum() != null)) {
                    object.put("message", "Ad inserted successfully.");
                    Menu.setOutputs(Menu.getOutputs().add(object));
                    return;
                }
            }
            object.put("message", action.getUsername() + " is not playing any music.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
