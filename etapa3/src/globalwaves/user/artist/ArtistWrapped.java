package globalwaves.user.artist;


import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.userstats.Listener;
import globalwaves.userstats.Wrapped;

import java.util.HashMap;
import java.util.Map;

public final class ArtistWrapped extends Wrapped {
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topFans;
    private Integer listeners;

    public Map<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(final Map<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public Map<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(final Map<String, Integer> topSongs) {
        this.topSongs = topSongs;
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

    public ArtistWrapped() {
        topAlbums = new HashMap<>();
        topSongs = new HashMap<>();
        topFans = new HashMap<>();
        listeners = 0;
    }

    /**
     *
     * @param currentUser
     */
    public void doWrapped(final UserInput currentUser) {
        for (Listener listener : Database.getInstance().getListeners()) {
            if (!listener.getSongsloaded().isEmpty()) {
                for (SongInput song : listener.getSongsloaded()) {
                    if (song.getArtist().equals(currentUser.getUsername())) {
                        updateMap(topSongs, song.getName());
                        updateMap(topAlbums, song.getAlbum());
                        updateMap(topFans, listener.getUsername());
                    }
                }
            }
        }
        this.topSongs = sortMapByValueThenKey(topSongs);
        this.topAlbums = sortMapByValueThenKey(topAlbums);
        this.topFans = sortMapByValueThenKey(topFans);
        this.listeners = topFans.size();
    }
    private void updateMap(final Map<String, Integer> map, final String key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

}
