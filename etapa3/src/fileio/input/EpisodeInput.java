package fileio.input;

import globalwaves.Database;

public final class EpisodeInput {
    private String name;
    private Integer duration;
    private String description;

    public EpisodeInput() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String searchOwner() {
        for (PodcastInput podcast : Database.getInstance().getLibrary().getPodcasts()) {
            for (EpisodeInput episode : podcast.getEpisodes()) {
                if (this.getName() == episode.getName()) {
                    return podcast.getOwner();
                }
            }
        }
        return null;
    }


}
