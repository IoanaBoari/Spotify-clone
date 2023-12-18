package globalwaves.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;

public final class Like {
    private LikedSongs songs = new LikedSongs();
    public LikedSongs getLikedSongs() {
        return songs;
    }
    public void setLikedSongs(final LikedSongs likedSongs) {
        this.songs = likedSongs;
    }
    public Like() {

    }
    public Like(final LikedSongs likedSongs) {
        this.songs = likedSongs;
    }

    /**
     * Handles the output for liking or unliking a song during the load operation.
     *
     * @param action      The action input specifying the command, user, and timestamp.
     * @param outputs     The array node containing output objects.
     * @param lastAction  The last action performed by the current user.
     * @param loadResults The results of the load operation.
     * @return The updated load results after processing the output.
     */
    public LoadResults doOutput(final ActionInput action, final ArrayNode outputs,
                         final String lastAction, final LoadResults loadResults) {
        this.songs.setUser(action.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        int auxiliar = 0;
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getType().equals("user") && user.getMode().equals("offline")) {
                object.put("message", action.getUsername() + " is offline.");
                auxiliar = 1;
                break;
            }
        }
        if (auxiliar == 0) {
            if (!lastAction.equals("load")) {
                object.put("message", "Please load a source before liking or unliking.");
            } else {
                if ((loadResults.getLoadedSong() == null
                        || loadResults.getLoadedSong().getName() == null)
                        && (loadResults.getLoadedPlaylist() == null
                        || loadResults.getLoadedPlaylist().getName() == null)
                        && (loadResults.getLoadedAlbum() == null
                        || loadResults.getLoadedAlbum().getName() == null)) {
                    object.put("message", "Loaded source is not a song.");
                } else {
                    if (loadResults.getLoadedSong() != null) {
                        int aux = 0;
                        if (this.songs.getLikedSongs() != null) {
                            for (SongInput song : this.songs.getLikedSongs()) {
                                if (song.getName().equals(loadResults.getLoadedSong().getName())) {
                                    aux = 1;
                                    break;
                                }
                            }
                            if (aux == 0) {
                                loadResults.getLoadedSong().setLikes(loadResults.
                                        getLoadedSong().getLikes() + 1);
                                this.songs.getLikedSongs().add(loadResults.getLoadedSong());
                                object.put("message", "Like registered successfully.");
                            } else {
                                loadResults.getLoadedSong().setLikes(loadResults.
                                        getLoadedSong().getLikes() - 1);
                                this.songs.getLikedSongs().remove(loadResults.getLoadedSong());
                                object.put("message", "Unlike registered successfully.");
                            }
                        } else {
                            loadResults.getLoadedSong().setLikes(loadResults.
                                    getLoadedSong().getLikes() + 1);
                            this.songs.getLikedSongs().add(loadResults.getLoadedSong());
                            object.put("message", "Like registered successfully.");
                        }
                    } else if (loadResults.getLoadedPlaylist() != null) {
                        int aux = 0;
                        int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                        if (this.songs.getLikedSongs() != null) {
                            for (SongInput song : this.songs.getLikedSongs()) {
                                if (song.getName().equals(loadResults.getLoadedPlaylist().
                                        getSongs().get(idx).getName())) {
                                    aux = 1;
                                    break;
                                }
                            }
                            if (aux == 0) {
                                loadResults.getLoadedPlaylist().getSongs().get(idx).
                                        setLikes(loadResults.getLoadedPlaylist().getSongs().
                                                get(idx).getLikes() + 1);
                                this.songs.getLikedSongs().
                                        add(loadResults.getLoadedPlaylist().getSongs().get(idx));
                                object.put("message", "Like registered successfully.");
                            } else {
                                loadResults.getLoadedPlaylist().getSongs().get(idx).setLikes(
                                        loadResults.getLoadedPlaylist().getSongs().
                                                get(idx).getLikes() - 1);
                                this.songs.getLikedSongs().
                                        remove(loadResults.getLoadedPlaylist().getSongs().get(idx));
                                object.put("message", "Unlike registered successfully.");
                            }
                        } else {
                            loadResults.getLoadedPlaylist().getSongs().get(idx).
                                    setLikes(loadResults.getLoadedPlaylist().
                                            getSongs().get(idx).getLikes() + 1);
                            this.songs.getLikedSongs().
                                    add(loadResults.getLoadedPlaylist().getSongs().get(idx));
                            object.put("message", "Like registered successfully.");
                        }
                    } else if (loadResults.getLoadedAlbum() != null) {
                        int aux = 0;
                        int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
                        if (this.songs.getLikedSongs() != null) {
                            for (SongInput song : this.songs.getLikedSongs()) {
                                if (song.getName().equals(loadResults.getLoadedAlbum().
                                        getSongs().get(idx).getName())) {
                                    aux = 1;
                                    break;
                                }
                            }
                            if (aux == 0) {
                                loadResults.getLoadedAlbum().getSongs().get(idx).
                                        setLikes(loadResults.getLoadedAlbum().getSongs().
                                                get(idx).getLikes() + 1);
                                this.songs.getLikedSongs().
                                        add(loadResults.getLoadedAlbum().getSongs().get(idx));
                                object.put("message", "Like registered successfully.");
                            } else {
                                loadResults.getLoadedAlbum().getSongs().get(idx).setLikes(
                                        loadResults.getLoadedAlbum().getSongs().
                                                get(idx).getLikes() - 1);
                                this.songs.getLikedSongs().
                                        remove(loadResults.getLoadedAlbum().getSongs().get(idx));
                                object.put("message", "Unlike registered successfully.");
                            }
                        } else {
                            loadResults.getLoadedAlbum().getSongs().get(idx).
                                    setLikes(loadResults.getLoadedAlbum().
                                            getSongs().get(idx).getLikes() + 1);
                            this.songs.getLikedSongs().
                                    add(loadResults.getLoadedAlbum().getSongs().get(idx));
                            object.put("message", "Like registered successfully.");
                        }
                    }
                }
            }
        }
        loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
