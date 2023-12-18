package globalwaves.admin.delete;

import globalwaves.Database;
import globalwaves.player.LoadResults;
import globalwaves.user.artist.Album;


public final class DeleteAlbum {
    public DeleteAlbum() {

    }
    /**
     * Deletes an album from the database, including related results.
     *
     * @param deleteAlbum The Album object representing the album to be deleted.
     * @return True if the album was successfully deleted, false otherwise.
     */
    public boolean deleteAlbum(final Album deleteAlbum) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedAlbum() != null
                    && loadResults.getLoadedAlbum().getName().equals(deleteAlbum.getName())) {
                return false;
            }
            if (loadResults.getLoadedSong() != null
                    && loadResults.getLoadedSong().getAlbum().equals(deleteAlbum.getName())) {
                return false;
            }
            for (int idx = 0; idx < deleteAlbum.getSongs().size(); idx++) {
                if (loadResults.getLoadedPlaylist() != null
                        && loadResults.getLoadedPlaylist().getSongs().
                        contains(deleteAlbum.getSongs().get(idx))) {
                    return false;
                }
            }
        }
        return true;
    }
}
