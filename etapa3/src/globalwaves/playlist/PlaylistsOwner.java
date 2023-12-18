package globalwaves.playlist;

import java.util.ArrayList;

public final class PlaylistsOwner {
    private String owner;
    private ArrayList<Playlist> playlists;
    public PlaylistsOwner(final String name) {
        this.owner = name;
        this.playlists = new ArrayList<>();
    }
    public PlaylistsOwner() {
        this.playlists = new ArrayList<>();
    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(final ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
}
