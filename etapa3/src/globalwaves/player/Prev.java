package globalwaves.player;



public final class Prev {
    public Prev() {

    }

    /**
     * Handles the transition to the previous song in a playlist during playback.
     * If playback is paused, it resumes playback.
     * Resets the playback statistics for the current song,
     * allowing it to be replayed from the beginning.
     *
     * @param loadResults The LoadResults object containing information
     *                   about the current state of playlist playback.
     */
    public void prevSong(final LoadResults loadResults) {
        if (loadResults.getStats().isPaused()) {
            loadResults.getStats().setPaused(false);
        }
       loadResults.getStats().setRemainedTime(null);
       loadResults.setStats(new Stats(loadResults));
    }

    /**
     * Handles the transition to the previous episode in a podcast during playback.
     * If playback is paused, it resumes playback.
     * If the remaining time in the current episode is less than 1 second,
     * it moves to the previous episode (if available) and resets playback statistics.
     *
     * @param loadResults The LoadResults object containing information
     *                    about the current state of podcast playback.
     */
    public void prevPodcast(final LoadResults loadResults) {
        if (loadResults.getStats().isPaused()) {
            loadResults.getStats().setPaused(false);
        }
        int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
        if (loadResults.getLoadedPodcast().getEpisodes().get(idx).getDuration()
                - loadResults.getStats().getRemainedTime() < 1) {
            if (idx > 0) {
                idx = idx - 1;
                loadResults.getLoadedPodcast().setCurrentEpisodeIndex(idx);
            }
        }
        loadResults.getStats().setRemainedTime(null);
        loadResults.setStats(new Stats(loadResults));
    }

    /**
     * Handles the transition to the previous song in a playlist during playback.
     * If playback is paused, it resumes playback.
     * If the remaining time in the current song is less than 1 second,
     * it moves to the previous song (if available) and resets playback statistics.
     *
     * @param loadResults The LoadResults object containing information
     *                    about the current state of playlist playback.
     */

    public void prevPlaylist(final LoadResults loadResults) {
        if (loadResults.getStats().isPaused()) {
            loadResults.getStats().setPaused(false);
        }
        int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
        if (loadResults.getLoadedPlaylist().getSongs().get(idx).getDuration()
                - loadResults.getStats().getRemainedTime() < 1) {
            if (idx > 0) {
                idx = idx - 1;
                loadResults.getLoadedPlaylist().setCurrentSongIndex(idx);
            }
        }
        loadResults.getStats().setRemainedTime(null);
        loadResults.setStats(new Stats(loadResults));
    }

    /**
     * Plays the previous song in the loaded album based on the provided load results.
     * Manages playback continuation and resets remaining time.
     *
     * @param loadResults The load results containing the loaded album and playback statistics.
     */
    public void prevAlbum(final LoadResults loadResults) {
        if (loadResults.getStats().isPaused()) {
            loadResults.getStats().setPaused(false);
        }
        int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
        if (loadResults.getLoadedAlbum().getSongs().get(idx).getDuration()
                - loadResults.getStats().getRemainedTime() < 1) {
            if (idx > 0) {
                idx = idx - 1;
                loadResults.getLoadedAlbum().setCurrentSongIndex(idx);
            }
        }
        loadResults.getStats().setRemainedTime(null);
        loadResults.setStats(new Stats(loadResults));
    }
}
