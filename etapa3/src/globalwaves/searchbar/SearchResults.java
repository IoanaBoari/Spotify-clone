package globalwaves.searchbar;

import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;
import globalwaves.playlist.Playlist;
import globalwaves.user.artist.Album;

import java.util.ArrayList;

public final class SearchResults {
    private ArrayList<SongInput> songResults = new ArrayList<>();
    private ArrayList<PodcastInput> podcastResults = new ArrayList<>();
    private ArrayList<Playlist> playlistResults = new ArrayList<>();
    private ArrayList<Artist> artistsResults = new ArrayList<>();
    private ArrayList<Album> albumsResults = new ArrayList<>();
    private ArrayList<Host> hostsResults = new ArrayList<>();
    private String username;
    private String lastCommand;

    public ArrayList<SongInput> getSongResults() {
        return songResults;
    }

    public void setSongResults(final ArrayList<SongInput> songResults) {
        this.songResults = songResults;
    }

    public ArrayList<PodcastInput> getPodcastResults() {
        return podcastResults;
    }

    public void setPodcastResults(final ArrayList<PodcastInput> podcastResults) {
        this.podcastResults = podcastResults;
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

    public ArrayList<Playlist> getPlaylistResults() {
        return playlistResults;
    }

    public void setPlaylistResults(final ArrayList<Playlist> playlistResults) {
        this.playlistResults = playlistResults;
    }

    public ArrayList<Artist> getArtistsResults() {
        return artistsResults;
    }

    public void setArtistsResults(final ArrayList<Artist> artistsResults) {
        this.artistsResults = artistsResults;
    }

    public ArrayList<Album> getAlbumsResults() {
        return albumsResults;
    }

    public void setAlbumsResults(final ArrayList<Album> albumsResults) {
        this.albumsResults = albumsResults;
    }

    public ArrayList<Host> getHostsResults() {
        return hostsResults;
    }

    public void setHostsResults(final ArrayList<Host> hostsResults) {
        this.hostsResults = hostsResults;
    }

    public SearchResults(final ActionInput action, final ArrayList<SongInput> songResults,
                         final ArrayList<PodcastInput> podcastResults,
                         final ArrayList<Playlist> playlistResults,
                         final ArrayList<Artist> artistResults,
                         final ArrayList<Album> albumResults,
                         final ArrayList<Host> hostResults) {
        this.songResults = songResults;
        this.podcastResults = podcastResults;
        this.playlistResults = playlistResults;
        this.artistsResults = artistResults;
        this.albumsResults = albumResults;
        this.hostsResults = hostResults;
        this.username = action.getUsername();
        this.lastCommand = action.getCommand();
    }
    public SearchResults() {

    }
}
