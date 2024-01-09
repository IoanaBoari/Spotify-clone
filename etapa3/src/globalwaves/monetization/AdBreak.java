package globalwaves.monetization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.commands.Command;
import globalwaves.player.LoadResults;
import globalwaves.userstats.Listener;

import java.util.ArrayList;

public final class AdBreak implements Command {
    public AdBreak() {
    }

    /**
     * Executes the specified action, updating user statistics and handling ad revenue insertion.
     *
     * @param action The ActionInput containing information necessary for executing the command.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                break;
            }
        }
        if (currentUser == null) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            new UpdateStats().doUpdateCurrent(action);
            for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
                if (loadResults.getUsername().equals(currentUser.getUsername())
                        && (loadResults.getLoadedSong() != null
                        || loadResults.getLoadedPlaylist() != null
                        || loadResults.getLoadedAlbum() != null)) {
                    adRevenue(action);
                    object.put("message", "Ad inserted successfully.");
                    Menu.setOutputs(Menu.getOutputs().add(object));
                    return;
                }
            }
            object.put("message", action.getUsername() + " is not playing any music.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }

    /**
     * Processes ad revenue for a specific action, distributing the revenue among artists
     * based on their songs loaded by a free listener.
     * Updates the ad revenue records in the database.
     *
     * @param action The ActionInput object representing the ad revenue action.
     */
    public void adRevenue(final ActionInput action) {
        for (Listener listener : Database.getInstance().getFreeListeners()) {
            if (listener.getUsername().equals(action.getUsername())) {
                for (SongInput song : listener.getSongsloaded()) {
                    double adRevenue = ((double) action.getPrice() / listener.
                            getSongsloaded().size());
                    if (Database.getInstance().getAdRevenues().containsKey(song.getArtist())) {
                        double currentRevenue = Database.getInstance().getAdRevenues().
                                get(song.getArtist());
                        Database.getInstance().getAdRevenues().put(song.getArtist(),
                                currentRevenue + adRevenue);
                    } else {
                        Database.getInstance().getAdRevenues().put(song.getArtist(), adRevenue);
                    }
                }

                listener.setSongsloaded(new ArrayList<>());
                break;
            }
        }
    }
}
