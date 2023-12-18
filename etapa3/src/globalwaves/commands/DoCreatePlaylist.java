package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.playlist.CreatePlaylist;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;


public final class DoCreatePlaylist implements Command {
    public DoCreatePlaylist() {

    }

    /**
     * Creates a new playlist based on the provided ActionInput.
     * Utilizes the CreatePlaylist class and interacts with a list of PlaylistsOwner
     * and public playlists.
     * Updates the playlistsOwners and publicPlaylists accordingly.
     *
     * @param action           The ActionInput containing information about the user's request.
     *
     */
    public void execute(final ActionInput action) {
        PlaylistsOwner playlistsOwner = null;
        if (Database.getInstance().getPlaylistsOwners() != null) {
            for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
                if (owner.getOwner().equals(action.getUsername())) {
                    playlistsOwner = owner;
                    Database.getInstance().getPlaylistsOwners().remove(owner);
                    break;
                }
            }
        }
        if (playlistsOwner == null) {
            playlistsOwner = new PlaylistsOwner(action.getUsername());
        }
        CreatePlaylist create = new CreatePlaylist(playlistsOwner);
        PlaylistsOwner newPlaylistOwner = create.doOutput(action, Menu.getOutputs());
        Database.getInstance().getPlaylistsOwners().add(newPlaylistOwner);
        for (PlaylistsOwner owner : Database.getInstance().getPlaylistsOwners()) {
            if (owner.getOwner().equals(action.getUsername())) {
                for (Playlist playlist : owner.getPlaylists()) {
                    if (!Database.getInstance().getPublicPlaylists().contains(playlist)
                            && playlist.getVisibility().equals("public")) {
                        Database.getInstance().getPublicPlaylists().add(playlist);
                    }
                }
            }
        }
    }
}
