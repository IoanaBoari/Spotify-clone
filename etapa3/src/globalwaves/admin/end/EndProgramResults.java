package globalwaves.admin.end;

public final class EndProgramResults implements Comparable<EndProgramResults> {
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
        return Double.compare(otherTotalRevenue, thisTotalRevenue);
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
        return 0;
    }

    /**
     *
     * @param artist
     * @return
     */
    public double calculateMerchRevenue(final String artist) {
        return 0;
    }

    /**
     *
     * @param artist
     * @return
     */
    public String calculateMostProfitableSong(final String artist) {
        return "N/A";
    }
}
