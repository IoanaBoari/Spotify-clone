package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.ForwardOutput;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;

public final class DoForward implements Command {
    public DoForward() {

    }

    /**
     * Processes the "forward" action based on the provided ActionInput.
     * Manages the forward operation using the ForwardOutput class and interacts with lists
     * of LoadResults for state tracking.
     * Updates the loadResultsArrayList accordingly.
     *
     * @param action               The ActionInput containing information about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        new UpdateStats().doUpdateCurrent(action);
        ForwardOutput forwardOutput = new ForwardOutput();
        LoadResults newResults = new LoadResults();
        int aux = 0;
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                newResults = forwardOutput.doOutput(action, loadResults);
                aux = 1;
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                break;
            }
        }
        if (aux == 0) {
            newResults = forwardOutput.doOutput(action, null);
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
        }
    }
}
