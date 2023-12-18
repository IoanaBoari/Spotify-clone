package globalwaves.player;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;


public final class Stats {
    private String name;
    private Integer remainedTime;
    private Integer currentTimestamp;
    private String repeat;
    private boolean shuffle;
    private boolean paused;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getRemainedTime() {
        return remainedTime;
    }

    public void setRemainedTime(final Integer remainedTime) {
        this.remainedTime = remainedTime;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(final String repeat) {
        this.repeat = repeat;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    public Integer getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(final Integer currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    /**
     * Initializes default playback statistics for a new playback session.
     */

    public Stats() {
        this.name = "";
        this.remainedTime = 0;
        this.repeat = "No Repeat";
        this.shuffle = false;
        this.paused = false;
        this.currentTimestamp = 0;
    }

    /**
     * Generates playback statistics based on the current state of the player for a specific user.
     * The statistics include the name of the currently playing item, remaining playback time,
     * repeat mode, shuffle status, and playback pause status.
     *
     * @param loadResults The LoadResults object containing information
     *                    about the current player state.
     */

    public Stats(final LoadResults loadResults) {
        this.currentTimestamp = loadResults.getTimestamp();
        if (loadResults.getLoadedSong() != null) {
            this.name = loadResults.getLoadedSong().getName();
            if (loadResults.getStats().getRemainedTime() == null) {
                this.remainedTime = loadResults.getLoadedSong().getDuration();
            } else {
                this.remainedTime = loadResults.getStats().getRemainedTime();
            }

        } else if (loadResults.getLoadedPodcast() != null) {
            if (loadResults.getLoadedPodcast().getEpisodes() != null) {
                int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
                EpisodeInput loadEpisode = new EpisodeInput();
                if (!loadResults.getLoadedPodcast().getEpisodes().isEmpty()) {
                    loadEpisode = loadResults.getLoadedPodcast().getEpisodes().get(idx);
                }
                this.name = loadEpisode.getName();
                if (loadResults.getStats().getRemainedTime() == null) {
                    this.remainedTime = loadEpisode.getDuration();
                } else {
                    this.remainedTime = loadResults.getStats().getRemainedTime();
                }
                if (this.remainedTime == 0) {
                    this.name = "";
                } else {
                    this.name = loadEpisode.getName();
                }
            }
        } else if (loadResults.getLoadedPlaylist() != null) {
            if (loadResults.getLoadedPlaylist().getSongs() != null) {
                int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                SongInput loadSong = new SongInput();
                if (!loadResults.getLoadedPlaylist().getSongs().isEmpty()) {
                    loadSong = loadResults.getLoadedPlaylist().getSongs().get(idx);
                }
                if (loadResults.getStats().getRemainedTime() == null) {
                    this.remainedTime = loadSong.getDuration();
                } else {
                    this.remainedTime = loadResults.getStats().getRemainedTime();
                }
                if (this.remainedTime != null && this.remainedTime == 0) {
                    this.name = "";
                } else {
                    this.name = loadSong.getName();
                }
            }
        } else if (loadResults.getLoadedAlbum() != null) {
            if (loadResults.getLoadedAlbum().getSongs() != null) {
                int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
                SongInput loadSong = new SongInput();
                if (!loadResults.getLoadedAlbum().getSongs().isEmpty()) {
                    loadSong = loadResults.getLoadedAlbum().getSongs().get(idx);
                }
                if (loadResults.getStats().getRemainedTime() == null) {
                    this.remainedTime = loadSong.getDuration();
                } else {
                    this.remainedTime = loadResults.getStats().getRemainedTime();
                }
                if (this.remainedTime != null && this.remainedTime == 0) {
                    this.name = "";
                } else {
                    this.name = loadSong.getName();
                }
            }
        }
        if (loadResults.getStats() != null && loadResults.getStats().getRepeat() != null) {
            this.repeat = loadResults.getStats().getRepeat();
        } else {
            this.repeat = "No Repeat";
        }
        if (loadResults.getStats() != null) {
            this.shuffle = loadResults.getStats().isShuffle();
            this.paused = loadResults.getStats().isPaused();
        } else {
            this.shuffle = false;
            this.paused = false;
        }
    }
}
