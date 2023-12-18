package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.PlayPauseOutput;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.LoadResults;

public final class DoPlayPause implements Command {
    public DoPlayPause() {

    }

    /**
     * Processes the "play" or "pause" action based on the provided ActionInput.
     * Manages the play/pause operation using the PlayPauseOutput class and interacts with
     * the list of LoadResults for state tracking. Updates the loadResultsArrayList accordingly.
     *
     * @param action                The ActionInput containing information
     *                              about the user's request.
     */
    public void execute(final ActionInput action) {
        PlayPauseOutput playPauseOutput = null;
        if (!Database.getInstance().getLoadResultsArrayList().isEmpty()) {
            for (LoadResults results : Database.getInstance().getLoadResultsArrayList()) {
                if (results.getUsername().equals(action.getUsername())) {
                    StatusOutput statusOutput = new StatusOutput(results);
                    statusOutput.doOutput(action);
                    if (statusOutput.getLoadResults().getStats().getRemainedTime() != 0) {
                        playPauseOutput = new PlayPauseOutput(statusOutput.getLoadResults());
                        playPauseOutput.getLoadResults().setStats(statusOutput.
                                getLoadResults().getStats());
                        Menu.setLastAction(results.getLastCommand());
                        break;
                    }
                }
            }
        }
        if (playPauseOutput == null) {
            playPauseOutput = new PlayPauseOutput(action);
            Menu.setLastAction("notload");
        }
        LoadResults newResults = playPauseOutput.doOutput(action, Menu.getLastAction());
        if (!Database.getInstance().getLoadResultsArrayList().isEmpty()) {
            for (LoadResults results : Database.getInstance().getLoadResultsArrayList()) {
                if (results.getUsername().equals(newResults.getUsername())) {
                    Database.getInstance().getLoadResultsArrayList().remove(results);
                    break;
                }
            }
        }
        Database.getInstance().getLoadResultsArrayList().add(newResults);
        Menu.setLastAction("PlayPause");
    }
}
