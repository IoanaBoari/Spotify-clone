package globalwaves.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

import java.util.ArrayList;

public final class ShowPodcasts implements Command {
    public ShowPodcasts() {

    }

    /**
     * Executes the "showPodcasts" command based on the provided action input. This method
     * generates a JSON response containing information about the podcasts associated with
     * the current host user and adds it to the outputs in the Menu.
     *
     * @param action The ActionInput object containing information for executing the command,
     *               including the command type, username, and timestamp.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        ArrayList<ShowPodcastResult> showPodcastResults = new ArrayList<>();
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                break;
            }
        }
        if (currentUser != null && currentUser.getType().equals("host")) {
            Host currentHost = (Host) currentUser;
            for (PodcastInput podcast : currentHost.getPodcasts()) {
                ShowPodcastResult showPodcastResult = new ShowPodcastResult(podcast);
                showPodcastResults.add(showPodcastResult);
            }
        }
        object.put("result", objectMapper.valueToTree(showPodcastResults));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
