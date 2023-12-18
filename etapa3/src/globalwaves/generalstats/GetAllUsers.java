package globalwaves.generalstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

import java.util.ArrayList;

public final class GetAllUsers implements Command {
    public GetAllUsers() {

    }

    /**
     * Executes the provided action, generating a response message containing a list of usernames
     * based on their types (user, artist, host) in the system.
     *
     * @param action The action input specifying the user and command details
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> results = new ArrayList<>();
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getType().equals("user")) {
                results.add(user.getUsername());
            }
        }
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getType().equals("artist")) {
                results.add(user.getUsername());
            }
        }
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getType().equals("host")) {
                results.add(user.getUsername());
            }
        }
        object.put("result",  objectMapper.valueToTree(results));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
