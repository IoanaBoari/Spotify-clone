package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.Backward;
import globalwaves.player.LoadResults;

public final class BackwardOutput {
    public BackwardOutput() {

    }

    /**
     * Outputs a message indicating the success or failure
     * of rewinding the currently loaded source.
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
            Backward backward = new Backward();
            if (loadResults == null) {
                object.put("message", "Please load a source before rewinding.");
            } else {
                if (loadResults.getLoadedSong() != null
                        || loadResults.getLoadedPlaylist() != null
                        || loadResults.getLoadedAlbum() != null) {
                    object.put("message", "The loaded source is not a podcast.");
                } else if (loadResults.getLoadedPodcast() != null) {
                    backward.backwardPodcast(loadResults);
                    if (loadResults.getStats().isPaused()) {
                        object.put("message",
                                "Please load a source before rewinding.");
                    } else {
                        object.put("message", "Rewound successfully.");
                    }
                }
                loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
