package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;
import globalwaves.playlist.AddRemoveInPlaylist;
import globalwaves.playlist.PlaylistsOwner;;

public final class DoAddRemoveInPlaylist implements Command {
    public DoAddRemoveInPlaylist() {

    }

    /**
     * Performs the addition or removal of audio files in a playlist.
     * Utilizes the AddRemoveInPlaylist class and interacts with a list of PlaylistsOwner objects
     * and a list of LoadResults objects.
     *
     * @param action               The ActionInput containing information about the user's request.
     *
     */
    public void execute(final ActionInput action) {
            new UpdateStats().doUpdateCurrent(action);
            AddRemoveInPlaylist addRemoveInPlaylist = null;
            for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
                if (owner.getOwner().equals(action.getUsername())) {
                    addRemoveInPlaylist = new AddRemoveInPlaylist(owner);
                    break;
                }
            }
            if (addRemoveInPlaylist == null) {
                addRemoveInPlaylist = new AddRemoveInPlaylist();
            }
            int aux = 0;
            for (LoadResults results : Database.getInstance().getLoadResultsArrayList()) {
                if (results.getUsername().equals(action.getUsername())) {
                    if (results.getLoadedAlbum() != null
                            && !results.getLoadedAlbum().getSongs().isEmpty()) {
                        int idx = results.getLoadedAlbum().getCurrentSongIndex();
                        results.setLoadedSong(results.getLoadedAlbum().getSongs().get(idx));
                        addRemoveInPlaylist.doOutput(action, Menu.getOutputs(),
                                "load", results);
                        results.setLoadedSong(null);
                    } else {
                        addRemoveInPlaylist.doOutput(action, Menu.getOutputs(),
                                "load", results);
                    }
                    aux = 1;
                    break;
                }
            }
            if (aux == 0) {
                addRemoveInPlaylist.doOutput(action, Menu.getOutputs(),
                        "notload", new LoadResults());
            }
    }
}
