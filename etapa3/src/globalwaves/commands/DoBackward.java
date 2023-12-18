package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.BackwardOutput;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;

public final class DoBackward implements Command {
    public DoBackward() {

    }

    /**
     * Performs a backward operation based on the provided ActionInput.
     * Utilizes the BackwardOutput class and interacts with a list of LoadResults objects.
     * Updates the status and output information accordingly.
     *
     * @param action               The ActionInput containing information about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        new UpdateStats().doUpdateCurrent(action);
        BackwardOutput backwardOutput = new BackwardOutput();
        LoadResults newResults = new LoadResults();
        int aux = 0;
        for (LoadResults loadResults:Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                newResults = backwardOutput.doOutput(action, loadResults);
                aux = 1;
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                break;
            }
        }
        if (aux == 0) {
            newResults = backwardOutput.doOutput(action, null);
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
        }
    }
}
