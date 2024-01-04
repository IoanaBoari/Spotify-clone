package globalwaves.notification;

import globalwaves.Database;
import globalwaves.user.artist.Album;
import globalwaves.user.artist.Event;
import globalwaves.user.artist.merch.Merch;

import java.util.ArrayList;

public final class Notification {
    private String username;
    private ArrayList<Album> newAlbums = new ArrayList<>();
    private ArrayList<Merch> newMerch = new ArrayList<>();
    private ArrayList<Event> newEvents = new ArrayList<>();

    public Notification() {
    }

    public Notification(final String username) {
        this.username = username;
        newAlbums = new ArrayList<>();
        newMerch = new ArrayList<>();
        newEvents = new ArrayList<>();
    }

    /**
     *
     * @param username
     * @param album
     */
    public void addNotificationAlbum(final String username, final Album album) {
        boolean found = false;
        for (Notification notification : Database.getInstance().getNewNotifications()) {
            if (notification.getUsername().equals(this.username)) {
                notification.getNewAlbums().add(album);
                found = true;
                break;
            }
        }
        if (!found) {
            Notification newNotification = new Notification(username);
            newNotification.getNewAlbums().add(album);
            Database.getInstance().getNewNotifications().add(newNotification);
        }
    }

    /**
     *
     * @param username
     * @param merch
     */
    public void addNotificationMerch(final String username, final Merch merch) {
        boolean found = false;
        for (Notification notification : Database.getInstance().getNewNotifications()) {
            if (notification.getUsername().equals(this.username)) {
                notification.getNewMerch().add(merch);
                found = true;
                break;
            }
        }
        if (!found) {
            Notification newNotification = new Notification(username);
            newNotification.getNewMerch().add(merch);
            Database.getInstance().getNewNotifications().add(newNotification);
        }
    }

    /**
     *
     * @param username
     * @param event
     */
    public void addNotificationEvent(final String username, final Event event) {
        boolean found = false;
        for (Notification notification : Database.getInstance().getNewNotifications()) {
            if (notification.getUsername().equals(this.username)) {
                notification.getNewEvents().add(event);
                found = true;
                break;
            }
        }
        if (!found) {
            Notification newNotification = new Notification(username);
            newNotification.getNewEvents().add(event);
            Database.getInstance().getNewNotifications().add(newNotification);
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public ArrayList<Album> getNewAlbums() {
        return newAlbums;
    }

    public void setNewAlbums(final ArrayList<Album> newAlbums) {
        this.newAlbums = newAlbums;
    }

    public ArrayList<Merch> getNewMerch() {
        return newMerch;
    }

    public void setNewMerch(final ArrayList<Merch> newMerch) {
        this.newMerch = newMerch;
    }

    public ArrayList<Event> getNewEvents() {
        return newEvents;
    }

    public void setNewEvents(final ArrayList<Event> newEvents) {
        this.newEvents = newEvents;
    }
}
