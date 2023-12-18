package globalwaves.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.SongInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.LoadResults;

public final class AddRemoveInPlaylist {
    private PlaylistsOwner playlistsOwner;

    public PlaylistsOwner getPlaylistsOwner() {
        return playlistsOwner;
    }

    public void setPlaylistsOwner(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }
    public AddRemoveInPlaylist(final PlaylistsOwner playlistsOwner) {
        this.playlistsOwner = playlistsOwner;
    }
    public AddRemoveInPlaylist() {
        this.playlistsOwner = new PlaylistsOwner();
    }

    /**
     * Outputs a message based on the action, last action, and load results, indicating the success
     * or failure of adding or removing a song from a playlist.
     *
     * @param action The ActionInput representing the user's action.
     * @param outputs The ArrayNode containing the output messages.
     * @param lastAction The last executed action.
     * @param loadResults The LoadResults containing information about the loaded source.
     */
    public void doOutput(final ActionInput action, final ArrayNode outputs,
                         final String lastAction, final LoadResults loadResults) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            if (!lastAction.equals("load")) {
                object.put("message",
                        "Please load a source before adding to or removing from the playlist.");
            } else {
                if (this.playlistsOwner.getPlaylists().isEmpty()
                        || this.playlistsOwner.getPlaylists().size() < action.getPlaylistId()) {
                    object.put("message", "The specified playlist does not exist.");
                } else {
                    if (loadResults.getLoadedSong() == null
                            || loadResults.getLoadedSong().getName() == null) {
                        object.put("message", "The loaded source is not a song.");
                    } else {
                        if (loadResults.getLoadedSong() != null
                                && loadResults.getLoadedSong().getName() != null) {
                            Playlist playlist = this.playlistsOwner.
                                    getPlaylists().get(action.getPlaylistId() - 1);
                            int aux = 0;
                            for (SongInput song : playlist.getSongs()) {
                                if (song.equals(loadResults.getLoadedSong())) {
                                    aux = 1;
                                    break;
                                }
                            }
                            if (aux == 0) {
                                this.playlistsOwner.getPlaylists().get(action.getPlaylistId() - 1).
                                        getSongs().add(loadResults.getLoadedSong());
                                object.put("message", "Successfully added to playlist.");
                            } else {
                                this.playlistsOwner.getPlaylists().get(action.getPlaylistId() - 1).
                                        getSongs().remove(loadResults.getLoadedSong());
                                object.put("message", "Successfully removed from playlist.");
                            }
                        }
                    }
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
