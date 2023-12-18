package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.player.LikedSongs;
import globalwaves.userstats.ShowPreferredSongs;

public final class DoShowPreferredSongs implements Command {
    public DoShowPreferredSongs() {

    }

    /**
     * Displays the preferred songs of the user specified in the ActionInput.
     * Utilizes the ShowPreferredSongs class to manage the display functionality.
     *
     * @param action           The ActionInput containing information
     *
     */
    public void execute(final ActionInput action) {
        ShowPreferredSongs showPreferredSongs = new ShowPreferredSongs();
        int aux = 0;
        for (LikedSongs likedSongs : Database.getInstance().getLikedSongsUsers()) {
            if (likedSongs.getUser().equals(action.getUsername())) {
                showPreferredSongs.doOutput(action, likedSongs);
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            showPreferredSongs.doOutput(action, new LikedSongs());
        }
    }
}
