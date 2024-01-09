package globalwaves.user.artist.merch;

import globalwaves.Database;

import java.util.ArrayList;

public final class OwnedMerch {
    private String username;
    private ArrayList<Merch> ownedmerchandise = new ArrayList<>();
    public OwnedMerch() {
    }
    public OwnedMerch(final String username) {
        this.username = username;
        this.ownedmerchandise = new ArrayList<>();
    }

    /**
     * Adds merchandise to the list of owned merchandise for a user.
     *
     * @param username The username of the user.
     * @param merch The merchandise to be added.
     */
    public void addMerchandise(final String username, final Merch merch) {
        boolean found = false;
        for (OwnedMerch ownedMerch : Database.getInstance().getOwnedMerchArrayList()) {
            if (ownedMerch.getUsername().equals(username)) {
                ownedMerch.getOwnedmerchandise().add(merch);
                found = true;
                break;
            }
        }
        if (!found) {
            OwnedMerch ownedMerch = new OwnedMerch(username);
            ownedMerch.getOwnedmerchandise().add(merch);
            Database.getInstance().getOwnedMerchArrayList().add(ownedMerch);
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public ArrayList<Merch> getOwnedmerchandise() {
        return ownedmerchandise;
    }

    public void setOwnedmerchandise(final ArrayList<Merch> ownedmerchandise) {
        this.ownedmerchandise = ownedmerchandise;
    }
}
