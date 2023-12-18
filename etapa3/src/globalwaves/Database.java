package globalwaves;

import fileio.input.LibraryInput;
import globalwaves.player.LikedSongs;
import globalwaves.player.LoadResults;
import globalwaves.player.PodcastLoaded;
import globalwaves.playlist.FollowedPlaylists;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.searchbar.SearchResults;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Album;

import java.util.ArrayList;

/**
 * Singleton database class for storing application library information.
 * Contains a single instance of {@code LibraryInput} and various collections
 * for managing albums, search results, select results, and other entities.
 */
public final class Database {
    private LibraryInput library;
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<SearchResults> searchResultsArrayList = new ArrayList<>();
    private ArrayList<SelectResults> selectResultsArrayList = new ArrayList<>();
    private ArrayList<SelectResults> currentPages = new ArrayList<>();
    private ArrayList<LoadResults> loadResultsArrayList = new ArrayList<>();
    private ArrayList<PlaylistsOwner> playlistsOwners = new ArrayList<>();
    private ArrayList<Playlist> publicPlaylists = new ArrayList<>();
    private ArrayList<LikedSongs> likedSongsUsers = new ArrayList<>();
    private ArrayList<FollowedPlaylists> followedPlaylistsUsers = new ArrayList<>();
    private ArrayList<PodcastLoaded> podcastsLoaded = new ArrayList<>();
    private static Database instance;
    private Database() {

    }

    /**
     * Retrieves the singleton instance of the Database class.
     * Ensures only one instance of Database exists
     * throughout the application.
     *
     * @return The singleton instance of the Database class.
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public LibraryInput getLibrary() {
        return library;
    }

    public void setLibrary(final LibraryInput library) {
        this.library = library;
    }
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<SearchResults> getSearchResultsArrayList() {
        return searchResultsArrayList;
    }

    public void setSearchResultsArrayList(final ArrayList<SearchResults> searchResultsArrayList) {
        this.searchResultsArrayList = searchResultsArrayList;
    }

    public ArrayList<SelectResults> getSelectResultsArrayList() {
        return selectResultsArrayList;
    }

    public void setSelectResultsArrayList(final ArrayList<SelectResults> selectResultsArrayList) {
        this.selectResultsArrayList = selectResultsArrayList;
    }

    public ArrayList<SelectResults> getCurrentPages() {
        return currentPages;
    }

    public void setCurrentPages(final ArrayList<SelectResults> currentPages) {
        this.currentPages = currentPages;
    }

    public ArrayList<LoadResults> getLoadResultsArrayList() {
        return loadResultsArrayList;
    }

    public void setLoadResultsArrayList(final ArrayList<LoadResults> loadResultsArrayList) {
        this.loadResultsArrayList = loadResultsArrayList;
    }

    public ArrayList<PlaylistsOwner> getPlaylistsOwners() {
        return playlistsOwners;
    }

    public void setPlaylistsOwners(final ArrayList<PlaylistsOwner> playlistsOwners) {
        this.playlistsOwners = playlistsOwners;
    }

    public ArrayList<Playlist> getPublicPlaylists() {
        return publicPlaylists;
    }

    public void setPublicPlaylists(final ArrayList<Playlist> publicPlaylists) {
        this.publicPlaylists = publicPlaylists;
    }

    public ArrayList<LikedSongs> getLikedSongsUsers() {
        return likedSongsUsers;
    }

    public void setLikedSongsUsers(final ArrayList<LikedSongs> likedSongsUsers) {
        this.likedSongsUsers = likedSongsUsers;
    }

    public ArrayList<FollowedPlaylists> getFollowedPlaylistsUsers() {
        return followedPlaylistsUsers;
    }

    public void setFollowedPlaylistsUsers(final ArrayList<FollowedPlaylists>
                                                  followedPlaylistsUsers) {
        this.followedPlaylistsUsers = followedPlaylistsUsers;
    }

    public ArrayList<PodcastLoaded> getPodcastsLoaded() {
        return podcastsLoaded;
    }

    public void setPodcastsLoaded(final ArrayList<PodcastLoaded> podcastsLoaded) {
        this.podcastsLoaded = podcastsLoaded;
    }
}
