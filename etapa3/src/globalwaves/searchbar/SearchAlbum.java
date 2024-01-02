package globalwaves.searchbar;

import fileio.input.Filters;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.user.artist.Artist;
import globalwaves.user.artist.Album;

import java.util.*;

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
            for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                if (user.getType().equals("artist")) {
                    Artist artist = (Artist) user;
                    for (Album album : artist.getAlbums()) {
                        if (album.getName().toLowerCase().startsWith(filters.
                                getName().toLowerCase())) {
                            searchedAlbums.add(album);
                        }
                    }
                }
            }
        }
        if (filters.getOwner() != null) {
            if (searchedAlbums.isEmpty()) {
                for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                    if (user.getType().equals("artist")) {
                        Artist artist = (Artist) user;
                        for (Album album : artist.getAlbums()) {
                            if (album.getUsername().startsWith(filters.getOwner())) {
                                searchedAlbums.add(album);
                            }
                        }
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
                for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                    if (user.getType().equals("artist")) {
                        Artist artist = (Artist) user;
                        for (Album album : artist.getAlbums()) {
                            if (album.getDescription().startsWith(filters.getDescription())) {
                                searchedAlbums.add(album);
                            }
                        }
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
