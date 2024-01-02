package globalwaves.userstats;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;

import java.util.ArrayList;

public final class ShowPodcastResult {
    private String name;
    private ArrayList<String> episodes = new ArrayList<>();
    public ShowPodcastResult(final PodcastInput podcastInput) {
        this.name = podcastInput.getName();
        for (EpisodeInput episode : podcastInput.getEpisodes()) {
            episodes.add(episode.getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<String> episodes) {
        this.episodes = episodes;
    }
}
