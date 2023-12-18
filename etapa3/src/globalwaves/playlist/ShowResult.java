package globalwaves.playlist;

import fileio.input.SongInput;

import java.util.ArrayList;

public final class ShowResult {
    private String name;
    private ArrayList<String> songs = new ArrayList<>();
    private String visibility;
    private Integer followers;

    /**
     * Constructs a ShowResult object based on the provided playlist.
     *
     * @param playlist The playlist to generate results from.
     */
    public ShowResult(final Playlist playlist) {
        this.name = playlist.getName();
        if (!playlist.getInitialOrder().isEmpty()) {
            for (SongInput song : playlist.getInitialOrder()) {
                this.songs.add(song.getName());
            }
        } else {
            for (SongInput song : playlist.getSongs()) {
                this.songs.add(song.getName());
            }
        }
        this.visibility = playlist.getVisibility();
        this.followers = playlist.getFollowers();
    }
    public ShowResult() {

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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(final Integer followers) {
        this.followers = followers;
    }
}
