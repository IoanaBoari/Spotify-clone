package globalwaves.pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.commands.Command;
import globalwaves.searchbar.SelectResults;


public final class ChangePage implements Command {
    public ChangePage() {

    }

    /**
     * Executes the provided action, updating the user's current page
     * and generating a response message.
     * Checks if the user is offline using the CheckOffline class.
     * If the user is offline, returns an appropriate message.
     * Otherwise, updates the user's current page based on the provided action.
     * Handles specific cases for the "Home" and "LikedContent" pages.
     *
     * @param action The action input specifying the user, command details, and the next page.
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
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else if (action.getNextPage().equals("Home")
                || action.getNextPage().equals("LikedContent")) {
            currentUser.setCurrentPage(action.getNextPage());
            for (SelectResults results : Database.getInstance().getCurrentPages()) {
                if (results.getUsername().equals(currentUser.getUsername())) {
                    Database.getInstance().getCurrentPages().remove(results);
                    break;
                }
            }
            object.put("message", action.getUsername() + " accessed "
                    + action.getNextPage() + " successfully.");
        } else {
            object.put("message", action.getUsername()
                    + " is trying to access a non-existent page.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
