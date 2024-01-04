package globalwaves.user.artist;

import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.user.artist.merch.Merch;


import java.util.ArrayList;

public final class Artist extends UserInput {
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Merch> merch = new ArrayList<>();
    public Artist() {

    }
    public Artist(final ActionInput action) {
        super(action);
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(final ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Merch> getMerch() {
        return merch;
    }

    public void setMerch(final ArrayList<Merch> merch) {
        this.merch = merch;
    }

    /**
     * Calculates and returns the total number of likes across all albums associated with
     * the artist.
     *
     * @return The total number of likes for all albums of the artist.
     */
    public int getAllLikes() {
        int result = 0;
        for (Album album : this.getAlbums()) {
            result += album.getTotalLikes();
        }
        return result;
    }
}
