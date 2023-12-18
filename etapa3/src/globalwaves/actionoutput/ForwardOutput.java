package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.Forward;
import globalwaves.player.LoadResults;

public final class ForwardOutput {
    public ForwardOutput() {

    }

    /**
     * Outputs a message indicating the success or failure
     * of skipping forward in the currently loaded podcast.
     *
     * @param action      The ActionInput object representing the user's action.
     * @param loadResults The LoadResults object representing the current state of the player.
     * @return The updated LoadResults object after the output operation.
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
            Forward forward = new Forward();
            if (loadResults == null) {
                object.put("message", "Please load a source before attempting to forward.");
            } else {
                if (loadResults.getLoadedSong() != null
                        || loadResults.getLoadedPlaylist() != null
                        || loadResults.getLoadedAlbum() != null) {
                    object.put("message", "The loaded source is not a podcast.");
                } else if (loadResults.getLoadedPodcast() != null) {
                    forward.forwardPodcast(loadResults);
                    if (loadResults.getStats().isPaused()) {
                        object.put("message",
                                "Please load a source before skipping forward.");
                    } else {
                        object.put("message", "Skipped forward successfully.");
                    }
                }
                loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
