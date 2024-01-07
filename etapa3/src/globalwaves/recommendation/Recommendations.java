package globalwaves.recommendation;

import fileio.input.SongInput;
import globalwaves.playlist.Playlist;

import java.util.ArrayList;

public final class Recommendations {
    private String username;
    private ArrayList<SongInput> songRecommendations = new ArrayList<>();
    private ArrayList<Playlist> playlistRecommend = new ArrayList<>();
    private String lastRecommendation;

    public Recommendations() {
    }

    public Recommendations(final String username) {
        this.username = username;
        this.songRecommendations = new ArrayList<>();
        this.playlistRecommend = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public ArrayList<SongInput> getSongRecommendations() {
        return songRecommendations;
    }

    public void setSongRecommendations(final ArrayList<SongInput> songRecommendations) {
        this.songRecommendations = songRecommendations;
    }

    public ArrayList<Playlist> getPlaylistRecommend() {
        return playlistRecommend;
    }

    public void setPlaylistRecommend(final ArrayList<Playlist> playlistRecommend) {
        this.playlistRecommend = playlistRecommend;
    }

    public String getLastRecommendation() {
        return lastRecommendation;
    }

    public void setLastRecommendation(final String lastRecommendation) {
        this.lastRecommendation = lastRecommendation;
    }
}
