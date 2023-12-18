package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.Like;
import globalwaves.player.LikedSongs;
import globalwaves.player.LoadResults;

public final class DoLike implements Command {
    public DoLike() {

    }

    /**
     * Processes the "like" action based on the provided ActionInput.
     * Manages the like operation using the Like class and interacts with lists
     * of LikedSongs and LoadResults for state tracking.
     * Updates the likedSongsUsers and loadResultsArrayList accordingly.
     *
     * @param action               The ActionInput containing information about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        Like like = null;
        LoadResults newResults = new LoadResults();
        for (LikedSongs likedSongs : Database.getInstance().getLikedSongsUsers()) {
            if (likedSongs.getUser().equals(action.getUsername())) {
                like = new Like(likedSongs);
                Database.getInstance().getLikedSongsUsers().remove(likedSongs);
                break;
            }
        }
        if (like == null) {
            like = new Like();
        }
        int aux = 0;
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                StatusOutput statusOutput = new StatusOutput(loadResults);
                statusOutput.doOutput(action);
                newResults = like.doOutput(action, Menu.getOutputs(),
                        "load", statusOutput.getLoadResults());
                aux = 1;
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                break;
            }
        }
        if (aux == 0) {
            newResults = like.doOutput(action, Menu.getOutputs(),
                    "notload", new LoadResults());
        }
        if (like.getLikedSongs() != null) {
            Database.getInstance().getLikedSongsUsers().add(like.getLikedSongs());
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
        }
    }
}
