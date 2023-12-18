package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.LoadResults;
import globalwaves.player.Next;

public final class NextOutput {
    public NextOutput() {

    }

    /**
     * Outputs a message indicating the success or failure
     * of skipping to the next track during playback.
     *
     * @param action      The ActionInput object representing the user's action.
     * @param loadResults The LoadResults object representing the current state of playback.
     * @return The updated LoadResults object after skipping to the next track.
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
            Next next = new Next();
            if (loadResults == null) {
                object.put("message",
                        "Please load a source before skipping to the next track.");
            } else {
                if (loadResults.getLoadedSong() != null) {
                    next.nextSong(loadResults);
                    object.put("message",
                            "Please load a source before skipping to the next track.");
                } else if (loadResults.getLoadedPodcast() != null) {
                    next.nextPodcast(loadResults);
                    if (loadResults.getStats().isPaused()) {
                        object.put("message",
                                "Please load a source before skipping to the next track.");
                    } else {
                        object.put("message",
                                "Skipped to next track successfully. The current track is "
                                        + loadResults.getStats().getName() + ".");
                    }
                } else if (loadResults.getLoadedPlaylist() != null) {
                    next.nextPlaylist(loadResults);
                    if (loadResults.getStats().isPaused()) {
                        object.put("message",
                                "Please load a source before skipping to the next track.");
                    } else {
                        object.put("message",
                                "Skipped to next track successfully. The current track is "
                                        + loadResults.getStats().getName() + ".");
                    }
                } else if (loadResults.getLoadedAlbum() != null) {
                    next.nextAlbum(loadResults);
                    if (loadResults.getStats().isPaused()) {
                        object.put("message",
                                "Please load a source before skipping to the next track.");
                    } else {
                        object.put("message",
                                "Skipped to next track successfully. The current track is "
                                        + loadResults.getStats().getName() + ".");
                    }
                }
                loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
