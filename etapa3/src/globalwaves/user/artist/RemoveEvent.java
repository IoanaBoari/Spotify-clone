package globalwaves.user.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.Artist;
import globalwaves.commands.Command;

public final class RemoveEvent implements Command {
    public RemoveEvent() {
    }

    /**
     * Executes the provided action, deleting an event for an artist
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is an artist, deletes the specified event for the artist user.
     * If the user is not found or not an artist, or if
     * the specified event is not found for the artist, an appropriate error message is generated.
     *
     * @param action The action input specifying the user, command details, and event information.
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
            if (!currentUser.getType().equals("artist")) {
                object.put("message", action.getUsername() + " is not an artist.");
            } else {
                Artist artist = (Artist) currentUser;
                for (Event event : artist.getEvents()) {
                    if (event.getName().equals(action.getName())) {
                        artist.getEvents().remove(event);
                        object.put("message", action.getUsername()
                                + " deleted the event successfully.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                object.put("message", action.getUsername()
                        + " doesn't have an event with the given name.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
