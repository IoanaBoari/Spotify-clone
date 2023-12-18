package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.Artist;
import globalwaves.admin.CheckOffline;
import globalwaves.admin.Host;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.searchbar.*;
import globalwaves.user.artist.Album;

import java.util.ArrayList;

import static globalwaves.Menu.getOutputs;

public final class SearchOutput {
    private final Integer five = 5;
    private ArrayList<SongInput> songResults = new ArrayList<>();
    private ArrayList<PodcastInput> podcastResults = new ArrayList<>();
    private ArrayList<Playlist> playlistResults = new ArrayList<>();
    private ArrayList<Artist> artistsResults = new ArrayList<>();
    private ArrayList<Album> albumsResults = new ArrayList<>();
    private ArrayList<Host> hostsResults = new ArrayList<>();

    public ArrayList<SongInput> getSongResults() {
        return songResults;
    }

    public void setSongResults(final ArrayList<SongInput> songResults) {
        this.songResults = songResults;
    }

    public ArrayList<PodcastInput> getPodcastResults() {
        return podcastResults;
    }

    public void setPodcastResults(final ArrayList<PodcastInput> podcastResults) {
        this.podcastResults = podcastResults;
    }

    public ArrayList<Playlist> getPlaylistResults() {
        return playlistResults;
    }

    public void setPlaylistResults(final ArrayList<Playlist> playlistResults) {
        this.playlistResults = playlistResults;
    }

    public ArrayList<Artist> getArtistsResults() {
        return artistsResults;
    }

    public void setArtistsResults(final ArrayList<Artist> artistsResults) {
        this.artistsResults = artistsResults;
    }

    public ArrayList<Album> getAlbumsResults() {
        return albumsResults;
    }

    public void setAlbumsResults(final ArrayList<Album> albumsResults) {
        this.albumsResults = albumsResults;
    }

    public ArrayList<Host> getHostsResults() {
        return hostsResults;
    }

    public void setHostsResults(final ArrayList<Host> hostsResults) {
        this.hostsResults = hostsResults;
    }

    public Integer getFIVE() {
        return five;
    }

    public SearchOutput() {

    }

    /**
     * Outputs search results based on the provided filters
     * and search type (song, podcast, or playlist).
     * Displays the first five results and adds them to the corresponding result lists
     * (songResults, podcastResults, playlistResults).
     *
     * @param action          The ActionInput object representing the user's search action.
     *
     * @param owner           The PlaylistsOwner object representing the owner of the playlists.
     */

    public void doOutput(final ActionInput action, final PlaylistsOwner owner) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (new CheckOffline().checkOffline(action) == 1) {
            ObjectNode object = objectMapper.createObjectNode();
            object.put("command", action.getCommand());
            object.put("user", action.getUsername());
            object.put("timestamp", action.getTimestamp());
            object.put("message", action.getUsername() + " is offline.");
            ArrayList<String> results = new ArrayList<>();
            object.put("results", objectMapper.valueToTree(results));
            Menu.setOutputs(getOutputs().add(object));
            return;
        }
        switch (action.getType()) {
            case "song" -> {
                SearchSong song = new SearchSong();
                ArrayList<SongInput> songsSearched = song.doSearch(action.getFilters());
                ArrayList<String> songNames = new ArrayList<>();
                ObjectNode songObject = objectMapper.createObjectNode();
                for (SongInput songIter : songsSearched) {
                    if (songNames.size() < five) {
                        songNames.add(songIter.getName());
                        songResults.add(songIter);
                    }
                }
                songObject.put("command", action.getCommand());
                songObject.put("user", action.getUsername());
                songObject.put("timestamp", action.getTimestamp());
                songObject.put("message", "Search returned " + songNames.size() + " results");
                songObject.put("results", objectMapper.valueToTree(songNames));
                Menu.setOutputs(getOutputs().add(songObject));
            }
            case "podcast" -> {
                SearchPodcast podcast = new SearchPodcast();
                ArrayList<PodcastInput> podcastsSearched = podcast.doSearch(action.getFilters());
                ArrayList<String> podcastNames = new ArrayList<>();
                ObjectNode podcastObject = objectMapper.createObjectNode();
                for (PodcastInput podcastIter : podcastsSearched) {
                    if (podcastNames.size() < five) {
                        podcastNames.add(podcastIter.getName());
                        podcastResults.add(podcastIter);
                    }
                }
                podcastObject.put("command", action.getCommand());
                podcastObject.put("user", action.getUsername());
                podcastObject.put("timestamp", action.getTimestamp());
                podcastObject.put("message", "Search returned " + podcastNames.size()
                        + " results");
                podcastObject.put("results", objectMapper.valueToTree(podcastNames));
                Menu.setOutputs(getOutputs().add(podcastObject));
            }
            case "playlist" -> {
                SearchPlaylist searchPlaylist = new SearchPlaylist();
                ArrayList<Playlist> playlistsSearched
                        = searchPlaylist.doSearch(action.getFilters(),
                        Database.getInstance().getPublicPlaylists(), owner);
                ArrayList<String> playlistNames = new ArrayList<>();
                ObjectNode playlistObject = objectMapper.createObjectNode();
                for (Playlist playlist : playlistsSearched) {
                    if (playlistNames.size() < five) {
                        playlistNames.add(playlist.getName());
                        playlistResults.add(playlist);
                    }
                }
                playlistObject.put("command", action.getCommand());
                playlistObject.put("user", action.getUsername());
                playlistObject.put("timestamp", action.getTimestamp());
                playlistObject.put("message", "Search returned "
                        + playlistNames.size() + " results");
                playlistObject.put("results", objectMapper.valueToTree(playlistNames));
                Menu.setOutputs(getOutputs().add(playlistObject));
            }
            case "artist" -> {
                SearchArtist searchArtist = new SearchArtist();
                ArrayList<Artist> artistsSearched = searchArtist.doSearch(action.getFilters());
                ArrayList<String> artistNames = new ArrayList<>();
                ObjectNode artistObject = objectMapper.createObjectNode();
                for (Artist artist : artistsSearched) {
                    if (artistNames.size() < five) {
                        artistNames.add(artist.getUsername());
                        artistsResults.add(artist);
                    }
                }
                artistObject.put("command", action.getCommand());
                artistObject.put("user", action.getUsername());
                artistObject.put("timestamp", action.getTimestamp());
                artistObject.put("message", "Search returned "
                        + artistNames.size() + " results");
                artistObject.put("results", objectMapper.valueToTree(artistNames));
                Menu.setOutputs(getOutputs().add(artistObject));
            }
            case "album" -> {
                SearchAlbum searchAlbum = new SearchAlbum();
                ArrayList<Album> albumsSearched = searchAlbum.doSearch(action.getFilters());
                ArrayList<String> albumNames = new ArrayList<>();
                ObjectNode albumObject = objectMapper.createObjectNode();
                for (Album album : albumsSearched) {
                    if (albumNames.size() < five) {
                        albumNames.add(album.getName());
                        albumsResults.add(album);
                    }
                }
                albumObject.put("command", action.getCommand());
                albumObject.put("user", action.getUsername());
                albumObject.put("timestamp", action.getTimestamp());
                albumObject.put("message", "Search returned "
                        + albumNames.size() + " results");
                albumObject.put("results", objectMapper.valueToTree(albumNames));
                Menu.setOutputs(getOutputs().add(albumObject));
            }
            case "host" -> {
                SearchHost searchHost = new SearchHost();
                ArrayList<Host> hostsSearched = searchHost.doSearch(action.getFilters());
                ArrayList<String> hostNames = new ArrayList<>();
                ObjectNode hostObject = objectMapper.createObjectNode();
                for (Host host : hostsSearched) {
                    if (hostNames.size() < five) {
                        hostNames.add(host.getUsername());
                        hostsResults.add(host);
                    }
                }
                hostObject.put("command", action.getCommand());
                hostObject.put("user", action.getUsername());
                hostObject.put("timestamp", action.getTimestamp());
                hostObject.put("message", "Search returned "
                        + hostNames.size() + " results");
                hostObject.put("results", objectMapper.valueToTree(hostNames));
                Menu.setOutputs(getOutputs().add(hostObject));
            }
            default -> {
            }
        }
    }
}
