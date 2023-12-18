package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.LoadResults;
import globalwaves.player.Shuffle;

public final class ShuffleOutput {
    public ShuffleOutput() {

    }

    /**
     * Outputs the result of using the shuffle function based on the loaded source (playlist).
     * Handles activating and deactivating the shuffle function,
     * displaying success messages accordingly.
     *
     * @param action      The ActionInput object representing the user's shuffle action.
     * @param loadResults The current state of the loaded source and playback.
     * @return The updated LoadResults object after applying the shuffle function.
     */

    public LoadResults doOutput(final ActionInput action, final LoadResults loadResults) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            Shuffle shuffle = new Shuffle();
            if (loadResults == null) {
                object.put("message", "Please load a source before using the shuffle function.");
            } else {
                if (loadResults.getLoadedSong() != null || loadResults.getLoadedPodcast() != null) {
                    object.put("message", "The loaded source is not a playlist or an album.");
                } else if (loadResults.getLoadedPlaylist() != null) {
                    shuffle.shufflePlaylist(loadResults, action);
                    if (loadResults.getStats().isShuffle()) {
                        object.put("message", "Shuffle function activated successfully.");
                    } else if (!loadResults.getStats().isShuffle()) {
                        if (loadResults.getStats().getRemainedTime() == 0) {
                            object.put("message",
                                    "Please load a source before using the shuffle function.");
                        } else {
                            object.put("message", "Shuffle function deactivated successfully.");
                        }
                    }
                    loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
                } else if (loadResults.getLoadedAlbum() != null) {
                    shuffle.shuffleAlbum(loadResults, action);
                    if (loadResults.getStats().isShuffle()) {
                        object.put("message", "Shuffle function activated successfully.");
                    } else if (!loadResults.getStats().isShuffle()) {
                        if (loadResults.getStats().getRemainedTime() == 0) {
                            object.put("message",
                                    "Please load a source before using the shuffle function.");
                        } else {
                            object.put("message", "Shuffle function deactivated successfully.");
                        }
                    }
                    loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
