package globalwaves.admin.end;

import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.user.artist.Album;
import globalwaves.user.artist.Artist;
import globalwaves.user.artist.merch.Merch;
import globalwaves.user.artist.merch.OwnedMerch;
import globalwaves.userstats.Listener;

public final class EndProgramResults implements Comparable<EndProgramResults> {
    private final Double credit = 1000000.0;
    private final Double round = 100.0;
    private String username;
    private double songRevenue;
    private double merchRevenue;
    private String mostProfitableSong;
    private Integer ranking;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public double getSongRevenue() {
        return songRevenue;
    }

    public void setSongRevenue(final double songRevenue) {
        this.songRevenue = songRevenue;
    }

    public double getMerchRevenue() {
        return merchRevenue;
    }

    public void setMerchRevenue(final double merchRevenue) {
        this.merchRevenue = merchRevenue;
    }

    public String getMostProfitableSong() {
        return mostProfitableSong;
    }

    public void setMostProfitableSong(final String mostProfitableSong) {
        this.mostProfitableSong = mostProfitableSong;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(final Integer ranking) {
        this.ranking = ranking;
    }

    public EndProgramResults() {
    }
    @Override
    public int compareTo(final EndProgramResults other) {
        // Comparație în ordine descrescătoare în funcție de suma songRevenue + merchRevenue
        double thisTotalRevenue = this.getSongRevenue() + this.getMerchRevenue();
        double otherTotalRevenue = other.getSongRevenue() + other.getMerchRevenue();

        // Comparare descrescătoare în funcție de totalRevenue
        int revenueComparison = Double.compare(otherTotalRevenue, thisTotalRevenue);

        // Dacă totalRevenue sunt egale, să se sorteze alfabetic
        if (revenueComparison == 0) {
            String thisName = this.getUsername();
            String otherName = other.getUsername();
            return thisName.compareTo(otherName);
        }

        return revenueComparison;
    }
    public EndProgramResults(final String artist) {
    this.username = artist;
    this.songRevenue = calculateSongRevenue(artist);
    this.merchRevenue = calculateMerchRevenue(artist);
    this.mostProfitableSong = calculateMostProfitableSong(artist);
    }

    /**
     *
     * @param artist
     * @return
     */
    public double calculateSongRevenue(final String artist) {
        double revenue = 0.0;
        for (Listener listener : Database.getInstance().getPremiumListeners()) {
            for (SongInput song : listener.getSongsloaded()) {
                if (song.getArtist().equals(artist)) {
                    revenue += (credit / listener.getSongsloaded().size());
                    song.setRevenue(song.getRevenue()
                            + (credit / listener.getSongsloaded().size()));
                }
            }
        }
        revenue = Math.round(revenue * round) / round;
        return revenue;
    }

    /**
     *
     * @param artist
     * @return
     */
    public double calculateMerchRevenue(final String artist) {
        double revenue = 0.0;
        for (OwnedMerch ownedMerch : Database.getInstance().getOwnedMerchArrayList()) {
            for (Merch merch : ownedMerch.getOwnedmerchandise()) {
                if (merch.getUsername().equals(artist)) {
                    revenue += merch.getPrice();
                }
            }
        }
        revenue = Math.round(revenue * round) / round;
        return revenue;
    }

    /**
     *
     * @param artist
     * @return
     */
    public String calculateMostProfitableSong(final String artist) {
        SongInput mostProfitableSong = null;
        UserInput currentuser = null;
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(artist)) {
                currentuser = user;
                break;
            }
        }
        Artist currentArtist = (Artist) currentuser;
        for (Album album : currentArtist.getAlbums()) {
            for (SongInput song : album.getSongs()) {
                if (song.getRevenue() > 0.0 && (mostProfitableSong == null
                        || mostProfitableSong.getRevenue() < song.getRevenue())) {
                    mostProfitableSong = song;
                }
            }
        }
        if (mostProfitableSong == null) {
            return "N/A";
        } else {
            return mostProfitableSong.getName();
        }
    }
}
