package globalwaves.admin;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.LoadResults;

import java.util.ArrayList;

import static globalwaves.userstats.Listener.updateListener;

public final class UpdateStats {
    public UpdateStats() {

    }

    /**
     * Updates the current status of the user's loaded content based on the provided action.
     *
     * @param action The action input containing information for updating the current status.
     */
    public void doUpdateCurrent(final ActionInput action) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                StatusOutput statusOutput = new StatusOutput(loadResults);
                statusOutput.doOutput(action);
                statusOutput.getLoadResults().getStats().
                        setCurrentTimestamp(action.getTimestamp());
                if (action.getCommand().equals("switchConnectionStatus")) {
                    statusOutput.getLoadResults().getStats().
                            setPaused(!loadResults.getStats().isPaused());
                }
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                if (statusOutput.getLoadResults().getStats().getRemainedTime() != 0) {
                    Database.getInstance().getLoadResultsArrayList().
                            add(statusOutput.getLoadResults());
                }
                break;
            }
        }
    }

    /**
     * Updates the status of all loaded content for all users based on the provided action.
     *
     * @param action The action input containing information for updating the status.
     */
    public void doUpdateAll(final ActionInput action) {
        ArrayList<StatusOutput> statusOutputs = new ArrayList<>();
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            StatusOutput statusOutput = new StatusOutput(loadResults);
            statusOutput.doOutput(action);
            statusOutput.getLoadResults().getStats().setCurrentTimestamp(action.getTimestamp());
            if (statusOutput.getLoadResults().getStats().getRemainedTime() != 0) {
                statusOutputs.add(statusOutput);
            }
        }
        Database.getInstance().getLoadResultsArrayList().clear();
        for (StatusOutput statusOutput : statusOutputs) {
            Database.getInstance().getLoadResultsArrayList().
                    add(new LoadResults(statusOutput.getLoadResults()));
            updateListener(action, new LoadResults(statusOutput.getLoadResults()));
        }
    }
}
