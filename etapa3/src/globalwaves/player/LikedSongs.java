package globalwaves.player;

import fileio.input.SongInput;

import java.util.ArrayList;

public final class LikedSongs {
    private ArrayList<SongInput> likedSongs = new ArrayList<>();
    private String user;
    public ArrayList<SongInput> getLikedSongs() {
        return likedSongs;
    }
    public void setLikedSongs(final ArrayList<SongInput> likedSongs) {
        this.likedSongs = likedSongs;
    }
    public String getUser() {
        return user;
    }
    public void setUser(final String user) {
        this.user = user;
    }
    public LikedSongs() {

    }

}
