package globalwaves.player;


public final class Forward {
    private final Integer ninety = 90;
    public Forward() {

    }

    /**
     * Moves the loaded podcast forward by 90 seconds.
     * If less than 90 seconds remain, it advances to the next episode.
     *
     * @param loadResults The results of the load operation containing podcast statistics.
     */
    public void forwardPodcast(final LoadResults loadResults) {
        int currentRemainedTime = loadResults.getStats().getRemainedTime() - ninety;
        if (currentRemainedTime > 0) {
            loadResults.getStats().setRemainedTime(currentRemainedTime);
        } else {
            Next next = new Next();
            next.nextPodcast(loadResults);
            loadResults.getStats().setRemainedTime(loadResults.getStats().getRemainedTime()
                    + currentRemainedTime);
        }
    }
}
