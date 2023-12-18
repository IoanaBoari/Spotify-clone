package globalwaves.player;

import fileio.input.ActionInput;
import fileio.input.SongInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public final class Shuffle {
    public Shuffle() {

    }
    /**
     * Changes the shuffle mode for the current playlist
     * and rearranges the playlist order accordingly.
     * If shuffle mode is turned off, the playlist order is reset to its initial order.
     * If shuffle mode is turned on, the playlist order is shuffled using the provided seed.
     *
     * @param loadResults The LoadResults object containing information
     *                    about the current state of playlist playback.
     * @param action      The ActionInput object containing user input information,
     *                    including the shuffle seed.
     */

    public void shufflePlaylist(final LoadResults loadResults,
                                            final ActionInput action) {
        int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
        SongInput currentSong = loadResults.getLoadedPlaylist().getSongs().get(idx);
       if (loadResults.getStats().isShuffle()) {
           loadResults.getStats().setShuffle(false);
           loadResults.getLoadedPlaylist().setSongs(loadResults.getLoadedPlaylist().
                   getInitialOrder());
       } else {
           loadResults.getLoadedPlaylist().setInitialOrder(new ArrayList<>(loadResults.
                   getLoadedPlaylist().getSongs()));
           loadResults.getStats().setShuffle(true);
           Random random = new Random(action.getSeed());
           Collections.shuffle(loadResults.getLoadedPlaylist().getSongs(), random);
       }
        for (int index = 0; index < loadResults.getLoadedPlaylist().getSongs().size(); index++) {
            if (currentSong.getName().equals(loadResults.getLoadedPlaylist().getSongs().
                    get(index).getName())) {
                loadResults.getLoadedPlaylist().setCurrentSongIndex(index);
                break;
            }
        }
    }

    /**
     * Toggles shuffle mode for the loaded album based on the provided load results and action.
     * If shuffle is enabled, it shuffles the song order using the specified seed.
     * If shuffle is disabled, it restores the initial order of songs.
     * Adjusts the current song index to match the current song after shuffling.
     *
     * @param loadResults The load results containing the loaded album and playback statistics.
     * @param action      The action input specifying the shuffle seed.
     */
    public void shuffleAlbum(final LoadResults loadResults,
                                final ActionInput action) {
        int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
        SongInput currentSong = loadResults.getLoadedAlbum().getSongs().get(idx);
        if (loadResults.getStats().isShuffle()) {
            loadResults.getStats().setShuffle(false);
            loadResults.getLoadedAlbum().setSongs(loadResults.getLoadedAlbum().
                    getInitialOrder());
        } else {
            loadResults.getLoadedAlbum().setInitialOrder(new ArrayList<>(loadResults.
                    getLoadedAlbum().getSongs()));
            loadResults.getStats().setShuffle(true);
            Random random = new Random(action.getSeed());
            Collections.shuffle(loadResults.getLoadedAlbum().getSongs(), random);
        }
        for (int index = 0; index < loadResults.getLoadedAlbum().getSongs().size(); index++) {
            if (currentSong.getName().equals(loadResults.getLoadedAlbum().getSongs().
                    get(index).getName())) {
                loadResults.getLoadedAlbum().setCurrentSongIndex(index);
                break;
            }
        }
    }
}
