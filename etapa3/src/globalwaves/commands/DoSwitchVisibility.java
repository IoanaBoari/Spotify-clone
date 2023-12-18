package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.playlist.SwitchVisibility;

public final class DoSwitchVisibility implements Command {
    public DoSwitchVisibility() {

    }

    /**
     * Switches the visibility of playlists owned by the specified user.
     * Utilizes the SwitchVisibility class to manage the visibility switching logic.
     *
     * @param action           The ActionInput containing information about
     *                         the user's request to switch visibility.
     *
     */
    public void execute(final ActionInput action) {
        SwitchVisibility switchVisibility = null;
        int aux = 0;
        for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
            if (owner.getOwner().equals(action.getUsername())) {
                switchVisibility = new SwitchVisibility(owner);
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            switchVisibility = new SwitchVisibility();
        }
        switchVisibility.doOutput(action, Menu.getOutputs());
        for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
            if (owner.getOwner().equals(action.getUsername())) {
                for (Playlist playlist : owner.getPlaylists()) {
                    if (!Database.getInstance().getPublicPlaylists().contains(playlist)
                            && playlist.getVisibility().equals("public")) {
                        Database.getInstance().getPublicPlaylists().add(playlist);
                    } else if (Database.getInstance().getPublicPlaylists().contains(playlist)
                            && playlist.getVisibility().equals("private")) {
                        Database.getInstance().getPublicPlaylists().remove(playlist);
                    }
                }
            }
        }
    }
}
