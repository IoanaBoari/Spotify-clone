package globalwaves.searchbar;

import fileio.input.Filters;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.user.artist.Artist;

import java.util.ArrayList;

public final class SearchArtist {
    public SearchArtist() {

    }

    /**
     * Performs a search for artists based on the specified filters.
     *
     * @param filters The filters to be applied to the search.
     * @return An ArrayList of artists that match the specified filters.
     */
    public ArrayList<Artist> doSearch(final Filters filters) {
        ArrayList<Artist> searchedArtists = new ArrayList<>();
        if (filters.getName() != null) {
            for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                if (user.getType().equals("artist") && user.getUsername().
                        startsWith(filters.getName())) {
                    Artist artist = (Artist) user;
                    searchedArtists.add(artist);
                }
            }
        }
        return searchedArtists;
    }
}
