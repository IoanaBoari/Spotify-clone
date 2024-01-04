package globalwaves.admin.delete;

import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.player.LoadResults;
import globalwaves.user.artist.Album;
import globalwaves.user.artist.Artist;


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
        for (SongInput songInput : deleteAlbum.getSongs()) {
            Database.getInstance().getLibrary().getSongs().remove(songInput);
        }
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getType().equals("artist")) {
                Artist artist = (Artist) user;
                for (Album album : artist.getAlbums()) {
                    if (album.getName().equals(deleteAlbum.getName())) {
                        artist.getAlbums().remove(deleteAlbum);
                        break;
                    }
                }
            }
        }
        Database.getInstance().getAlbums().remove(deleteAlbum);
        return true;
    }
}
