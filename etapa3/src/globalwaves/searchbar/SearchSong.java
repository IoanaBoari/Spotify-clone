package globalwaves.searchbar;

import fileio.input.Filters;
import fileio.input.SongInput;
import globalwaves.Database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SearchSong {
    public SearchSong() {

    }

    /**
     * Checks if a list of filter tags is contained within a list of song tags.
     *
     * @param filtersTags List of tags from filters.
     * @param songsTags List of tags associated with songs.
     * @return True if all filter tags are found in the song tags, otherwise false.
     */
    private boolean containsTags(final List<String> filtersTags, final List<String> songsTags) {
        for (String filterTag : filtersTags) {
            boolean found = false;
            for (String songTag : songsTags) {
                if (songTag.contains(filterTag)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches for songs based on specified filters in the application's library.
     *
     * @param filters The Filters object containing search criteria.
     * @return An ArrayList of songs matching the search criteria.
     */
    public ArrayList<SongInput> doSearch(final Filters filters) {
        ArrayList<SongInput> searchedSongs = new ArrayList<SongInput>();
        if (filters.getName() != null) {
            for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                if (song.getName().startsWith(filters.getName())) {
                    searchedSongs.add(song);
                }
            }
        }
        if (filters.getAlbum() != null) {
            if (searchedSongs.isEmpty()) {
                for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                    if (song.getAlbum().equals(filters.getAlbum())) {
                        searchedSongs.add(song);
                    }
                }
            } else {
                Iterator<SongInput> iterator = searchedSongs.iterator();
                while (iterator.hasNext()) {
                    SongInput song = iterator.next();
                    if (!song.getAlbum().equals(filters.getAlbum())) {
                        iterator.remove();
                    }
                }
            }
        }
        if (filters.getTags() != null) {
            if (searchedSongs.isEmpty()) {
                for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                    if (containsTags(filters.getTags(), song.getTags())) {
                        searchedSongs.add(song);
                    }
                }
            } else {
                Iterator<SongInput> iterator = searchedSongs.iterator();
                while (iterator.hasNext()) {
                    SongInput song = iterator.next();
                    if (!containsTags(filters.getTags(), song.getTags())) {
                        iterator.remove();
                    }
                }
            }
        }
        if (filters.getLyrics() != null) {
            if (searchedSongs.isEmpty()) {
                for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                    if (song.getLyrics().toLowerCase().
                            contains(filters.getLyrics().toLowerCase())) {
                        searchedSongs.add(song);
                    }
                }
            } else {
                Iterator<SongInput> iterator = searchedSongs.iterator();
                while (iterator.hasNext()) {
                    SongInput song = iterator.next();
                    if (!song.getLyrics().toLowerCase().
                            contains(filters.getLyrics().toLowerCase())) {
                        iterator.remove();
                    }
                }
            }
        }
        if (filters.getGenre() != null) {
            if (searchedSongs.isEmpty()) {
                for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                    if (song.getGenre().equalsIgnoreCase(filters.getGenre())) {
                        searchedSongs.add(song);
                    }
                }
            } else {
                Iterator<SongInput> iterator = searchedSongs.iterator();
                while (iterator.hasNext()) {
                    SongInput song = iterator.next();
                    if (!song.getGenre().equalsIgnoreCase(filters.getGenre())) {
                        iterator.remove();
                    }
                }
            }
        }
        if (filters.getReleaseYear() != null) {
            int compareYear = Integer.parseInt(filters.getReleaseYear().
                    replaceAll("[^\\d]", ""));
            if (searchedSongs.isEmpty()) {
                for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                    if (filters.getReleaseYear().startsWith("<")
                            && song.getReleaseYear() < compareYear) {
                        searchedSongs.add(song);
                    } else if (filters.getReleaseYear().startsWith(">")
                            && song.getReleaseYear() > compareYear) {
                        searchedSongs.add(song);
                    }
                }
            } else {
                Iterator<SongInput> iterator = searchedSongs.iterator();
                while (iterator.hasNext()) {
                    SongInput song = iterator.next();
                    if (filters.getReleaseYear().startsWith("<")
                            && !(song.getReleaseYear() < compareYear)) {
                        iterator.remove();
                    } else if (filters.getReleaseYear().startsWith(">")
                            && !(song.getReleaseYear() > compareYear)) {
                        iterator.remove();
                    }
                }
            }
        }
        if (filters.getArtist() != null) {
            if (searchedSongs.isEmpty()) {
                for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
                    if (song.getArtist().equals(filters.getArtist())) {
                        searchedSongs.add(song);
                    }
                }
            } else {
                Iterator<SongInput> iterator = searchedSongs.iterator();
                while (iterator.hasNext()) {
                    SongInput song = iterator.next();
                    if (!song.getArtist().equals(filters.getArtist())) {
                        iterator.remove();
                    }
                }
            }
        }
        return searchedSongs;
    }
}
