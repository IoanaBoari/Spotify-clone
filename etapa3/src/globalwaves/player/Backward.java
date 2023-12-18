package globalwaves.player;


public final class Backward {
    private final Integer ninety = 90;
    public Backward() {

    }

    /**
     * Moves the loaded podcast backward by 90 seconds.
     * If less than 90 seconds have passed, the player remains at the beginning
     * of the current episode.
     *
     * @param loadResults The results of the load operation containing podcast statistics.
     */
    public void backwardPodcast(final LoadResults loadResults) {
        int currentRemainedTime = loadResults.getStats().getRemainedTime() + ninety;
        int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
        if (currentRemainedTime >= loadResults.getLoadedPodcast().getEpisodes().
                get(idx).getDuration()) {
            loadResults.getStats().setRemainedTime(null);
            loadResults.setStats(new Stats(loadResults));
        } else {
            loadResults.getStats().setRemainedTime(currentRemainedTime);
        }
    }
}
