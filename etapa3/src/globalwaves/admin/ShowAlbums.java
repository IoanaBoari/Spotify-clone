package globalwaves.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.user.artist.Album;

import java.util.ArrayList;

public final class ShowAlbums implements Command {
    public ShowAlbums() {

    }

    /**
     * Executes the "showAlbums" command based on the provided action input. This method
     * generates a JSON response containing information about the albums associated with
     * the current artist user and adds it to the outputs in the Menu.
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
        ArrayList<ShowAlbumResult> showAlbumResults = new ArrayList<>();
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                break;
            }
        }
        if (currentUser != null && currentUser.getType().equals("artist")) {
            Artist currentArtist = (Artist) currentUser;
            for (Album album : currentArtist.getAlbums()) {
                ShowAlbumResult showAlbumResult = new ShowAlbumResult(album);
                showAlbumResults.add(showAlbumResult);
            }
        }
        object.put("result", objectMapper.valueToTree(showAlbumResults));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
