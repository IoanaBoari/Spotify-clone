package globalwaves.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;

import static globalwaves.Menu.getOutputs;

public final class SwitchVisibility {
    private PlaylistsOwner playlistsOwner;

    public PlaylistsOwner getPlaylistsOwner() {
        return playlistsOwner;
    }

    public void setPlaylistsOwner(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }

    public SwitchVisibility(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }
    public SwitchVisibility() {

    }
    /**
     * Updates the visibility status of a specified playlist based on the provided action.
     *
     * @param action   The action containing information about the playlist and the user.
     * @param outputs  The ArrayNode where the output results will be added.
     */
    public void doOutput(final ActionInput action, final ArrayNode outputs) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            if (this.playlistsOwner == null
                    || this.playlistsOwner.getPlaylists().size() < action.getPlaylistId()) {
                object.put("message", "The specified playlist ID is too high.");
            } else {
                String visibility = this.playlistsOwner.getPlaylists().
                        get(action.getPlaylistId() - 1).getVisibility();
                if (visibility.equals("public")) {
                    this.playlistsOwner.getPlaylists().
                            get(action.getPlaylistId() - 1).setVisibility("private");
                } else {
                    this.playlistsOwner.getPlaylists().
                            get(action.getPlaylistId() - 1).setVisibility("public");
                }
                visibility = this.playlistsOwner.getPlaylists().
                        get(action.getPlaylistId() - 1).getVisibility();
                object.put("message",
                        "Visibility status updated successfully to " + visibility + ".");
            }
        }
        Menu.setOutputs(getOutputs().add(object));
    }
}
