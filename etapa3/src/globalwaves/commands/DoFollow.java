package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.playlist.Follow;
import globalwaves.playlist.FollowedPlaylists;
import globalwaves.searchbar.SelectResults;


public final class DoFollow implements Command {
    public DoFollow() {

    }

    /**
     * Processes the "follow" action based on the provided ActionInput.
     * Manages user following of playlists using the Follow class and interacts with lists
     * of FollowedPlaylists and SelectResults for state tracking.
     * Updates the followedPlaylistsUsers list accordingly.
     *
     * @param action                  The ActionInput containing information about
     *                                the user's request.
     *
     */
    public void execute(final ActionInput action) {
        Follow follow = null;
        for (FollowedPlaylists followedPlaylists : Database.getInstance().
                getFollowedPlaylistsUsers()) {
            if (followedPlaylists.getUser().equals(action.getUsername())) {
                follow = new Follow(followedPlaylists);
                Database.getInstance().getFollowedPlaylistsUsers().remove(followedPlaylists);
                break;
            }
        }
        if (follow == null) {
            follow = new Follow();
        }
        int aux = 0;
        for (SelectResults selectResults : Database.getInstance().getSelectResultsArrayList()) {
            if (selectResults.getUsername().equals(action.getUsername())) {
                follow.doOutput(action, Menu.getOutputs(), "select", selectResults);
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            follow.doOutput(action, Menu.getOutputs(),
                    "noselect", new SelectResults());
        }
        if (follow.getFollowedPlaylists() != null) {
            Database.getInstance().getFollowedPlaylistsUsers().add(follow.getFollowedPlaylists());
        }
    }
}
