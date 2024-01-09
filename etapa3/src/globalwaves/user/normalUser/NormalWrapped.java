package globalwaves.user.normalUser;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.userstats.Listener;
import globalwaves.userstats.Wrapped;

import java.util.*;

public final class NormalWrapped extends Wrapped {
    private Map<String, Integer> topArtists;
    private Map<String, Integer> topGenres;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topEpisodes;

    public Map<String, Integer> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(final Map<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    public Map<String, Integer> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(final Map<String, Integer> topGenres) {
        this.topGenres = topGenres;
    }

    public Map<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(final Map<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    public Map<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(final Map<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public Map<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(final Map<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    public NormalWrapped() {
        topArtists = new HashMap<>();
        topGenres = new HashMap<>();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topEpisodes = new HashMap<>();
    }

    /**
     * Updates the top artists, genres, songs, albums, and episodes based on the content
     * loaded by the given user.
     *
     * @param currentUser The user for whom the statistics are updated.
     */
    public void doWrapped(final UserInput currentUser) {
        for (Listener listener : Database.getInstance().getListeners()) {
            if (listener.getUsername().equals(currentUser.getUsername())) {
                if (!listener.getSongsloaded().isEmpty()) {
                    for (SongInput song : listener.getSongsloaded()) {
                        updateMap(topArtists, song.getArtist());
                        updateMap(topGenres, song.getGenre());
                        updateMap(topSongs, song.getName());
                        updateMap(topAlbums, song.getAlbum());
                    }
                }
                if (!listener.getEpisodesloaded().isEmpty()) {
                    for (EpisodeInput episode : listener.getEpisodesloaded()) {
                        updateMap(topEpisodes, episode.getName());
                    }
                }
            }
        }
        this.topArtists = sortMapByValueThenKey(topArtists);
        this.topGenres = sortMapByValueThenKey(topGenres);
        this.topSongs = sortMapByValueThenKey(topSongs);
        this.topAlbums = sortMapByValueThenKey(topAlbums);
        this.topEpisodes = sortMapByValueThenKey(topEpisodes);
    }
    private void updateMap(final Map<String, Integer> map, final String key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

}
