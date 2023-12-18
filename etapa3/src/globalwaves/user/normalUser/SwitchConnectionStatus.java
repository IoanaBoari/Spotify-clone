package globalwaves.user.normalUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.commands.Command;

public final class SwitchConnectionStatus implements Command {
    public SwitchConnectionStatus() {

    }

    /**
     * Executes the provided action, updating user status and generating a response message.
     *
     * The method checks if the username in the action exists in the library's users.
     * If the user is found, it updates their online/offline status and sends a success message.
     * If the user is not found or has a role other than "user,"
     * an appropriate error message is generated.
     *
     *
     * @param action The action input specifying the user and command details.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        int aux = 0;
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                aux = 1;
            }
        }
        if (aux == 0) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            if (!currentUser.getType().equals("user")) {
                object.put("message", action.getUsername() + " is not a normal user.");
            } else {
                new UpdateStats().doUpdateCurrent(action);
                if (currentUser.getMode().equals("online")) {
                    currentUser.setMode("offline");
                } else {
                    currentUser.setMode("online");
                }
                object.put("message", action.getUsername() + " has changed status successfully.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
