package globalwaves.searchbar;

import fileio.input.Filters;
import fileio.input.PodcastInput;
import globalwaves.Database;

import java.util.ArrayList;
import java.util.Iterator;

public final class SearchPodcast {
    public SearchPodcast() {

    }

    /**
     * Searches for podcasts based on specified filters in the application's library.
     *
     * @param filters The Filters object containing search criteria.
     * @return An ArrayList of podcasts matching the search criteria.
     */
    public ArrayList<PodcastInput> doSearch(final Filters filters) {
        ArrayList<PodcastInput> searchedPodcasts = new ArrayList<>();
        if (filters.getName() != null) {
            for (PodcastInput podcast : Database.getInstance().getLibrary().getPodcasts()) {
                if (podcast.getName().startsWith(filters.getName())) {
                    searchedPodcasts.add(podcast);
                }
            }
        }
        if (filters.getOwner() != null) {
            if (searchedPodcasts.isEmpty()) {
                for (PodcastInput podcast : Database.getInstance().getLibrary().getPodcasts()) {
                    if (podcast.getOwner().equals(filters.getOwner())) {
                        searchedPodcasts.add(podcast);
                    }
                }
            } else {
                Iterator<PodcastInput> iterator = searchedPodcasts.iterator();
                while (iterator.hasNext()) {
                    PodcastInput podcast = iterator.next();
                    if (!podcast.getOwner().equals(filters.getOwner())) {
                        iterator.remove();
                    }
                }
            }
        }
        return searchedPodcasts;
    }
}
