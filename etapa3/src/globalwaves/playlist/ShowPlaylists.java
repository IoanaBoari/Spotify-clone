package globalwaves.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;

import java.util.ArrayList;

public final class ShowPlaylists {
    private PlaylistsOwner playlistsOwner;

    public PlaylistsOwner getPlaylistsOwner() {
        return playlistsOwner;
    }

    public void setPlaylistsOwner(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }
    public ShowPlaylists(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }
    public ShowPlaylists() {

    }

    /**
     * Displays all songs from all the playlists of the current user.
     *
     * @param action   The action triggering the output.
     * @param outputs  The ArrayNode containing outputs.
     */
    public void doOutput(final ActionInput action, final ArrayNode outputs) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        ArrayList<ShowResult> showResults = new ArrayList<>();
        for (Playlist playlist : this.playlistsOwner.getPlaylists()) {
            ShowResult newResult = new ShowResult(playlist);
            showResults.add(newResult);
        }
        object.put("result", objectMapper.valueToTree(showResults));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
