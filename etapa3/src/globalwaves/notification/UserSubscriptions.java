package globalwaves.notification;

import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;

import java.util.ArrayList;

public final class UserSubscriptions {
    private String username;
    private ArrayList<Artist> subscribedArtists = new ArrayList<>();
    private ArrayList<Host> subscribedHosts = new ArrayList<>();
    public UserSubscriptions(final String username) {
        this.username = username;
        this.subscribedArtists = new ArrayList<>();
        this.subscribedHosts = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public ArrayList<Artist> getSubscribedArtists() {
        return subscribedArtists;
    }

    public void setSubscribedArtists(final ArrayList<Artist> subscribedArtists) {
        this.subscribedArtists = subscribedArtists;
    }

    public ArrayList<Host> getSubscribedHosts() {
        return subscribedHosts;
    }

    public void setSubscribedHosts(final ArrayList<Host> subscribedHosts) {
        this.subscribedHosts = subscribedHosts;
    }
}
