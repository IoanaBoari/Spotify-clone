package globalwaves.playlist;

import java.util.ArrayList;

public final class FollowedPlaylists {
    private ArrayList<Playlist> followedPlaylists = new ArrayList<>();
    private String user;

    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }
    public FollowedPlaylists() {

    }
}
