package globalwaves.recommendation;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.player.LoadResults;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;

public final class FansPlaylist {
    public FansPlaylist() {
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean updateRecommendations(final ActionInput action) {
        String artistName = new String();
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getUsername().equals(action.getUsername())) {
                if (loadResults.getLoadedSong() != null) {
                    artistName = loadResults.getLoadedSong().getArtist();
                } else if (loadResults.getLoadedAlbum() != null) {
                    artistName = loadResults.getLoadedAlbum().getUsername();
                } else if (loadResults.getLoadedPlaylist() != null) {
                    int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                    artistName = loadResults.getLoadedPlaylist().getSongs().get(idx).getArtist();
                }
            }
        }
        Playlist newPlaylist = new Playlist(action.getUsername(), artistName
                + " Fan Club recommendations", action.getTimestamp());
        boolean found = false;
        for (PlaylistsOwner playlistsOwner : Database.getInstance().getPlaylistsOwners()) {
            if (playlistsOwner.getOwner().equals(action.getUsername())) {
                playlistsOwner.getPlaylists().add(newPlaylist);
                found = true;
                break;
            }
        }
        if (!found) {
            PlaylistsOwner newOwner = new PlaylistsOwner(action.getUsername());
            newOwner.getPlaylists().add(newPlaylist);
            Database.getInstance().getPlaylistsOwners().add(newOwner);
        }
        Database.getInstance().getPublicPlaylists().add(newPlaylist);
        for (Recommendations recommendations : Database.getInstance().getRecommendations()) {
            if (recommendations.getUsername().equals(action.getUsername())) {
                recommendations.setLastRecommendation(action.getRecommendationType());
                recommendations.getPlaylistRecommend().add(newPlaylist);
                break;
            }
        }
        return true;
    }
}
