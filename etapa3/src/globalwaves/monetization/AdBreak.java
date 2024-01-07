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
     *
     * @param action The action input containing information necessary for executing the command.
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
                    boolean premium = false;
                    for (UserInput premiumUser : Database.getInstance().getPremiumUsers()) {
                        if (premiumUser.getUsername().equals(currentUser.getUsername())) {
                            premium = true;
                            break;
                        }
                    }
                    if (!premium) {
                        adRevenue(action);
                    }
                    object.put("message", "Ad inserted successfully.");
                    for (String artist : Database.getInstance().getAdRevenues().keySet()) {
                        double revenue = Database.getInstance().getAdRevenues().get(artist);
                        object.put(artist, revenue);
                    }

                    Menu.setOutputs(Menu.getOutputs().add(object));
                    return;
                }
            }
            object.put("message", action.getUsername() + " is not playing any music.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }

    /**
     *
     * @param action
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
                // Golește lista de melodii pentru utilizatorul curent
                listener.setSongsloaded(new ArrayList<>());
                break;
            }
        }
    }
}
