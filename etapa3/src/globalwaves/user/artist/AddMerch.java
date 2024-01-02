package globalwaves.user.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

public final class AddMerch implements Command {
    public AddMerch() {

    }

    /**
     * Executes the provided action, adding new merchandise for an artist
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is an artist, adds new merchandise for the artist user.
     * If the user is not found, not an artist, or already has merchandise with the same name,
     * an appropriate error message is generated. Additionally, validates that the price
     * for merchandise is not negative.
     *
     * @param action The action input specifying the user,
     *               command details, and merchandise information.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        int find = 0;
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                find = 1;
                break;
            }
        }
        if (find == 0) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            if (!currentUser.getType().equals("artist")) {
                object.put("message", action.getUsername() + " is not an artist.");
            } else {
                Artist artist = (Artist) currentUser;
                for (Merch merch : artist.getMerch()) {
                    if (merch.getName().equals(action.getName())) {
                        object.put("message", action.getUsername()
                                + " has merchandise with the same name.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                if (action.getPrice() > 0) {
                    Merch newMerch = new Merch(action);
                    artist.getMerch().add(newMerch);
                    object.put("message", action.getUsername()
                            + " has added new merchandise successfully.");
                } else {
                    object.put("message", "Price for merchandise can not be negative.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
