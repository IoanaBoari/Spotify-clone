package globalwaves.playlist;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class SortPlaylists {
    public SortPlaylists() {

    }

    /**
     * Sorts the provided list of playlists based on their creation timestamps.
     *
     * @param playlists The list of playlists to be sorted.
     */
    public void doSortPlaylist(final ArrayList<Playlist> playlists) {
        Collections.sort(playlists, new Comparator<Playlist>() {
            @Override
            public int compare(final Playlist p1, final Playlist p2) {
                return Long.compare(p1.getCreateTimestamp(), p2.getCreateTimestamp());
            }
        });
    }
}
