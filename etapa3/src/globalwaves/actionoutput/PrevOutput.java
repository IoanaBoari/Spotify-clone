package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.LoadResults;
import globalwaves.player.Prev;

public final class PrevOutput {
    public PrevOutput() {

    }

    /**
     * Outputs a message indicating the success or failure of returning to the previous track.
     * Checks the current loaded source type (song, podcast, or playlist)
     * and performs the corresponding action.
     *
     * @param action      The ActionInput object representing the user's action.
     * @param loadResults The current state of the loaded source and playback statistics.
     * @return The updated LoadResults object after returning to the previous track.
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
            Prev prev = new Prev();
            if (loadResults == null) {
                object.put("message",
                        "Please load a source before returning to the previous track.");
            } else {
                if (loadResults.getLoadedSong() != null) {
                    prev.prevSong(loadResults);
                } else if (loadResults.getLoadedPodcast() != null) {
                    prev.prevPodcast(loadResults);
                } else if (loadResults.getLoadedPlaylist() != null) {
                    prev.prevPlaylist(loadResults);
                } else if (loadResults.getLoadedAlbum() != null) {
                    prev.prevAlbum(loadResults);
                }
                object.put("message",
                        "Returned to previous track successfully. The current track is "
                        + loadResults.getStats().getName() + ".");
                loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return loadResults;
    }
}
