package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.LoadResults;

import static globalwaves.userstats.Listener.updateListener;

public final class DoStatus implements Command {
    public DoStatus() {

    }

    /**
     * Retrieves and displays the current status of the player for the specified user.
     * Utilizes the StatusOutput class to manage the status information.
     *
     * @param action               The ActionInput containing information about
     *                             the user's status request.
     */
    public void execute(final ActionInput action) {
        StatusOutput statusOutput = null;
        if (!Database.getInstance().getLoadResultsArrayList().isEmpty()) {
            for (LoadResults results : Database.getInstance().getLoadResultsArrayList()) {
                if (results.getUsername().equals(action.getUsername())) {
                    Database.getInstance().getLoadResultsArrayList().remove(results);
                    statusOutput = new StatusOutput(results);
                    break;
                }
            }
        }
        if (statusOutput == null) {
            statusOutput = new StatusOutput();
        }
        statusOutput.doOutput(action);
        statusOutput.getLoadResults().getStats().
                setCurrentTimestamp(action.getTimestamp());
        if (statusOutput.getLoadResults().getStats().getRemainedTime() != null
                && statusOutput.getLoadResults().getStats().getRemainedTime() > 0) {
            Database.getInstance().getLoadResultsArrayList().add(statusOutput.getLoadResults());
            updateListener(action, statusOutput.getLoadResults());
        }
        Menu.setLastAction("status");
    }
}
