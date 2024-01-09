package globalwaves.searchbar;

import fileio.input.Filters;
import globalwaves.Database;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.playlist.SortPlaylists;

import java.util.ArrayList;
import java.util.Iterator;

public final class SearchPlaylist {
    public SearchPlaylist() {

    }

    /**
     * Searches for playlists based on specified filters among public playlists
     * and private playlists of the current user.
     * @param filters The Filters object containing search criteria.
     * @param owner The PlaylistsOwner representing the current user and their private playlists.
     * @return An ArrayList of playlists matching the search criteria.
     */
    public ArrayList<Playlist> doSearch(final Filters filters,
                                        final PlaylistsOwner owner) {
        ArrayList<Playlist> searchedPlaylists = new ArrayList<>();
        if (filters.getName() != null) {
            for (Playlist playlist : Database.getInstance().getPublicPlaylists()) {
                if (playlist.getName().startsWith(filters.getName())) {
                    searchedPlaylists.add(playlist);
                }
            }
            if (!owner.getPlaylists().isEmpty()) {
                for (Playlist playlist : owner.getPlaylists()) {
                    if (playlist.getVisibility().equals("private")
                            && playlist.getName().startsWith(filters.getName())) {
                        searchedPlaylists.add(playlist);
                    }
                }
            }
        }
        if (filters.getOwner() != null) {
            if (searchedPlaylists.isEmpty()) {
                for (Playlist playlist : Database.getInstance().getPublicPlaylists()) {
                    if (playlist.getOwner().equals(filters.getOwner())) {
                        searchedPlaylists.add(playlist);
                    }
                }
                if (!owner.getPlaylists().isEmpty()) {
                    for (Playlist playlist : owner.getPlaylists()) {
                        if (playlist.getVisibility().equals("private")
                                && playlist.getOwner().equals(filters.getOwner())) {
                            searchedPlaylists.add(playlist);
                        }
                    }
                }
            } else {
                Iterator<Playlist> iterator = searchedPlaylists.iterator();
                while (iterator.hasNext()) {
                    Playlist playlist = iterator.next();
                    if (!playlist.getOwner().equals(filters.getOwner())) {
                        iterator.remove();
                    }
                }
            }
        }
        new SortPlaylists().doSortPlaylist(searchedPlaylists);
        return searchedPlaylists;
    }
}
