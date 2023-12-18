package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.RepeatOutput;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.LoadResults;

public final class DoRepeat implements Command {
    public DoRepeat() {

    }

    /**
     * Processes the "repeat" action based on the provided ActionInput.
     * Manages the repeat functionality using the RepeatOutput class and interacts with
     * the list of LoadResults for state tracking. Updates the loadResultsArrayList accordingly.
     *
     * @param action                The ActionInput containing information
     *                              about the user's request.
     */
    public void execute(final ActionInput action) {
        RepeatOutput repeat = new RepeatOutput();
        LoadResults newResults = new LoadResults();
        int aux = 0;
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                StatusOutput statusOutput = new StatusOutput(loadResults);
                statusOutput.doOutput(action);
                if (statusOutput.getLoadResults().getStats().getRemainedTime() != 0) {
                    newResults = repeat.doOutput(action, statusOutput.getLoadResults());
                    aux = 1;
                }
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                break;
            }
        }
        if (aux == 0) {
            newResults = repeat.doOutput(action, null);
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
        }
    }
}
