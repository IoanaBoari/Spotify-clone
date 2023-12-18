package globalwaves.user.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.Host;
import globalwaves.commands.Command;

public final class RemoveAnnouncement implements Command {
    public RemoveAnnouncement() {

    }

    /**
     * Executes the provided action, deleting an announcement for a host user
     * and generating a response message.
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is a host, deletes the announcement with the specified name.
     * If the user is not found, not a host, or has no announcement with the given name,
     * an appropriate error message is generated.
     *
     * @param action The action input specifying the user, command details, and announcement name.
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
                object.put("message", action.getUsername() + " is not a host.");
            } else {
                Host host = (Host) currentUser;
                for (Announcement announcement : host.getAnnouncements()) {
                    if (announcement.getName().equals(action.getName())) {
                        host.getAnnouncements().remove(announcement);
                        object.put("message", action.getUsername()
                                + " has successfully deleted the announcement.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                object.put("message", action.getUsername()
                        + " has no announcement with the given name.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
