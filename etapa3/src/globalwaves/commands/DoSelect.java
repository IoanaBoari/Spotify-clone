package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.SelectOutput;
import globalwaves.searchbar.SearchResults;
import globalwaves.searchbar.SelectResults;
public final class DoSelect implements Command {
    public DoSelect() {

    }

    /**
     * Processes the "select" action based on the provided ActionInput.
     * Manages the selection functionality using the SelectOutput class and interacts with
     * the lists (e.g., searchResultsArrayList, selectResultsArrayList) for state tracking.
     * Updates these lists accordingly.
     *
     * @param action                  The ActionInput containing information
     *                                about the user's request.
     */
    public void execute(final ActionInput action) {
        SelectOutput selectOutput = null;
        for (SearchResults results : Database.getInstance().getSearchResultsArrayList()) {
            if (results.getUsername().equals(action.getUsername())
                    && results.getLastCommand().equals("search")) {
                selectOutput = new SelectOutput(results);
                Menu.setLastAction(results.getLastCommand());
                Database.getInstance().getSearchResultsArrayList().remove(results);
                break;
            }
        }
        if (selectOutput == null) {
            selectOutput = new SelectOutput();
        }
        selectOutput.doOutput(action, Menu.getLastAction());
        SelectResults selectResults = new SelectResults(action,
                selectOutput.getSongSelected(), selectOutput.getPodcastSelected(),
                selectOutput.getPlaylistSelected(), selectOutput.getArtistSelected(),
                selectOutput.getAlbumSelected(), selectOutput.getHostSelected());
        int aux = 0;
        if ((selectResults.getSelectSong() == null
                    || selectResults.getSelectSong().getName() == null)
                && (selectResults.getSelectPodcast() == null
                    || selectResults.getSelectPodcast().getName() == null)
                && (selectResults.getSelectPlaylist() == null
                    || selectResults.getSelectPlaylist().getName() == null)
                && (selectResults.getSelectArtist() == null
                    || selectResults.getSelectArtist().getUsername() == null)
                && (selectResults.getSelectAlbum() == null
                    || selectResults.getSelectAlbum().getUsername() == null)
                && (selectResults.getSelectHost() == null
                    || selectResults.getSelectHost().getUsername() == null)) {
            aux = 1;
        }
        if (Menu.getLastAction().equals("search") && ((selectResults.getSelectArtist() != null
                    && selectResults.getSelectArtist().getUsername() != null)
                || (selectResults.getSelectHost() != null
                    && selectResults.getSelectHost().getUsername() != null))) {
            Database.getInstance().getCurrentPages().add(selectResults);
        }
        if (Menu.getLastAction().equals("search") && aux == 0) {
            Database.getInstance().getSelectResultsArrayList().add(selectResults);
            Menu.setLastAction(selectResults.getLastCommand());
        }
    }
}
