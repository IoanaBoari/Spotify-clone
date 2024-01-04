package globalwaves.user.artist.merch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.searchbar.SelectResults;

public class BuyMerch implements Command {
    public BuyMerch() {
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
            SelectResults currentPage = null;
            for (SelectResults page : Database.getInstance().getCurrentPages()) {
                if (page.getUsername().equals(currentUser.getUsername())
                        && page.getSelectArtist() != null) {
                    currentPage = page;
                    break;
                }
            }
            if (currentPage == null) {
                object.put("message", "Cannot buy merch from this page.");
            } else {
                for (Merch merch : currentPage.getSelectArtist().getMerch()) {
                    if (merch.getName().equals(action.getName())) {
                        new OwnedMerch().addMerchandise(currentUser.getUsername(), merch);
                        object.put("message", currentUser.getUsername()
                                + " has added new merch successfully.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                object.put("message", "The merch " + action.getName() + " doesn't exist.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
