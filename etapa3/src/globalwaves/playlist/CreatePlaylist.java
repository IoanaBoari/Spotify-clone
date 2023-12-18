package globalwaves.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;

import static globalwaves.Menu.getOutputs;

public final class CreatePlaylist {
    private PlaylistsOwner playlistsOwner;
    public CreatePlaylist(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }

    /**
     * Outputs a message and creates a new playlist with the specified name in the user's playlists
     * if no playlist with the same name already exists.
     *
     * @param action The ActionInput representing the user's action.
     * @param outputs The ArrayNode containing the output messages.
     * @return The updated PlaylistsOwner object.
     */
    public PlaylistsOwner doOutput(final ActionInput action, final ArrayNode outputs) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            int aux = 0;
            if (this.playlistsOwner != null) {
                for (Playlist playlist : playlistsOwner.getPlaylists()) {
                    if (playlist.getName().equals(action.getPlaylistName())) {
                        object.put("message", "A playlist with the same name already exists.");
                        aux = 1;
                        break;
                    }
                }
            }
            if (aux == 0) {
                Playlist newPlaylist = new Playlist(action);
                this.playlistsOwner.getPlaylists().add(newPlaylist);
                object.put("message", "Playlist created successfully.");
            }
        }
        Menu.setOutputs(getOutputs().add(object));
        return this.playlistsOwner;
    }
}
