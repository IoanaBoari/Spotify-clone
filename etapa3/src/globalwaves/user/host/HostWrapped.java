package globalwaves.user.host;

import fileio.input.EpisodeInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.userstats.Listener;
import globalwaves.userstats.Wrapped;

import java.util.HashMap;
import java.util.Map;

public final class HostWrapped extends Wrapped {
    private Map<String, Integer> topEpisodes;
    private Map<String, Integer> topFans;
    private Integer listeners;

    public Map<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(final Map<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    public Map<String, Integer> getTopFans() {
        return topFans;
    }

    public void setTopFans(final Map<String, Integer> topFans) {
        this.topFans = topFans;
    }

    public Integer getListeners() {
        return listeners;
    }

    public void setListeners(final Integer listeners) {
        this.listeners = listeners;
    }

    public HostWrapped() {
        topEpisodes = new HashMap<>();
        topFans = new HashMap<>();
        listeners = 0;
    }

    /**
     *
     * @param currentUser
     */
    public void doWrapped(final UserInput currentUser) {
        for (Listener listener : Database.getInstance().getListeners()) {
            if (!listener.getEpisodesloaded().isEmpty()) {
                for (EpisodeInput episode : listener.getEpisodesloaded()) {
                    if (episode.searchOwner().equals(currentUser.getUsername())) {
                        updateMap(topEpisodes, episode.getName());
                        updateMap(topFans, listener.getUsername());
                    }
                }
            }
        }
        this.topEpisodes = sortMapByValueThenKey(topEpisodes);
        this.topFans = sortMapByValueThenKey(topFans);
        this.listeners = topFans.size();
    }
    private void updateMap(final Map<String, Integer> map, final String key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

}
