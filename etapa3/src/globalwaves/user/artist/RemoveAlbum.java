package globalwaves.user.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.Artist;
import globalwaves.admin.delete.DeleteAlbum;
import globalwaves.commands.Command;

import javax.swing.*;

public final class RemoveAlbum implements Command {
    public RemoveAlbum() {
    }

    /**
     * Executes the provided action, deleting an album for an artist
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is an artist, deletes the specified album for the artist user.
     * If the user is not found or not an artist, or if the specified album is not found
     * for the artist, an appropriate error message is generated.
     *
     * @param action The action input specifying the user, command details, and album information.
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
            if (!currentUser.getType().equals("artist")) {
                object.put("message",  action.getUsername() + " is not an artist.");
            } else {
                Artist artist = (Artist) currentUser;
                DeleteAlbum delete = new DeleteAlbum();
                int aux = 0;
                for (Album album : artist.getAlbums()) {
                    if (album.getName().equals(action.getName())) {
                        boolean remove = delete.deleteAlbum(album);
                        if (remove) {
                            object.put("message",   action.getUsername()
                                    + " deleted the album successfully.");
                        } else {
                            object.put("message",
                                    action.getUsername() + " can't delete this album.");
                        }
                        aux = 1;
                        break;
                    }
                }
                if (aux == 0) {
                    object.put("message", action.getUsername()
                            + " doesn't have an album with the given name.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
