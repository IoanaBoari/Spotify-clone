package fileio.input;

import java.util.ArrayList;

public final class PodcastInput {
    private String name;
    private String owner;
    private ArrayList<EpisodeInput> episodes;
    private Integer currentEpisodeIndex = 0;

    public PodcastInput() {
    }
    public PodcastInput(final ActionInput action) {
        this.name = action.getName();
        this.owner = action.getUsername();
        this.episodes = action.getEpisodes();
        this.currentEpisodeIndex = 0;
    }
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    public Integer getCurrentEpisodeIndex() {
        return currentEpisodeIndex;
    }

    public void setCurrentEpisodeIndex(final Integer currentEpisodeIndex) {
        this.currentEpisodeIndex = currentEpisodeIndex;
    }

    /**
     * Advances to the next episode in the list if available.
     */
    public void playNextEpisode() {
        if (this.currentEpisodeIndex < this.episodes.size() - 1) {
            this.currentEpisodeIndex++;
        }
    }
}
