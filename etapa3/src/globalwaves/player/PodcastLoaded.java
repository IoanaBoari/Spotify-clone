package globalwaves.player;

import fileio.input.PodcastInput;

/**
 * Represents the state of a podcast that has been loaded by a specific user,
 * including the username, the podcast, the current episode index, and the remaining playback time.
 */

public final class PodcastLoaded {
    private String username;
    private PodcastInput podcast;
    private Integer currentEpisodeIndex;
    private Integer time;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public PodcastInput getPodcast() {
        return podcast;
    }

    public void setPodcast(final PodcastInput podcast) {
        this.podcast = podcast;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(final Integer time) {
        this.time = time;
    }

    public Integer getCurrentEpisodeIndex() {
        return currentEpisodeIndex;
    }

    public void setCurrentEpisodeIndex(final Integer currentEpisodeIndex) {
        this.currentEpisodeIndex = currentEpisodeIndex;
    }

    public PodcastLoaded() {

    }
    public PodcastLoaded(final LoadResults loadResults) {
        this.username = loadResults.getUsername();
        this.podcast = loadResults.getLoadedPodcast();
        this.currentEpisodeIndex = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
        this.time = loadResults.getStats().getRemainedTime();
    }
}
