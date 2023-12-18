package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.Load;
import globalwaves.playlist.Playlist;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Album;


public final class LoadOutput {
    private SelectResults selectResults;
    private SongInput songLoaded;
    private PodcastInput podcastLoaded;
    private Playlist playlistLoaded;
    private Album albumLoaded;
    public SelectResults getSelectResults() {
        return selectResults;
    }

    public void setSelectResults(final SelectResults selectResults) {
        this.selectResults = selectResults;
    }

    public SongInput getSongLoaded() {
        return songLoaded;
    }

    public void setSongLoaded(final SongInput songLoaded) {
        this.songLoaded = songLoaded;
    }

    public PodcastInput getPodcastLoaded() {
        return podcastLoaded;
    }

    public void setPodcastLoaded(final PodcastInput podcastLoaded) {
        this.podcastLoaded = podcastLoaded;
    }

    public Playlist getPlaylistLoaded() {
        return playlistLoaded;
    }

    public void setPlaylistLoaded(final Playlist playlistLoaded) {
        this.playlistLoaded = playlistLoaded;
    }

    public Album getAlbumLoaded() {
        return albumLoaded;
    }

    public void setAlbumLoaded(final Album albumLoaded) {
        this.albumLoaded = albumLoaded;
    }

    public LoadOutput() {

    }
    public LoadOutput(final SelectResults selectResults) {
        this.setSelectResults(selectResults);
    }

    /**
     * Outputs a message indicating the success or failure
     * of loading a selected source for playback.
     *
     * @param action         The ActionInput object representing the user's action.
     * @param lastAction     The last action performed by the user.
     *
     */

    public void doOutput(final ActionInput action, final String lastAction) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            //object.put("lastAction",lastAction);
            if (!lastAction.equals("select")) {
                object.put("message", "Please select a source before attempting to load.");
            } else {
                Load load = new Load();
                if (this.selectResults.getSelectSong() != null
                        && this.selectResults.getSelectSong().getName() != null) {
                    this.songLoaded = load.doLoadSong(this.selectResults);
                    if (this.songLoaded == null) {
                        object.put("message", "You can't load an empty audio collection.");
                    } else {
                        object.put("message", "Playback loaded successfully.");
                    }
                } else if (this.selectResults.getSelectPodcast() != null
                        && this.selectResults.getSelectPodcast().getName() != null) {
                    this.podcastLoaded = load.doLoadPodcast(this.selectResults);
                    if (this.podcastLoaded == null) {
                        object.put("message", "You can't load an empty audio collection.");
                    } else {
                        object.put("message", "Playback loaded successfully.");
                    }
                } else if (this.selectResults.getSelectPlaylist() != null
                        && this.selectResults.getSelectPlaylist().getName() != null) {
                    this.playlistLoaded = load.doLoadPlaylist(this.selectResults);
                    if (this.playlistLoaded == null) {
                        object.put("message", "You can't load an empty audio collection.");
                    } else {
                        object.put("message", "Playback loaded successfully.");
                    }
                } else if (this.selectResults.getSelectAlbum() != null
                && this.selectResults.getSelectAlbum().getName() != null) {
                    this.albumLoaded = load.doLoadAlbum(this.selectResults);
                    if (this.albumLoaded == null) {
                        object.put("message", "You can't load an empty audio collection.");
                    } else {
                        object.put("message", "Playback loaded successfully.");
                    }
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
