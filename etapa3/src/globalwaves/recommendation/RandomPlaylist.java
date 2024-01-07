package globalwaves.recommendation;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;

public final class RandomPlaylist {
    public RandomPlaylist() {
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean updateRecommendations(final ActionInput action) {
        action.setPlaylistName(action.getUsername() + "'s recommendations");
        Playlist newPlaylist = new Playlist(action.getUsername(),
                action.getUsername() + "'s recommendations", action.getTimestamp());
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
