package globalwaves.player;

import fileio.input.ActionInput;

public final class PlayPause {
    public PlayPause() {

    }

    /**
     * Resumes playback by setting the pause state to false and updating the timestamp.
     *
     * @param results The LoadResults object containing playback information.
     * @param action  The ActionInput object representing the play action.
     * @return The updated LoadResults object after resuming playback.
     */
    public LoadResults doPlay(final LoadResults results, final ActionInput action) {
        results.getStats().setPaused(false);
        results.getStats().setCurrentTimestamp(action.getTimestamp());
        return results;
    }

    /**
     * Pauses playback by setting the pause state to true and updating the timestamp.
     *
     * @param results The LoadResults object containing playback information.
     * @param action  The ActionInput object representing the pause action.
     * @return The updated LoadResults object after pausing playback.
     */
    public LoadResults doPause(final LoadResults results, final ActionInput action) {
        results.getStats().setPaused(true);
        results.getStats().setCurrentTimestamp(action.getTimestamp());
        return results;
    }
}
