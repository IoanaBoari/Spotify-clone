package globalwaves.player;


public final class Next {
    public Next() {

    }

    /**
     * Handles the transition to the next song when listening to a song from the library.
     * If the repeat mode is set to "No Repeat," it resets the playback statistics.
     * Otherwise, if the playback is paused, it resumes playback and
     * updates the statistics for the repeated song.
     *
     * @param loadResults The LoadResults object containing information
     *                    about the current state of playback.
     */
    public void nextSong(final LoadResults loadResults) {
        if (loadResults.getStats().getRepeat().equals("No Repeat")) {
            loadResults.setStats(new Stats());
            loadResults.getStats().setPaused(true);
        } else {
            if (loadResults.getStats().isPaused()) {
                loadResults.getStats().setPaused(false);
            }
            loadResults.getStats().setRemainedTime(null);
            loadResults.setStats(new Stats(loadResults));
        }
    }

    /**
     * Handles the transition to the next episode when listening to a podcast.
     * If there is another episode in the podcast, it resumes playback and
     * updates the statistics for the next episode.
     *
     * @param loadResults The LoadResults object containing information
     *                    about the current state of podcast playback.
     */
    public void nextPodcast(final LoadResults loadResults) {
        if (loadResults.getLoadedPodcast().getCurrentEpisodeIndex()
                < loadResults.getLoadedPodcast().getEpisodes().size() - 1) {
            if (loadResults.getStats().isPaused()) {
                loadResults.getStats().setPaused(false);
            }
            loadResults.getLoadedPodcast().playNextEpisode();
            loadResults.getStats().setRemainedTime(null);
            loadResults.setStats(new Stats(loadResults));
        } else {
            if (loadResults.getStats().getRepeat().equals("No Repeat")) {
                loadResults.setStats(new Stats());
                loadResults.getStats().setPaused(true);
            }
        }
    }

    /**
     * Handles the transition to the next song in a playlist during playback.
     * Resumes playback if paused, resets stats for the current song in "Repeat Current Song" mode.
     * Plays the next song and updates playback statistics in other modes.
     * Resets stats and pauses playback if the last song is reached in "No Repeat" mode.
     * Resets stats and starts playback from the first song in "Repeat All" mode.
     *
     * @param loadResults The LoadResults object for playlist playback.
     */
    public void nextPlaylist(final LoadResults loadResults) {
        if (loadResults.getStats().isPaused()) {
            loadResults.getStats().setPaused(false);
        }
        if (loadResults.getStats().getRepeat().equals("Repeat Current Song")) {
            loadResults.getStats().setRemainedTime(null);
            loadResults.setStats(new Stats(loadResults));
        } else {
            if (loadResults.getLoadedPlaylist().getCurrentSongIndex()
                    < loadResults.getLoadedPlaylist().getSongs().size() - 1) {
                loadResults.getLoadedPlaylist().playNextSong();
                loadResults.getStats().setRemainedTime(null);
                loadResults.setStats(new Stats(loadResults));
            } else {
                if (loadResults.getStats().getRepeat().equals("No Repeat")) {
                    loadResults.setStats(new Stats());
                    loadResults.getStats().setPaused(true);
                } else if (loadResults.getStats().getRepeat().equals("Repeat All")) {
                    loadResults.getLoadedPlaylist().setCurrentSongIndex(0);
                    loadResults.getStats().setRemainedTime(null);
                    loadResults.setStats(new Stats(loadResults));
                }
            }
        }
    }

    /**
     * Plays the next song in the loaded album based on the provided load results.
     * Manages song repetition and album repetition settings.
     *
     * @param loadResults The load results containing the loaded album and playback statistics.
     */
    public void nextAlbum(final LoadResults loadResults) {
        if (loadResults.getStats().isPaused()) {
            loadResults.getStats().setPaused(false);
        }
        if (loadResults.getStats().getRepeat().equals("Repeat Current Song")) {
            loadResults.getStats().setRemainedTime(null);
            loadResults.setStats(new Stats(loadResults));
        } else {
            if (loadResults.getLoadedAlbum().getCurrentSongIndex()
                    < loadResults.getLoadedAlbum().getSongs().size() - 1) {
                loadResults.getLoadedAlbum().playNextSong();
                loadResults.getStats().setRemainedTime(null);
                loadResults.setStats(new Stats(loadResults));
            } else {
                if (loadResults.getStats().getRepeat().equals("No Repeat")) {
                    loadResults.setStats(new Stats());
                    loadResults.getStats().setPaused(true);
                } else if (loadResults.getStats().getRepeat().equals("Repeat All")) {
                    loadResults.getLoadedAlbum().setCurrentSongIndex(0);
                    loadResults.getStats().setRemainedTime(null);
                    loadResults.setStats(new Stats(loadResults));
                }
            }
        }
    }
}
