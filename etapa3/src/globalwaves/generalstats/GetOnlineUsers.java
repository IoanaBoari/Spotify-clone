package globalwaves.generalstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

import java.util.ArrayList;

public final class GetOnlineUsers implements Command {
    public GetOnlineUsers() {

    }
    /**
     * Executes the provided action, generating a response message
     * containing a list of online usernames
     * with the user type "user" in the system.
     *
     * @param action The action input specifying the user, command details, and the timestamp.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> results = new ArrayList<>();
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getType().equals("user") && user.getMode().equals("online")) {
                results.add(user.getUsername());
            }
        }
        object.put("result",  objectMapper.valueToTree(results));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }

}
