package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.playlist.ShowPlaylists;

public final class DoShowPlaylists implements Command {
    public DoShowPlaylists() {

    }

    /**
     * Displays the playlists owned by the user specified in the ActionInput.
     * Utilizes the ShowPlaylists class to manage the display functionality.
     *
     * @param action           The ActionInput containing information
     *                         about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        ShowPlaylists showPlaylists = null;
        for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
            if (owner.getOwner().equals(action.getUsername())) {
                showPlaylists = new ShowPlaylists(owner);
            }
        }
        showPlaylists.doOutput(action, Menu.getOutputs());
    }
}
