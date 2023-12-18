package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.LoadOutput;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.LoadResults;
import globalwaves.player.PodcastLoaded;
import globalwaves.searchbar.SelectResults;

public final class DoLoad implements Command {
    public DoLoad() {

    }

    /**
     * Processes the "load" action based on the provided ActionInput.
     * Manages the loading operation using the LoadOutput class and interacts with lists
     * of SelectResults, and LoadResults for state tracking.
     * Updates the loadResultsArrayList and podcastsLoaded accordingly.
     *
     * @param action                  The ActionInput containing information
     *                                about the user's request.
     */
    public void execute(final ActionInput action) {
        LoadOutput loadOutput = null;
        for (SelectResults results : Database.getInstance().getSelectResultsArrayList()) {
            if (results.getUsername().equals(action.getUsername())) {
                loadOutput = new LoadOutput(results);
                Menu.setLastAction("select");
                Database.getInstance().getSelectResultsArrayList().remove(results);
                break;
            }
        }
        if (loadOutput == null) {
            loadOutput = new LoadOutput();
        }
        loadOutput.doOutput(action, Menu.getLastAction());
        LoadResults loadResults = new LoadResults(action, loadOutput.getSongLoaded(),
                loadOutput.getPodcastLoaded(), loadOutput.getPlaylistLoaded(),
                loadOutput.getAlbumLoaded());
        if (loadResults.getLoadedPodcast() != null) {
            if (Database.getInstance().getPodcastsLoaded().size() != 0) {
                for (PodcastLoaded podcast : Database.getInstance().getPodcastsLoaded()) {
                    StatusOutput statusOutput = new StatusOutput(loadResults);
                    statusOutput.doOutput(action);
                    if (podcast.getUsername().equals(loadResults.getUsername())
                            && podcast.getPodcast().getName().
                            equals(loadResults.getLoadedPodcast().getName())) {
                        loadResults.getStats().setRemainedTime(podcast.getTime());
                    }
                }
            }
        }
        loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
        if (Menu.getLastAction().equals("select")) {
            Database.getInstance().getLoadResultsArrayList().add(loadResults);
            Menu.setLastAction(loadResults.getLastCommand());
        }
    }
}
