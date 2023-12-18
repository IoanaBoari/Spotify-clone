package globalwaves;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.ActionInput;
import globalwaves.commands.*;
import globalwaves.playlist.SortPlaylists;


import java.util.ArrayList;
import java.util.List;

/**
 *The central class managing user actions and orchestrating
 * song, podcast and playlist functionalities in the application.
 * Utilizes various command classes for action execution.
 */
public final class Menu {
    private final List<ActionInput> actions;
    private static ArrayNode outputs;
    private static String lastAction;
    public List<ActionInput> getActions() {
        return actions;
    }

    public static ArrayNode getOutputs() {
        return outputs;
    }

    public static void setOutputs(final ArrayNode outputs) {
        Menu.outputs = outputs;
    }
    public static String getLastAction() {
        return lastAction;
    }

    public static void setLastAction(final String lastAction) {
        Menu.lastAction = lastAction;
    }
    /**
     * Constructs a Menu object with the specified list of actions
     * and an ArrayNode for storing outputs. Initializes the lastAction
     * to null.
     *
     * @param actions List of ActionInput objects representing user actions.
     * @param outputs ArrayNode for storing outputs from executed actions.
     */
    public Menu(final List<ActionInput> actions, final ArrayNode outputs) {
        this.actions = actions;
        Menu.outputs = outputs;
        Menu.lastAction = null;
    }
    /**
     * Executes a sequence of user actions based on the provided list of actions.
     * Manages various functionalities such as searching, selecting, loading, and
     * performing playlist and song operations. Utilizes specific command classes
     * for each action type.
     *
     * Creates and manages dynamic lists for search, select, load, playlists, songs,
     * liked songs, followed playlists, and podcasts during the execution of actions.
     */
    public void action() {
        // Clear existing lists in the database to ensure a clean state
        Database.getInstance().setAlbums(new ArrayList<>());
        Database.getInstance().setSearchResultsArrayList(new ArrayList<>());
        Database.getInstance().setSelectResultsArrayList(new ArrayList<>());
        Database.getInstance().setCurrentPages(new ArrayList<>());
        Database.getInstance().setLoadResultsArrayList(new ArrayList<>());
        Database.getInstance().setPlaylistsOwners(new ArrayList<>());
        Database.getInstance().setPublicPlaylists(new ArrayList<>());
        Database.getInstance().setLikedSongsUsers(new ArrayList<>());
        Database.getInstance().setFollowedPlaylistsUsers(new ArrayList<>());
        Database.getInstance().setPodcastsLoaded(new ArrayList<>());

        // Execute each action using the Command Pattern
        for (ActionInput action : actions) {
            new SortPlaylists().doSortPlaylist(Database.getInstance().getPublicPlaylists());
            new Invoker().execute(action);
        }

        // Clear lists after executing actions to avoid data duplication
        Database.getInstance().getAlbums().clear();
        Database.getInstance().getSearchResultsArrayList().clear();
        Database.getInstance().getSelectResultsArrayList().clear();
        Database.getInstance().getCurrentPages().clear();
        Database.getInstance().getLoadResultsArrayList().clear();
        Database.getInstance().getPlaylistsOwners().clear();
        Database.getInstance().getPublicPlaylists().clear();
        Database.getInstance().getLikedSongsUsers().clear();
        Database.getInstance().getFollowedPlaylistsUsers().clear();
        Database.getInstance().getPodcastsLoaded().clear();
    }
}
