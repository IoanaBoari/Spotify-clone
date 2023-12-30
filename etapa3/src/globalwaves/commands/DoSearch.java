package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.SearchOutput;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;
import globalwaves.player.PodcastLoaded;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.searchbar.SearchResults;
import globalwaves.searchbar.SelectResults;

public final class DoSearch implements Command {
    public DoSearch() {

    }

    /**
     * Processes the "search" action based on the provided ActionInput.
     * Manages the search functionality using the SearchOutput class and interacts with
     * various lists (e.g., playlistsOwners, publicPlaylists, searchResultsArrayList, etc.)
     * for state tracking. Updates these lists accordingly.
     *
     * @param action                  The ActionInput containing information
     *
     */
    public void execute(final ActionInput action) {
        SearchOutput searchOutput = new SearchOutput();
        int aux = 0;
        for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
            if (owner.getOwner().equals(action.getUsername())) {
                searchOutput.doOutput(action, owner);
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            searchOutput.doOutput(action, new PlaylistsOwner());
        }
        if (!Database.getInstance().getSearchResultsArrayList().isEmpty()) {
            for (SearchResults results : Database.getInstance().getSearchResultsArrayList()) {
                if (results.getUsername().equals(action.getUsername())) {
                    Database.getInstance().getSearchResultsArrayList().remove(results);
                    break;
                }
            }
        }
        if (!Database.getInstance().getSelectResultsArrayList().isEmpty()) {
            for (SelectResults results : Database.getInstance().getSelectResultsArrayList()) {
                if (results.getUsername().equals(action.getUsername())) {
                    Database.getInstance().getSelectResultsArrayList().remove(results);
                    break;
                }
            }
        }
        if (!Database.getInstance().getCurrentPages().isEmpty()) {
            for (SelectResults results : Database.getInstance().getCurrentPages()) {
                if (results.getUsername().equals(action.getUsername())
                        && (action.getType().equals("artist")
                            || action.getType().equals("host"))) {
                    Database.getInstance().getCurrentPages().remove(results);
                    break;
                }
            }
        }
        new UpdateStats().doUpdateCurrent(action);
        if (!Database.getInstance().getLoadResultsArrayList().isEmpty()) {
            for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
                if (loadResults.getUsername().equals(action.getUsername())) {
                    if (loadResults.getLoadedPodcast() != null
                            && loadResults.getStats().getRemainedTime() != null) {
                        int currentRemainedTime =
                                loadResults.getStats().getRemainedTime()
                                        - (action.getTimestamp()
                                        - loadResults.getStats().getCurrentTimestamp());
                        loadResults.getStats().setRemainedTime(currentRemainedTime);
                        PodcastLoaded podcast = new PodcastLoaded(loadResults);
                        Database.getInstance().getPodcastsLoaded().add(podcast);
                    }
                    Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                    break;
                }
            }
        }
        SearchResults searchResults = new SearchResults(action,
                searchOutput.getSongResults(), searchOutput.getPodcastResults(),
                searchOutput.getPlaylistResults(), searchOutput.getArtistsResults(),
                searchOutput.getAlbumsResults(), searchOutput.getHostsResults());
        Database.getInstance().getSearchResultsArrayList().add(searchResults);
        Menu.setLastAction(searchResults.getLastCommand());
    }
}
