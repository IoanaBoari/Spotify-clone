package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.PrevOutput;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;

public final class DoPrev implements Command {
    public DoPrev() {

    }

    /**
     * Processes the "previous" track action based on the provided ActionInput.
     * Manages the track navigation using the PrevOutput class and interacts with
     * the list of LoadResults for state tracking. Updates the loadResultsArrayList accordingly.
     *
     * @param action                The ActionInput containing information
     *                              about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        new UpdateStats().doUpdateCurrent(action);
        PrevOutput prevOutput = new PrevOutput();
        LoadResults newResults = new LoadResults();
        int aux = 0;
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                newResults = prevOutput.doOutput(action, loadResults);
                aux = 1;
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                break;
            }
        }
        if (aux == 0) {
            newResults = prevOutput.doOutput(action, null);
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
        }
    }
}
