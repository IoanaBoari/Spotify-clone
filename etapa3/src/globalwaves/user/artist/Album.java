package globalwaves.user.artist;

import fileio.input.ActionInput;
import fileio.input.SongInput;
import globalwaves.Database;

import java.util.ArrayList;

public final class Album {
    private String username;
    private Integer createTimestamp;
    private String name;
    private Integer releaseYear;
    private String description;
    private Integer currentSongIndex;
    private ArrayList<SongInput> songs = new ArrayList<>();
    private ArrayList<SongInput> initialOrder = new ArrayList<>();
    public Album() {

    }
    public Album(final ActionInput action) {
        this.username = action.getUsername();
        this.createTimestamp = action.getTimestamp();
        this.name = action.getName();
        this.releaseYear = action.getReleaseYear();
        this.description = action.getDescription();
        this.currentSongIndex = 0;
        for (SongInput song : action.getSongs()) {
            songs.add(song);
            int find = 0;
            for (SongInput songInput : Database.getInstance().getLibrary().getSongs()) {
                if (song.getName().equals(songInput.getName())) {
                    find = 1;
                }
            }
            if (find == 0) {
                Database.getInstance().getLibrary().getSongs().add(song);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Integer getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(final Integer createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Integer getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(final Integer currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public ArrayList<SongInput> getInitialOrder() {
        return initialOrder;
    }

    public void setInitialOrder(final ArrayList<SongInput> initialOrder) {
        this.initialOrder = initialOrder;
    }

    /**
     * Returns the total number of likes for all songs in the current album.
     *
     * @return The total number of likes.
     */
    public int getTotalLikes() {
        int result = 0;
        for (SongInput song : this.getSongs()) {
            result += song.getLikes();
        }
        return result;
    }

    /**
     * Plays the next song in the album, if available.
     */
    public void playNextSong() {
        if (this.currentSongIndex < this.songs.size() - 1) {
            this.currentSongIndex++;
        }
    }
}
