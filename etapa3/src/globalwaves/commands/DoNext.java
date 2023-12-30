package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.NextOutput;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;

import static globalwaves.userstats.Listener.updateListener;

public final class DoNext implements Command {
    public DoNext() {

    }

    /**
     * Processes the "next" action based on the provided ActionInput.
     * Manages the next operation using the NextOutput class and interacts with the
     * list of LoadResults for state tracking. Updates the loadResultsArrayList accordingly.
     *
     * @param action                The ActionInput containing information
     *                              about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        new UpdateStats().doUpdateCurrent(action);
        NextOutput nextOutput = new NextOutput();
        LoadResults newResults = new LoadResults();
        int aux = 0;
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                newResults = nextOutput.doOutput(action, loadResults);
                aux = 1;
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                break;
            }
        }
        if (aux == 0) {
            newResults = nextOutput.doOutput(action, null);
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
            updateListener(action, newResults);
        }
    }
}
