package globalwaves.user.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;


import java.util.HashMap;
import java.util.Map;

public final class AddAlbum implements Command {
    public AddAlbum() {

    }

    /**
     * Executes the provided action, adding a new album for an artist
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is an artist, adds a new album for the artist user.
     * If the user is not found, not an artist, or already has an album
     * with the same name, an appropriate error message is generated.
     * Additionally, checks for duplicate songs in the provided songs list.
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
                for (Album album : artist.getAlbums()) {
                    if (album.getName().equals(action.getName())) {
                        object.put("message", action.getUsername()
                                + " has another album with the same name.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                Map<String, Integer> songCount = new HashMap<>();
                for (SongInput song : action.getSongs()) {
                    String songName = song.getName();
                    songCount.put(songName, songCount.getOrDefault(songName, 0) + 1);
                }
                boolean existsAtLeastTwice = songCount.values().stream().
                        anyMatch(count -> count >= 2);
                if (existsAtLeastTwice) {
                    object.put("message", action.getUsername()
                            + " has the same song at least twice in this album.");
                } else {
                    Album newAlbum = new Album(action);
                    artist.getAlbums().add(newAlbum);
                    Database.getInstance().getAlbums().add(newAlbum);
                    object.put("message", action.getUsername()
                            + " has added new album successfully.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
