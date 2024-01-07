package globalwaves.pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.*;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.recommendation.Recommendations;
import globalwaves.user.artist.Artist;
import globalwaves.admin.CheckOffline;
import globalwaves.user.host.Host;
import globalwaves.commands.Command;
import globalwaves.player.LikedSongs;
import globalwaves.playlist.FollowedPlaylists;
import globalwaves.playlist.Playlist;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Album;
import globalwaves.user.artist.Event;
import globalwaves.user.artist.merch.Merch;
import globalwaves.user.host.Announcement;

import java.util.ArrayList;

public final class PrintCurrentPage implements Command {
    private final Integer maxNrNames = 5;
    public PrintCurrentPage() {

    }

    /**
     * Executes the provided action, generating a response message
     * based on the user's current page.
     * Checks if the user is offline using the CheckOffline class.
     * If the user is offline, returns an appropriate message.
     * Otherwise, retrieves user information and constructs a response message
     * based on the current page.
     *
     * @param action The action input specifying the user, command details, and the page.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            new UpdateStats().doUpdateCurrent(action);
            ArrayList<String> likedSongsNames = new ArrayList<>();
            ArrayList<String> followedPlaylistsNames = new ArrayList<>();
            ArrayList<String> songsRecommendations = new ArrayList<>();
            ArrayList<String> playlistsRecommendations = new ArrayList<>();
            UserInput currentUser = null;
            for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
                if (userInput.getUsername().equals(action.getUsername())) {
                    currentUser = userInput;
                    break;
                }
            }
            if (currentUser != null) {
                LikedSongs likedSongsCurrent = null;
                for (LikedSongs likedSongs : Database.getInstance().getLikedSongsUsers()) {
                    if (likedSongs.getUser().equals(currentUser.getUsername())) {
                        likedSongsCurrent = new LikedSongs();
                        for (SongInput songInput : likedSongs.getLikedSongs()) {
                            likedSongsCurrent.getLikedSongs().add(songInput);
                        }
                        break;
                    }
                }
                FollowedPlaylists followedPlaylistsCurrent = null;
                for (FollowedPlaylists followedPlaylists : Database.getInstance().
                        getFollowedPlaylistsUsers()) {
                    if (followedPlaylists.getUser().equals(currentUser.getUsername())) {
                        followedPlaylistsCurrent = new FollowedPlaylists();
                        for (Playlist playlist : followedPlaylists.getFollowedPlaylists()) {
                            followedPlaylistsCurrent.getFollowedPlaylists().add(playlist);
                        }
                        break;
                    }
                }

                if (currentUser.getCurrentPage().equals("Home")) {
                    if (likedSongsCurrent != null) {
                        likedSongsCurrent.getLikedSongs().sort((a, b) ->
                                b.getLikes() - a.getLikes());
                        int count = 0;
                        for (SongInput songInput : likedSongsCurrent.getLikedSongs()) {
                            likedSongsNames.add(songInput.getName());
                            count++;
                            if (count == maxNrNames) {
                                break;
                            }
                        }
                    }
                    if (followedPlaylistsCurrent != null) {
                        followedPlaylistsCurrent.getFollowedPlaylists().sort((p1, p2) -> Integer.
                                compare(p2.getTotalLikes(), p1.getTotalLikes()));
                        int count = 0;
                        for (Playlist playlist : followedPlaylistsCurrent.getFollowedPlaylists()) {
                            followedPlaylistsNames.add(playlist.getName());
                            count++;
                            if (count == maxNrNames) {
                                break;
                            }
                        }
                    }
                    for (Recommendations recommendations : Database.getInstance().
                            getRecommendations()) {
                        if (recommendations.getUsername().equals(action.getUsername())) {
                            for (SongInput song : recommendations.getSongRecommendations()) {
                                songsRecommendations.add(song.getName());
                            }
                            for (Playlist playlist : recommendations.getPlaylistRecommend()) {
                                playlistsRecommendations.add(playlist.getName());
                            }
                        }
                    }
                    object.put("message", "Liked songs:\n\t" + likedSongsNames
                            + "\n\nFollowed playlists:\n\t" + followedPlaylistsNames
                            + "\n\nSong recommendations:\n\t" + songsRecommendations
                            + "\n\nPlaylists recommendations:\n\t" + playlistsRecommendations);
                } else if (currentUser.getCurrentPage().equals("LikedContent")) {
                    StringBuilder likedSongs = new StringBuilder("[");
                    StringBuilder followedPlaylists = new StringBuilder("[");
                    if (likedSongsCurrent != null) {
                        for (SongInput song : likedSongsCurrent.getLikedSongs()) {
                            likedSongs.append(song.getName()).append(" - ").
                                    append(song.getArtist()).append(", ");
                        }
                    }
                    if (likedSongs.length() > 1) {
                        likedSongs.delete(likedSongs.length() - 2, likedSongs.length());
                    }
                    likedSongs.append("]");
                    if (followedPlaylistsCurrent != null) {
                        for (Playlist playlist : followedPlaylistsCurrent.getFollowedPlaylists()) {
                            followedPlaylists.append(playlist.getName()).append(" - ").
                                    append(playlist.getOwner()).append(", ");
                        }
                    }
                    if (followedPlaylists.length() > 1) {
                        followedPlaylists.delete(followedPlaylists.length() - 2,
                                followedPlaylists.length());
                    }
                    followedPlaylists.append("]");
                    object.put("message", "Liked songs:\n\t" + likedSongs.toString()
                            + "\n\nFollowed playlists:\n\t" + followedPlaylists.toString());
                } else if (currentUser.getCurrentPage().equals("Artist")) {
                    Artist artist = new Artist();
                    for (SelectResults pageResults : Database.getInstance().getCurrentPages()) {
                        if (pageResults.getUsername().equals(currentUser.getUsername())) {
                            artist = pageResults.getSelectArtist();
                            break;
                        }
                    }
                    ArrayList<String> albumsNames = new ArrayList<>();
                    StringBuilder events = new StringBuilder("[");
                    StringBuilder merchandise = new StringBuilder("[");
                    for (Album album : artist.getAlbums()) {
                        albumsNames.add(album.getName());
                    }
                    for (Event event : artist.getEvents()) {
                        events.append(event.getName()).append(" - ").append(event.getDate()).
                                append(":\n\t").append(event.getDescription()).append(", ");
                    }
                    if (events.length() > 1) {
                        events.delete(events.length() - 2, events.length());
                    }
                    events.append("]");
                    for (Merch merch : artist.getMerch()) {
                        merchandise.append(merch.getName()).append(" - ").append(merch.getPrice()).
                                append(":\n\t").append(merch.getDescription()).append(", ");
                    }
                    if (merchandise.length() > 1) {
                        merchandise.delete(merchandise.length() - 2, merchandise.length());
                    }
                    merchandise.append("]");
                    object.put("message", "Albums:\n\t" + albumsNames
                            + "\n\nMerch:\n\t" + merchandise.toString() + "\n\nEvents:\n\t"
                            + events.toString());
                } else if (currentUser.getCurrentPage().equals("Host")) {
                    Host host = new Host();
                    for (SelectResults pageResults : Database.getInstance().getCurrentPages()) {
                        if (pageResults.getUsername().equals(currentUser.getUsername())) {
                            host = pageResults.getSelectHost();
                            break;
                        }
                    }
                    StringBuilder podcasts = new StringBuilder("[");
                    for (PodcastInput podcastInput : host.getPodcasts()) {
                        podcasts.append(podcastInput.getName()).append(":\n\t[");
                        for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                            podcasts.append(episodeInput.getName()).append(" - ").
                                    append(episodeInput.getDescription()).append(", ");
                        }
                        if (podcasts.length() > 1) {
                            podcasts.delete(podcasts.length() - 2, podcasts.length());
                        }
                        podcasts.append("]\n, ");
                    }
                    if (podcasts.length() > 1) {
                        podcasts.delete(podcasts.length() - 2, podcasts.length());
                    }
                    podcasts.append("]");
                    StringBuilder announcements = new StringBuilder("[");
                    for (Announcement announcement : host.getAnnouncements()) {
                        announcements.append(announcement.getName()).append(":\n\t").
                                append(announcement.getDescription()).append("\n, ");
                    }
                    if (announcements.length() > 1) {
                        announcements.delete(announcements.length() - 2, announcements.length());
                    }
                    announcements.append("]");
                    object.put("message", "Podcasts:\n\t" + podcasts.toString()
                            + "\n\nAnnouncements:\n\t" + announcements.toString());
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
