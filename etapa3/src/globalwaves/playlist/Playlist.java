package globalwaves.playlist;

import fileio.input.ActionInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public final class Playlist {
    private ArrayList<SongInput> songs = new ArrayList<>();
    private ArrayList<SongInput> initialOrder = new ArrayList<>();
    private ArrayList<EpisodeInput> episodes = new ArrayList<>();
    private String owner;
    private String name;
    private String visibility;
    private Integer followers;
    private Integer currentSongIndex;
    private Integer currentEpisodeIndex;
    private Integer createTimestamp;

    public Playlist(final ActionInput action) {
        this.songs = new ArrayList<SongInput>();
        this.initialOrder = new ArrayList<SongInput>();
        this.episodes = new ArrayList<EpisodeInput>();
        this.owner = action.getUsername();
        this.name = action.getPlaylistName();
        this.visibility = "public";
        this.followers = 0;
        this.currentSongIndex = 0;
        this.currentEpisodeIndex = 0;
        this.createTimestamp = action.getTimestamp();
    }
    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(final Integer followers) {
        this.followers = followers;
    }

    public Integer getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(final Integer currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public Integer getCurrentEpisodeIndex() {
        return currentEpisodeIndex;
    }

    public void setCurrentEpisodeIndex(final Integer currentEpisodeIndex) {
        this.currentEpisodeIndex = currentEpisodeIndex;
    }

    public ArrayList<SongInput> getInitialOrder() {
        return initialOrder;
    }

    public void setInitialOrder(final ArrayList<SongInput> initialOrder) {
        this.initialOrder = initialOrder;
    }

    public Integer getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(final Integer createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Playlist() {

    }
    /**
     * Plays the next song in the playlist.
     */
    public void playNextSong() {
        if (this.currentSongIndex < this.songs.size() - 1) {

            this.currentSongIndex++;
        }
    }

    /**
     * Plays the next episode in the series.
     */
    public void playNextEpisode() {
        if (this.currentEpisodeIndex < this.episodes.size() - 1) {
            this.currentEpisodeIndex++;
        }
    }

    /**
     *
     * @return
     */
    public int getTotalLikes() {
        return songs.stream().mapToInt(SongInput::getLikes).sum();
    }
}
