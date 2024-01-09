package globalwaves.pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.recommendation.UserPages;
import globalwaves.searchbar.SelectResults;

public final class NextPage implements Command {
    public NextPage() {
    }

    /**
     * Processes a user's request to navigate to the next page.
     * If the user exists and there are more pages to navigate forward,
     * updates the user's current page and generates a success message.
     * If the user is not found or there are no more pages to navigate,
     * an appropriate message is provided.
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
            SelectResults currentPage = new SelectResults();
            for (UserPages userPages : Database.getInstance().getAllPages()) {
                if (userPages.getUsername().equals(action.getUsername())) {
                    if (userPages.getPageIdx() < userPages.getPages().size() - 1) {
                        userPages.setPageIdx(userPages.getPageIdx() + 1);
                        currentPage = userPages.getPages().get(userPages.getPageIdx());
                        currentUser.setCurrentPage(currentPage.getLastCommand());
                    } else {
                        object.put("message", "There are no pages left to go forward.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }

                }
            }
            for (SelectResults selectResults : Database.getInstance().getCurrentPages()) {
                if (selectResults.getUsername().equals(action.getUsername())) {
                    Database.getInstance().getCurrentPages().remove(selectResults);
                    break;
                }
            }
            Database.getInstance().getCurrentPages().add(currentPage);
            object.put("message", "The user " + action.getUsername()
                    + " has navigated successfully to the next page.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
