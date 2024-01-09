package globalwaves.recommendation;

import fileio.input.ActionInput;
import fileio.input.Filters;
import fileio.input.SongInput;
import globalwaves.Database;
import globalwaves.player.LoadResults;
import globalwaves.searchbar.SearchSong;

import java.util.ArrayList;
import java.util.Random;

public final class RandomSong {
    public RandomSong() {
    }

    /**
     * Updates song recommendations for a user based on the specified action.
     *
     * @param action The ActionInput containing information necessary for updating recommendations.
     * @return true if recommendations are successfully updated, false otherwise.
     */
    public boolean updateRecommendations(final ActionInput action) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                if (loadResults.getLoadedSong() != null) {
                    int timePassed = loadResults.getLoadedSong().getDuration()
                            - loadResults.getStats().getRemainedTime();
                    if (timePassed < 30) {
                        return false;
                    } else {
                        Filters filters = new Filters();
                        filters.setGenre(loadResults.getLoadedSong().getGenre());
                        ArrayList<SongInput> searchedSongs = new SearchSong().doSearch(filters);
                        Random random = new Random(timePassed);
                        int randomIndex = random.nextInt(searchedSongs.size());
                        SongInput randomSong = searchedSongs.get(randomIndex);
                        for (Recommendations recommendations : Database.getInstance().
                                getRecommendations()) {
                            if (recommendations.getUsername().equals(action.getUsername())) {
                                recommendations.setLastRecommendation(action.
                                        getRecommendationType());
                                recommendations.getSongRecommendations().add(randomSong);
                                break;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
