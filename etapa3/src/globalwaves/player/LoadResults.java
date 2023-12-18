package globalwaves.player;

import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.playlist.Playlist;
import globalwaves.user.artist.Album;

public final class LoadResults {
    private SongInput loadedSong;
    private PodcastInput loadedPodcast;
    private Playlist loadedPlaylist;
    private Album loadedAlbum;
    private Integer timestamp;
    private String username;
    private String lastCommand;
    private Stats stats = new Stats(this);

    public SongInput getLoadedSong() {
        return loadedSong;
    }

    public void setLoadedSong(final SongInput loadedSong) {
        this.loadedSong = loadedSong;
    }

    public PodcastInput getLoadedPodcast() {
        return loadedPodcast;
    }

    public void setLoadedPodcast(final PodcastInput loadedPodcast) {
        this.loadedPodcast = loadedPodcast;
    }

    public Album getLoadedAlbum() {
        return loadedAlbum;
    }

    public void setLoadedAlbum(final Album loadedAlbum) {
        this.loadedAlbum = loadedAlbum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(final String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(final Stats stats) {
        this.stats = stats;
    }

    public Playlist getLoadedPlaylist() {
        return loadedPlaylist;
    }

    public void setLoadedPlaylist(final Playlist loadedPlaylist) {
        this.loadedPlaylist = loadedPlaylist;
    }

    /**
     * Constructs a new LoadResults object based on the provided parameters.
     *
     * @param action    The action input associated with the load operation.
     * @param song      The loaded song, or null if no song is loaded.
     * @param podcast   The loaded podcast, or null if no podcast is loaded.
     * @param playlist  The loaded playlist, or null if no playlist is loaded.
     * @param album     The loaded album, or null if no album is loaded
     */
    public LoadResults(final ActionInput action, final SongInput song,
                       final PodcastInput podcast, final Playlist playlist, final Album album) {
        this.loadedSong = song;
        this.loadedPodcast = podcast;
        this.loadedPlaylist = playlist;
        this.loadedAlbum = album;
        if (playlist != null) {
            this.loadedPlaylist.setCurrentSongIndex(0);
            if (!this.loadedPlaylist.getSongs().isEmpty()) {
                if (!this.loadedPlaylist.getInitialOrder().isEmpty()) {
                    this.loadedPlaylist.setSongs(this.loadedPlaylist.getInitialOrder());
                }
                this.stats.setRemainedTime(this.loadedPlaylist.getSongs().get(0).getDuration());
            }
        }
        if (podcast != null) {
            this.loadedPodcast.setCurrentEpisodeIndex(0);
            if (!this.loadedPodcast.getEpisodes().isEmpty()) {
                this.stats.setRemainedTime(this.loadedPodcast.getEpisodes().get(0).getDuration());
            }
        }
        if (album != null) {
            this.loadedAlbum.setCurrentSongIndex(0);
            if (!this.loadedAlbum.getSongs().isEmpty()) {
                if (!this.loadedAlbum.getInitialOrder().isEmpty()) {
                    this.loadedAlbum.setSongs(this.loadedAlbum.getInitialOrder());
                }
                this.stats.setRemainedTime(this.loadedAlbum.getSongs().get(0).getDuration());
            }
        }
        this.username = action.getUsername();
        this.lastCommand = action.getCommand();
        this.timestamp = action.getTimestamp();
        this.stats = new Stats(this);
    }
    public LoadResults() {

    }
    public LoadResults(final LoadResults copyResults) {
        this.loadedSong = copyResults.getLoadedSong();
        this.loadedPodcast = copyResults.getLoadedPodcast();
        this.loadedPlaylist = copyResults.getLoadedPlaylist();
        this.loadedAlbum = copyResults.getLoadedAlbum();
        this.timestamp = copyResults.getTimestamp();
        this.username = copyResults.getUsername();
        this.lastCommand = copyResults.getLastCommand();
        this.stats = copyResults.getStats();
    }
}
