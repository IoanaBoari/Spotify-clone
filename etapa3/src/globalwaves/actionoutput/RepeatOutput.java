package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.LoadResults;
import globalwaves.player.Repeat;

public final class RepeatOutput {
    public RepeatOutput() {

    }
    /**
     * Outputs a message indicating the success or failure of changing the repeat mode.
     * Checks the current loaded source type (song, podcast, or playlist)
     * and performs the corresponding action.
     *
     * @param action      The ActionInput object representing the user's action.
     * @param loadResults The current state of the loaded source and playback statistics.
     * @return The updated LoadResults object after changing the repeat mode.
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
            Repeat repeat = new Repeat();
            if (loadResults == null) {
                object.put("message", "Please load a source before setting the repeat status.");
            } else {
                if (loadResults.getLoadedSong() != null) {
                    repeat.repeatSong(loadResults);
                } else if (loadResults.getLoadedPlaylist() != null) {
                    repeat.repeatPlaylist(loadResults);
                } else if (loadResults.getLoadedPodcast() != null) {
                    repeat.repeatPodcast(loadResults);
                }
                object.put("message", "Repeat mode changed to "
                        + loadResults.getStats().getRepeat().toLowerCase() + ".");
                loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
