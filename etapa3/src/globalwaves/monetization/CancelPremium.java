package globalwaves.monetization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

public final class CancelPremium implements Command {
    public CancelPremium() {
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
            for (UserInput premiumUser: Database.getInstance().getPremiumUsers()) {
                if (premiumUser.getUsername().equals(currentUser.getUsername())) {
                    Database.getInstance().getPremiumUsers().remove(currentUser);
                    object.put("message", currentUser.getUsername()
                            + " cancelled the subscription successfully.");
                    Menu.setOutputs(Menu.getOutputs().add(object));
                    return;
                }
            }
            object.put("message", currentUser.getUsername() + " is not a premium user.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
