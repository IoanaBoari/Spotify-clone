package globalwaves;

import fileio.input.LibraryInput;
import fileio.input.UserInput;
import globalwaves.notification.Notification;
import globalwaves.notification.UserSubscriptions;
import globalwaves.player.LikedSongs;
import globalwaves.player.LoadResults;
import globalwaves.player.PodcastLoaded;
import globalwaves.playlist.FollowedPlaylists;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.recommendation.Recommendations;
import globalwaves.recommendation.UserPages;
import globalwaves.searchbar.SearchResults;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Album;
import globalwaves.user.artist.merch.OwnedMerch;
import globalwaves.userstats.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private ArrayList<Listener> listeners = new ArrayList<>();
    private ArrayList<UserInput> premiumUsers = new ArrayList<>();
    private ArrayList<Listener> premiumListeners = new ArrayList<>();
    private ArrayList<UserSubscriptions> userSubscriptionsArrayList = new ArrayList<>();
    private ArrayList<Notification> newNotifications = new ArrayList<>();
    private ArrayList<OwnedMerch> ownedMerchArrayList = new ArrayList<>();
    private ArrayList<Listener> freeListeners = new ArrayList<>();
    private Map<String, Double> adRevenues = new HashMap<>();
    private ArrayList<Recommendations> recommendations = new ArrayList<>();
    private ArrayList<UserPages> allPages = new ArrayList<>();
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

    public ArrayList<Listener> getListeners() {
        return listeners;
    }

    public void setListeners(final ArrayList<Listener> listeners) {
        this.listeners = listeners;
    }

    public ArrayList<UserInput> getPremiumUsers() {
        return premiumUsers;
    }

    public void setPremiumUsers(final ArrayList<UserInput> premiumUsers) {
        this.premiumUsers = premiumUsers;
    }

    public ArrayList<Listener> getPremiumListeners() {
        return premiumListeners;
    }

    public void setPremiumListeners(final ArrayList<Listener> premiumListeners) {
        this.premiumListeners = premiumListeners;
    }

    public ArrayList<UserSubscriptions> getUserSubscriptionsArrayList() {
        return userSubscriptionsArrayList;
    }

    public void setUserSubscriptionsArrayList(final ArrayList<UserSubscriptions>
                                                      userSubscriptionsArrayList) {
        this.userSubscriptionsArrayList = userSubscriptionsArrayList;
    }

    public ArrayList<Notification> getNewNotifications() {
        return newNotifications;
    }

    public void setNewNotifications(final ArrayList<Notification> newNotifications) {
        this.newNotifications = newNotifications;
    }

    public ArrayList<OwnedMerch> getOwnedMerchArrayList() {
        return ownedMerchArrayList;
    }

    public void setOwnedMerchArrayList(final ArrayList<OwnedMerch> ownedMerchArrayList) {
        this.ownedMerchArrayList = ownedMerchArrayList;
    }

    public ArrayList<Listener> getFreeListeners() {
        return freeListeners;
    }

    public void setFreeListeners(final ArrayList<Listener> freeListeners) {
        this.freeListeners = freeListeners;
    }

    public Map<String, Double> getAdRevenues() {
        return adRevenues;
    }

    public void setAdRevenues(final Map<String, Double> adRevenues) {
        this.adRevenues = adRevenues;
    }

    public ArrayList<Recommendations> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(final ArrayList<Recommendations> recommendations) {
        this.recommendations = recommendations;
    }

    public ArrayList<UserPages> getAllPages() {
        return allPages;
    }

    public void setAllPages(final ArrayList<UserPages> allPages) {
        this.allPages = allPages;
    }
}
