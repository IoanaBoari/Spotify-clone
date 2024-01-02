package globalwaves.user.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.admin.delete.DeletePodcast;
import globalwaves.commands.Command;

public final class RemovePodcast implements Command {
    public RemovePodcast() {

    }

    /**
     * Executes the provided action, deleting a podcast for a host user
     * and generating a response message.
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is a host, updates statistics for all users,
     * and attempts to delete the podcast with the specified name.
     * Generates a success or failure message based on the deletion result.
     * If the user is not found, not a host, or has no podcast with the given name,
     * an appropriate error message is generated.
     *
     * @param action The action input specifying the user, command details, and podcast name.
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
            if (!currentUser.getType().equals("host")) {
                object.put("message",  action.getUsername() + " is not a host.");
            } else {
                Host host = (Host) currentUser;
                new UpdateStats().doUpdateAll(action);
                DeletePodcast delete = new DeletePodcast();
                int aux = 0;
                for (PodcastInput podcast : host.getPodcasts()) {
                    if (podcast.getName().equals(action.getName())) {
                        boolean remove = delete.deletePodcast(podcast);
                        if (remove) {
                            object.put("message",   action.getUsername()
                                    + " deleted the podcast successfully.");
                        } else {
                            object.put("message",
                                    action.getUsername() + " can't delete this podcast.");
                        }
                        aux = 1;
                        break;
                    }
                }
                if (aux == 0) {
                    object.put("message", action.getUsername()
                            + " doesn't have a podcast with the given name.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
