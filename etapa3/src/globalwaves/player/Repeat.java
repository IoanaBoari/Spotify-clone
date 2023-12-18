package globalwaves.player;


public final class Repeat {
    public Repeat() {

    }

    /**
     * Changes the repeat mode for the current song.
     * The repeat mode can be modified between "No Repeat," "Repeat Once," or "Repeat Infinite."
     *
     * @param results The LoadResults object containing information
     *                about the current state of playback.
     */
    public void repeatSong(final LoadResults results) {
        if (results.getStats().getRepeat().equals("No Repeat")) {
            results.getStats().setRepeat("Repeat Once");
        } else if (results.getStats().getRepeat().equals("Repeat Once")) {
            results.getStats().setRepeat("Repeat Infinite");
        } else if (results.getStats().getRepeat().equals("Repeat Infinite")) {
            results.getStats().setRepeat("No Repeat");
        }
    }

    /**
     * Changes the repeat mode for the current playlist.
     * The repeat mode can be modified between "No Repeat," "Repeat All," or "Repeat Current Song."
     *
     * @param results The LoadResults object containing information
     *                about the current state of playlist playback.
     */

    public void repeatPlaylist(final LoadResults results) {
        if (results.getStats().getRepeat().equals("No Repeat")) {
            results.getStats().setRepeat("Repeat All");
        } else if (results.getStats().getRepeat().equals("Repeat All")) {
            results.getStats().setRepeat("Repeat Current Song");
        } else if (results.getStats().getRepeat().equals("Repeat Current Song")) {
            results.getStats().setRepeat("No Repeat");
        }
    }

    /**
     * Changes the repeat mode for the current podcast.
     * The repeat mode can be modified between "No Repeat," "Repeat Once," or "Repeat Infinite."
     *
     * @param results The LoadResults object containing information
     *                about the current state of podcast playback.
     */
    public void repeatPodcast(final LoadResults results) {
        if (results.getStats().getRepeat().equals("No Repeat")) {
            results.getStats().setRepeat("Repeat Once");
        } else if (results.getStats().getRepeat().equals("Repeat Once")) {
            results.getStats().setRepeat("Repeat Infinite");
        } else if (results.getStats().getRepeat().equals("Repeat Infinite")) {
            results.getStats().setRepeat("No Repeat");
        }
    }
}
