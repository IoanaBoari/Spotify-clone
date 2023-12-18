package globalwaves.userstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.SongInput;
import globalwaves.Menu;
import globalwaves.player.LikedSongs;

import java.util.ArrayList;

public final class ShowPreferredSongs {
    public ShowPreferredSongs() {

    }

    /**
     * Generates output for the current user's preferred songs.
     *
     * @param action The ActionInput representing the user action.
     * @param likedSongs The LikedSongs object containing information about the user's liked songs.
     */
    public void doOutput(final ActionInput action, final LikedSongs likedSongs) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> songsName = new ArrayList<>();
        if (likedSongs.getLikedSongs().isEmpty()) {
            object.put("result", objectMapper.valueToTree(songsName));
        } else {
            for (SongInput song : likedSongs.getLikedSongs()) {
                songsName.add(song.getName());
            }
            object.put("result", objectMapper.valueToTree(songsName));
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
