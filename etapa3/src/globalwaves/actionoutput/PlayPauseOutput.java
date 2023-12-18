package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.player.LoadResults;
import globalwaves.player.PlayPause;
import globalwaves.player.Stats;


public final class PlayPauseOutput {
    private LoadResults loadResults = new LoadResults();

    public LoadResults getLoadResults() {
        return loadResults;
    }
    public void setLoadResults(final LoadResults loadResults) {
        this.loadResults = loadResults;
    }
    public PlayPauseOutput(final LoadResults loadResults) {
        this.loadResults = loadResults;
        this.loadResults.setStats(loadResults.getStats());
    }
    public PlayPauseOutput(final ActionInput action) {
        this.loadResults.setLoadedPodcast(null);
        this.loadResults.setLoadedSong(null);
        this.loadResults.setLoadedPlaylist(null);
        this.loadResults.setUsername(action.getUsername());
        this.loadResults.setLastCommand(action.getCommand());
        this.loadResults.setTimestamp(action.getTimestamp());
        this.loadResults.setStats(new Stats(this.loadResults));
    }
    public PlayPauseOutput() {

    }
    /**
     * Outputs a message indicating the success or failure of pausing or resuming playback.
     *
     * @param action      The ActionInput object representing the user's action.
     * @param lastAction  A String representing the last action performed by the user.
     * @return The updated LoadResults object after pausing or resuming playback.
     */

    public LoadResults doOutput(final ActionInput action, final String lastAction) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            PlayPause playPause = new PlayPause();
            if (lastAction.equals("load")) {
                if (this.loadResults.getStats().isPaused()) {
                    this.loadResults = playPause.doPlay(loadResults, action);
                    object.put("message", "Playback resumed successfully.");
                } else {
                    this.loadResults = playPause.doPause(loadResults, action);
                    object.put("message", "Playback paused successfully.");
                    //object.put("time", this.loadResults.getStats().getRemainedTime());
                }
                //object.put("remained Time", this.loadResults.getStats().getRemainedTime());
            } else {
                object.put("message",
                        "Please load a source before attempting to pause or resume playback.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
        return this.loadResults;
    }
}
