package globalwaves.searchbar;

import fileio.input.Filters;
import globalwaves.Database;
import globalwaves.user.artist.Album;

import java.util.ArrayList;
import java.util.Iterator;

public final class SearchAlbum {
    public SearchAlbum() {

    }

    /**
     * Performs a search for albums based on the specified filters.
     *
     * @param filters The filters to be applied to the search.
     * @return An ArrayList of albums that match the specified filters.
     */
    public ArrayList<Album> doSearch(final Filters filters) {
        ArrayList<Album> searchedAlbums = new ArrayList<>();
        if (filters.getName() != null) {
            for (Album album : Database.getInstance().getAlbums()) {
                if (album.getName().startsWith(filters.getName())) {
                    searchedAlbums.add(album);
                }
            }
        }
        if (filters.getOwner() != null) {
            if (searchedAlbums.isEmpty()) {
                for (Album album : Database.getInstance().getAlbums()) {
                    if (album.getUsername().startsWith(filters.getOwner())) {
                        searchedAlbums.add(album);
                    }
                }
            } else {
                Iterator<Album> iterator = searchedAlbums.iterator();
                while (iterator.hasNext()) {
                    Album album = iterator.next();
                    if (!album.getUsername().startsWith(filters.getOwner())) {
                        iterator.remove();
                    }
                }
            }
        }
        if (filters.getDescription() != null) {
            if (searchedAlbums.isEmpty()) {
                for (Album album : Database.getInstance().getAlbums()) {
                    if (album.getDescription().startsWith(filters.getDescription())) {
                        searchedAlbums.add(album);
                    }
                }
            } else {
                Iterator<Album> iterator = searchedAlbums.iterator();
                while (iterator.hasNext()) {
                    Album album = iterator.next();
                    if (!album.getDescription().startsWith(filters.getDescription())) {
                        iterator.remove();
                    }
                }
            }
        }
        return searchedAlbums;
    }
}
