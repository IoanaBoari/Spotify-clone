package fileio.input;

import java.util.ArrayList;

public final class ActionInput {
    private String command;
    private String username;
    private Integer timestamp;
    private String type;
    private Filters filters;
    private Integer itemNumber;
    private Integer seed;
    private Integer playlistId;
    private String playlistName;
    private String nextPage;
    private Integer age;
    private String city;
    private String name;
    private Integer releaseYear;
    private String description;
    private ArrayList<SongInput> songs;
    private String date;
    private Integer price;
    private ArrayList<EpisodeInput> episodes;
    private String recommendationType;

    public ActionInput() {

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(final Filters filters) {
        this.filters = filters;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(final Integer seed) {
        this.seed = seed;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(final Integer playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
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

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(final String recommendationType) {
        this.recommendationType = recommendationType;
    }

    public ActionInput(final ActionInput actions) {
        this.command = actions.command;
        this.username = actions.username;
        this.timestamp = actions.timestamp;
        this.type = actions.type;
        this.filters = actions.filters;
        this.itemNumber = actions.itemNumber;
        this.seed = actions.seed;
        this.playlistId = actions.playlistId;
        this.playlistName = actions.playlistName;
        this.nextPage = actions.nextPage;
        this.age = actions.age;
        this.city = actions.city;
        this.name = actions.name;
        this.releaseYear = actions.releaseYear;
        this.description = actions.description;
        this.songs = actions.songs;
        this.date = actions.date;
        this.price = actions.price;
        this.episodes = actions.episodes;
        this.recommendationType = actions.recommendationType;
    }

}
