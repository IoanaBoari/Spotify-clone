package globalwaves.admin;

import fileio.input.SongInput;
import globalwaves.user.artist.Album;

import java.util.ArrayList;

public final class ShowAlbumResult {
    private String name;
    private ArrayList<String> songs = new ArrayList<>();

public ShowAlbumResult(final Album album) {
    this.name = album.getName();
    if (!album.getInitialOrder().isEmpty()) {
        for (SongInput song : album.getInitialOrder()) {
            this.songs.add(song.getName());
        }
    } else {
        for (SongInput song : album.getSongs()) {
            this.songs.add(song.getName());
        }
    }
}
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }
}
