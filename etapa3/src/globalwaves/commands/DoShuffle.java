package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.actionoutput.ShuffleOutput;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.player.LoadResults;

import static globalwaves.userstats.Listener.updateListener;

public final class DoShuffle implements Command {
    public DoShuffle() {

    }

    /**
     * Executes the shuffle operation on the playlist or loaded source of the user
     * specified in the ActionInput.
     * Utilizes the ShuffleOutput class to manage the shuffle functionality.
     *
     * @param action                The ActionInput containing information about
     *                              the user's shuffle request.
     *
     */
    public void execute(final ActionInput action) {
        ShuffleOutput shuffleOutput = new ShuffleOutput();
        LoadResults newResults = new LoadResults();
        int aux = 0;
        for (LoadResults loadResults: Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                StatusOutput statusOutput = new StatusOutput(loadResults);
                Database.getInstance().getLoadResultsArrayList().remove(loadResults);
                statusOutput.doOutput(action);
                newResults = shuffleOutput.doOutput(action, statusOutput.getLoadResults());
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            newResults = shuffleOutput.doOutput(action, null);
        }
        if (newResults != null && newResults.getUsername() != null) {
            Database.getInstance().getLoadResultsArrayList().add(newResults);
            updateListener(action, newResults);
        }
    }
}
