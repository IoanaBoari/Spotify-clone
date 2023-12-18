package globalwaves.searchbar;

import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.admin.Artist;
import globalwaves.admin.Host;
import globalwaves.playlist.Playlist;
import globalwaves.user.artist.Album;

public final class Select {
    public Select() {

    }

    /**
     * Selects a song based on the provided action and search results.
     *
     * @param action The ActionInput representing the user's action.
     * @param searchResults The SearchResults containing the list of songs.
     * @return The selected SongInput, or null if the selection is out of bounds
     * or the list is empty.
     */
    public SongInput doSelectSong(final ActionInput action, final SearchResults searchResults) {
        if (!searchResults.getSongResults().isEmpty()) {
            if (action.getItemNumber() <= searchResults.getSongResults().size()) {
                return searchResults.getSongResults().get(action.getItemNumber() - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Selects a podcast based on the provided action and search results.
     *
     * @param action The ActionInput representing the user's action.
     * @param searchResults The SearchResults containing the list of podcasts.
     * @return The selected PodcastInput, or null if the selection is out of bounds
     * or the list is empty.
     */
    public PodcastInput doSelectPodcast(final ActionInput action,
                                        final SearchResults searchResults) {
        if (!searchResults.getPodcastResults().isEmpty()) {
            if (action.getItemNumber() <= searchResults.getPodcastResults().size()) {
                return searchResults.getPodcastResults().get(action.getItemNumber() - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Selects a playlist based on the provided action and search results.
     *
     * @param action The ActionInput representing the user's action.
     * @param searchResults The SearchResults containing the list of playlists.
     * @return The selected Playlist, or null if the selection is out of bounds
     * or the list is empty.
     */
    public Playlist doSelectPlaylist(final ActionInput action, final SearchResults searchResults) {
        if (!searchResults.getPlaylistResults().isEmpty()) {
            if (action.getItemNumber() <= searchResults.getPlaylistResults().size()) {
                return searchResults.getPlaylistResults().get(action.getItemNumber() - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Selects an artist from the search results based on the provided action.
     *
     * @param action        The action input specifying the user's selection.
     * @param searchResults The search results containing the list of artists.
     * @return The selected artist or null if the selection is invalid.
     */
    public Artist doSelectArtist(final ActionInput action, final SearchResults searchResults) {
        if (!searchResults.getArtistsResults().isEmpty()) {
            if (action.getItemNumber() <= searchResults.getArtistsResults().size()) {
                return searchResults.getArtistsResults().get(action.getItemNumber() - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Selects an album from the search results based on the provided action.
     *
     * @param action        The action input specifying the user's selection.
     * @param searchResults The search results containing the list of albums.
     * @return The selected album or null if the selection is invalid.
     */
    public Album doSelectAlbum(final ActionInput action, final SearchResults searchResults) {
        if (!searchResults.getAlbumsResults().isEmpty()) {
            if (action.getItemNumber() <= searchResults.getAlbumsResults().size()) {
                return searchResults.getAlbumsResults().get(action.getItemNumber() - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Selects a host from the search results based on the provided action.
     *
     * @param action        The action input specifying the user's selection.
     * @param searchResults The search results containing the list of hosts.
     * @return The selected host or null if the selection is invalid.
     */
    public Host doSelectHost(final ActionInput action, final SearchResults searchResults) {
        if (!searchResults.getHostsResults().isEmpty()) {
            if (action.getItemNumber() <= searchResults.getHostsResults().size()) {
                return searchResults.getHostsResults().get(action.getItemNumber() - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
