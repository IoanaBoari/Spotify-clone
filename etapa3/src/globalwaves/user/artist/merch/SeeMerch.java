package globalwaves.user.artist.merch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

import java.util.ArrayList;

public final class SeeMerch implements Command {
    public SeeMerch() {
    }

    /**
     * Executes the command to retrieve and display a user's owned merchandise names.
     *
     * @param action The ActionInput containing information necessary for executing the command.
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
            ArrayList<String> merchNames = new ArrayList<>();
            for (OwnedMerch ownedMerch : Database.getInstance().getOwnedMerchArrayList()) {
                if (ownedMerch.getUsername().equals(action.getUsername())) {
                    for (Merch merch : ownedMerch.getOwnedmerchandise()) {
                        merchNames.add(merch.getName());
                    }
                }
            }
            object.put("result", objectMapper.valueToTree(merchNames));
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
