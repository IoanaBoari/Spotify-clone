package globalwaves.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

public final class AddUser implements Command {
    public AddUser() {

    }

    /**
     * Executes the add user operation based on the user type (normal user, artist, host).
     *
     * @param action The ActionInput containing user information.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        int find = 0;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                find = 1;
                break;
            }
        }
        if (find == 1) {
            object.put("message", "The username " + action.getUsername() + " is already taken.");
        } else {
            if (action.getType().equals("user")) {
                UserInput newUser = new UserInput(action);
                Database.getInstance().getLibrary().getUsers().add(newUser);
            } else if (action.getType().equals("artist")) {
                Artist newArtist = new Artist(action);
                Database.getInstance().getLibrary().getUsers().add(newArtist);
            } else if (action.getType().equals("host")) {
                Host newHost = new Host(action);
                Database.getInstance().getLibrary().getUsers().add(newHost);
            }
            object.put("message", "The username " + action.getUsername()
                    + " has been added successfully.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}