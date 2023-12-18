package globalwaves.searchbar;

import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.admin.Artist;
import globalwaves.admin.Host;
import globalwaves.playlist.Playlist;
import globalwaves.user.artist.Album;

public final class SelectResults {
    private SongInput selectSong;
    private PodcastInput selectPodcast;
    private Playlist selectPlaylist;
    private Artist selectArtist;
    private Album selectAlbum;
    private Host selectHost;
    private String username;
    private String lastCommand;

    public SongInput getSelectSong() {
        return selectSong;
    }

    public void setSelectSong(final SongInput selectSong) {
        this.selectSong = selectSong;
    }

    public PodcastInput getSelectPodcast() {
        return selectPodcast;
    }

    public void setSelectPodcast(final PodcastInput selectPodcast) {
        this.selectPodcast = selectPodcast;
    }

    public Playlist getSelectPlaylist() {
        return selectPlaylist;
    }

    public void setSelectPlaylist(final Playlist selectPlaylist) {
        this.selectPlaylist = selectPlaylist;
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

    public Artist getSelectArtist() {
        return selectArtist;
    }

    public void setSelectArtist(final Artist selectArtist) {
        this.selectArtist = selectArtist;
    }

    public Album getSelectAlbum() {
        return selectAlbum;
    }

    public void setSelectAlbum(final Album selectAlbum) {
        this.selectAlbum = selectAlbum;
    }

    public Host getSelectHost() {
        return selectHost;
    }

    public void setSelectHost(final Host selectHost) {
        this.selectHost = selectHost;
    }

    public void setLastCommand(final String lastCommand) {
        this.lastCommand = lastCommand;
    }
    public SelectResults(final ActionInput action, final SongInput song,
                         final PodcastInput podcast, final Playlist playlist,
                         final Artist artist, final Album album, final Host host) {
        this.selectSong = song;
        this.selectPodcast = podcast;
        this.selectPlaylist = playlist;
        this.selectArtist = artist;
        this.selectAlbum = album;
        this.selectHost = host;
        this.username = action.getUsername();
        this.lastCommand = action.getCommand();
    }
    public SelectResults() {

    }
}
