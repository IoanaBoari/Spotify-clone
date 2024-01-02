package globalwaves.user.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

public final class AddAnnouncement implements Command {
    public AddAnnouncement() {
    }

    /**
     * Adds a new announcement for a host user based on the provided action
     * and generates a response message.
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is a host, a new announcement is added.
     * If the user is not found, not a host, or already has an announcement with the same name,
     * an appropriate error message is generated.
     *
     * @param action The action input specifying the user, command details,
     *               and announcement information.
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
                        object.put("message", action.getUsername()
                                + " has already added an announcement with this name.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                Announcement newAnnouncement = new Announcement(action);
                host.getAnnouncements().add(newAnnouncement);
                object.put("message", action.getUsername()
                        + " has successfully added new announcement.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
