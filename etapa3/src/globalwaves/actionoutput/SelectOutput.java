package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;
import globalwaves.playlist.Playlist;
import globalwaves.searchbar.SearchResults;
import globalwaves.searchbar.Select;
import globalwaves.user.artist.Album;


import static globalwaves.Menu.getOutputs;

public final class SelectOutput {
    private SearchResults results = null;
    private SongInput songSelected = new SongInput();
    private PodcastInput podcastSelected = new PodcastInput();
    private Playlist playlistSelected = new Playlist();
    private Artist artistSelected = new Artist();
    private Album albumSelected = new Album();
    private Host hostSelected = new Host();

    public SearchResults getResults() {
        return results;
    }

    public void setResults(final SearchResults results) {
        this.results = results;
    }

    public SongInput getSongSelected() {
        return songSelected;
    }

    public void setSongSelected(final SongInput songSelected) {
        this.songSelected = songSelected;
    }

    public PodcastInput getPodcastSelected() {
        return podcastSelected;
    }

    public void setPodcastSelected(final PodcastInput podcastSelected) {
        this.podcastSelected = podcastSelected;
    }

    public Playlist getPlaylistSelected() {
        return playlistSelected;
    }

    public void setPlaylistSelected(final Playlist playlistSelected) {
        this.playlistSelected = playlistSelected;
    }

    public Artist getArtistSelected() {
        return artistSelected;
    }

    public void setArtistSelected(final Artist artistSelected) {
        this.artistSelected = artistSelected;
    }

    public Album getAlbumSelected() {
        return albumSelected;
    }

    public void setAlbumSelected(final Album albumSelected) {
        this.albumSelected = albumSelected;
    }

    public Host getHostSelected() {
        return hostSelected;
    }

    public void setHostSelected(final Host hostSelected) {
        this.hostSelected = hostSelected;
    }

    public SelectOutput(final SearchResults results) {
        this.results = results;
    }
    public SelectOutput() {

    }

    /**
     * Outputs the result of a user selection based on the last action performed (search).
     * Handles song, podcast, and playlist selections
     * and updates the corresponding selected objects.
     *
     * @param action    The ActionInput object representing the user's selection action.
     * @param lastAction The last action performed (should be "search" to proceed with selection).
     */

    public void doOutput(final ActionInput action, final String lastAction) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        UserInput currentUser = null;
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(action.getUsername())) {
                currentUser = user;
                if (user.getType().equals("user") && user.getMode().equals("offline")) {
                    object.put("message", action.getUsername() + " is offline.");
                    Menu.setOutputs(getOutputs().add(object));
                    return;
                }
            }
        }
        if (!lastAction.equals("search") || this.results == null) {
            object.put("message", "Please conduct a search before making a selection.");
        } else {
            Select select = new Select();
            if (this.results != null) {
                if (!this.results.getSongResults().isEmpty()) {
                    this.songSelected = select.doSelectSong(action, this.results);
                    if (this.songSelected == null) {
                        object.put("message", "The selected ID is too high.");
                    } else {
                        object.put("message", "Successfully selected "
                                + this.songSelected.getName() + ".");
                    }
                } else if (!this.results.getPodcastResults().isEmpty()) {
                    this.podcastSelected = select.doSelectPodcast(action, this.results);
                    if (this.podcastSelected == null) {
                        object.put("message", "The selected ID is too high.");
                    } else {
                        object.put("message", "Successfully selected "
                                + this.podcastSelected.getName() + ".");
                    }
                } else if (!this.results.getPlaylistResults().isEmpty()) {
                    this.playlistSelected = select.doSelectPlaylist(action, this.results);
                    if (this.playlistSelected == null) {
                        object.put("message", "The selected ID is too high.");
                    } else {
                        object.put("message", "Successfully selected "
                                + this.playlistSelected.getName() + ".");
                    }
                } else if (!this.results.getArtistsResults().isEmpty()) {
                    this.artistSelected = select.doSelectArtist(action, this.results);
                    if (this.artistSelected == null) {
                        object.put("message", "The selected ID is too high.");
                    } else {
                        currentUser.setCurrentPage("Artist");
                        object.put("message", "Successfully selected "
                                + this.artistSelected.getUsername() + "'s page.");
                    }
                } else if (!this.results.getAlbumsResults().isEmpty()) {
                    this.albumSelected = select.doSelectAlbum(action, this.results);
                    if (this.albumSelected == null) {
                        object.put("message", "The selected ID is too high.");
                    } else {
                        object.put("message", "Successfully selected "
                                + this.albumSelected.getName() + ".");
                    }
                } else if (!this.results.getHostsResults().isEmpty()) {
                    this.hostSelected = select.doSelectHost(action, this.results);
                    if (this.hostSelected == null) {
                        object.put("message", "The selected ID is too high.");
                    } else {
                        currentUser.setCurrentPage("Host");
                        object.put("message", "Successfully selected "
                                + this.hostSelected.getUsername() + "'s page.");
                    }
                } else {
                    object.put("message", "The selected ID is too high.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
