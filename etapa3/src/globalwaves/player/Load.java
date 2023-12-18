package globalwaves.player;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import globalwaves.playlist.Playlist;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Album;

public final class Load {
    public Load() {

    }

    /**
     * Loads the selected song from the provided select results.
     *
     * @param selectResults The select results containing the selected song.
     * @return The loaded song or null if no song is selected.
     */
    public SongInput doLoadSong(final SelectResults selectResults) {
        if (selectResults.getSelectSong() != null) {
            return selectResults.getSelectSong();
        } else {
            return null;
        }
    }

    /**
     * Loads the selected podcast from the provided select results.
     *
     * @param selectResults The select results containing the selected podcast.
     * @return The loaded podcast or null if no podcast is selected.
     */
    public PodcastInput doLoadPodcast(final SelectResults selectResults) {
        if (selectResults.getSelectPodcast() != null) {
            return selectResults.getSelectPodcast();
        } else {
            return null;
        }
    }

    /**
     * Loads the selected playlist from the provided select results.
     *
     * @param selectResults The select results containing the selected playlist.
     * @return The loaded playlist or null if no playlist is selected.
     */
    public Playlist doLoadPlaylist(final SelectResults selectResults) {
        if (selectResults.getSelectPlaylist() != null) {
            return selectResults.getSelectPlaylist();
        } else {
            return null;
        }
    }

    /**
     * Loads an album based on the provided select results.
     *
     * @param selectResults The select results containing the loaded album.
     * @return The loaded album or null if no album is selected.
     */
    public Album doLoadAlbum(final SelectResults selectResults) {
        if (selectResults.getSelectAlbum() != null) {
            return selectResults.getSelectAlbum();
        } else {
            return null;
        }
    }
}
