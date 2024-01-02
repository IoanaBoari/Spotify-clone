package globalwaves.admin.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.commands.Command;

public final class DeleteUser implements Command {
    public DeleteUser() {

    }

    /**
     * Executes the delete operation based on the user type (normal user, artist, host).
     *
     * @param action The ActionInput containing user information.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        UserInput deleteUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                deleteUser = userInput;
                break;
            }
        }
        if (deleteUser == null) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            new UpdateStats().doUpdateAll(action);
            DeleteNormalArtistHost delete = new DeleteNormalArtistHost();
            if (deleteUser.getType().equals("user")) {
                boolean delNormal = delete.deleteNormalUser(deleteUser);
                if (delNormal) {
                    object.put("message",   action.getUsername() + " was successfully deleted.");
                } else {
                    object.put("message",   action.getUsername() + " can't be deleted.");
                }
            } else if (deleteUser.getType().equals("artist")) {
                boolean delArtist = delete.deleteArtist(deleteUser);
                if (delArtist) {
                    object.put("message",   action.getUsername() + " was successfully deleted.");
                } else {
                    object.put("message",   action.getUsername() + " can't be deleted.");
                }
            } else if (deleteUser.getType().equals("host")) {
                boolean delHost = delete.deleteHost(deleteUser);
                if (delHost) {
                    object.put("message",   action.getUsername() + " was successfully deleted.");
                } else {
                    object.put("message",   action.getUsername() + " can't be deleted.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
