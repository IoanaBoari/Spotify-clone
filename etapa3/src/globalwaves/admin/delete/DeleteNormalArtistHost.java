package globalwaves.admin.delete;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;
import globalwaves.player.LikedSongs;
import globalwaves.player.LoadResults;
import globalwaves.playlist.FollowedPlaylists;
import globalwaves.playlist.Playlist;
import globalwaves.playlist.PlaylistsOwner;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Album;
import java.util.Iterator;

public final class DeleteNormalArtistHost {
    public DeleteNormalArtistHost() {
    }

    /**
     * Deletes a normal user from the database, including playlists, liked songs,
     * and followed playlists.
     *
     * @param deleteUser The UserInput object representing the user to be deleted.
     * @return True if the user was successfully deleted, false otherwise.
     */
    public boolean deleteNormalUser(final UserInput deleteUser) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedPlaylist() != null
                    && loadResults.getLoadedPlaylist().getOwner().
                    equals(deleteUser.getUsername())) {
                return false;
            }
        }
        Iterator<PlaylistsOwner> iterator1 = Database.getInstance().
                getPlaylistsOwners().iterator();
        while (iterator1.hasNext()) {
            PlaylistsOwner playlistsOwner = iterator1.next();
            if (playlistsOwner.getOwner().equals(deleteUser.getUsername())) {
                iterator1.remove();
                break;
            }
        }
        Iterator<Playlist> iterator2 = Database.getInstance().getPublicPlaylists().iterator();
        while (iterator2.hasNext()) {
            Playlist playlist = iterator2.next();
            if (playlist.getOwner().equals(deleteUser.getUsername())) {
                iterator2.remove();
            }
        }
        Iterator<FollowedPlaylists> iterator = Database.getInstance().
                getFollowedPlaylistsUsers().iterator();
        while (iterator.hasNext()) {
            FollowedPlaylists followedPlaylists = iterator.next();
            Iterator<Playlist> playlistIterator = followedPlaylists.getFollowedPlaylists().
                    iterator();
            while (playlistIterator.hasNext()) {
                Playlist playlist = playlistIterator.next();
                if (playlist.getOwner().equals(deleteUser.getUsername())) {
                    playlistIterator.remove();
                }
            }
            if (followedPlaylists.getFollowedPlaylists().isEmpty()) {
                iterator.remove();
            }
        }
        for (LikedSongs deleteUserLikedSongs : Database.getInstance().getLikedSongsUsers()) {
            if (deleteUserLikedSongs.getUser().equals(deleteUser.getUsername())) {
                for (SongInput song : deleteUserLikedSongs.getLikedSongs()) {
                    song.setLikes(song.getLikes() - 1);
                }
                Database.getInstance().getLikedSongsUsers().remove(deleteUserLikedSongs);
                break;
            }
        }

        for (FollowedPlaylists deleteUserFollowedPlaylists : Database.getInstance().
                getFollowedPlaylistsUsers()) {
            if (deleteUserFollowedPlaylists.getUser().equals(deleteUser.getUsername())) {
                for (Playlist playlist : deleteUserFollowedPlaylists.getFollowedPlaylists()) {
                    playlist.setFollowers(playlist.getFollowers() - 1);
                }
                Database.getInstance().getFollowedPlaylistsUsers().
                        remove(deleteUserFollowedPlaylists);
                break;
            }
        }

        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(deleteUser.getUsername())) {
                Database.getInstance().getLibrary().getUsers().remove(userInput);
                break;
            }
        }
        return true;
    }

    /**
     * Deletes an artist user from the database, including albums, songs, playlists, liked songs,
     * and followed playlists.
     *
     * @param deleteUser The UserInput object representing the artist user to be deleted.
     * @return True if the artist user was successfully deleted, false otherwise.
     */
    public boolean deleteArtist(final UserInput deleteUser) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedAlbum() != null
                    && loadResults.getLoadedAlbum().getUsername().
                    equals(deleteUser.getUsername())) {
                return false;
            }
        }
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedSong() != null
                    && loadResults.getLoadedSong().getArtist().
                    equals(deleteUser.getUsername())) {
                return false;
            }
        }
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedPlaylist() != null
                    && !loadResults.getLoadedPlaylist().getSongs().isEmpty()) {
                if (loadResults.getLoadedPlaylist().getOwner().
                        equals(deleteUser.getUsername())) {
                    return false;
                }
                for (SongInput song : loadResults.getLoadedPlaylist().getSongs()) {
                    if (song.getArtist().equals(deleteUser.getUsername())) {
                        return false;
                    }
                }
            }
        }
        for (SelectResults currentPage : Database.getInstance().getCurrentPages()) {
            if (currentPage.getSelectArtist() != null
                    && currentPage.getSelectArtist().getUsername() != null
                    && currentPage.getSelectArtist().getUsername().
                    equals(deleteUser.getUsername())) {
                return false;
            }
        }
        Artist deleteArtist = (Artist) deleteUser;

        Iterator<LikedSongs> iterator = Database.getInstance().getLikedSongsUsers().iterator();
        while (iterator.hasNext()) {
            LikedSongs likedSongs = iterator.next();
            Iterator<SongInput> songIterator = likedSongs.getLikedSongs().iterator();
            while (songIterator.hasNext()) {
                SongInput song = songIterator.next();
                if (song.getArtist().equals(deleteArtist.getUsername())) {
                    songIterator.remove();
                }
            }
            if (likedSongs.getLikedSongs().isEmpty()) {
                iterator.remove();
            }
        }
        for (LikedSongs deleteArtistLikedSongs : Database.getInstance().getLikedSongsUsers()) {
            if (deleteArtistLikedSongs.getUser().equals(deleteArtist.getUsername())) {
                for (SongInput song : deleteArtistLikedSongs.getLikedSongs()) {
                    song.setLikes(song.getLikes() - 1);
                }
                Database.getInstance().getLikedSongsUsers().remove(deleteArtistLikedSongs);
                break;
            }
        }

        for (FollowedPlaylists deleteArtistFollowedPlaylists : Database.getInstance().
                getFollowedPlaylistsUsers()) {
            if (deleteArtistFollowedPlaylists.getUser().equals(deleteArtist.getUsername())) {
                for (Playlist playlist : deleteArtistFollowedPlaylists.getFollowedPlaylists()) {
                    playlist.setFollowers(playlist.getFollowers() - 1);
                }
                Database.getInstance().getFollowedPlaylistsUsers().
                        remove(deleteArtistFollowedPlaylists);
                break;
            }
        }
        Iterator<PlaylistsOwner> iterator1 = Database.getInstance().
                getPlaylistsOwners().iterator();
        while (iterator1.hasNext()) {
            PlaylistsOwner playlistsOwner = iterator1.next();
            Iterator<Playlist> playlistIterator = playlistsOwner.getPlaylists().iterator();
            while (playlistIterator.hasNext()) {
                Playlist playlist = playlistIterator.next();
                Iterator<SongInput> songIterator = playlist.getSongs().iterator();
                Iterator<SongInput> songInitialIterator = playlist.getInitialOrder().iterator();

                while (songIterator.hasNext()) {
                    SongInput songInput = songIterator.next();
                    if (songInput.getArtist().equals(deleteArtist.getUsername())) {
                        songIterator.remove();
                    }
                }
                while (songInitialIterator.hasNext()) {
                    SongInput songInput = songInitialIterator.next();
                    if (songInput.getArtist().equals(deleteArtist.getUsername())) {
                        songInitialIterator.remove();
                    }
                }
            }
            if (playlistsOwner.getOwner().equals(deleteArtist.getUsername())) {
                iterator1.remove();
            }
        }
        Iterator<Playlist> iterator2 = Database.getInstance().getPublicPlaylists().iterator();
        while (iterator2.hasNext()) {
            Playlist playlist = iterator2.next();
            if (playlist.getOwner().equals(deleteArtist.getUsername())) {
                iterator2.remove();
            }
        }
        Iterator<FollowedPlaylists> iterator3 = Database.getInstance().
                getFollowedPlaylistsUsers().iterator();
        while (iterator3.hasNext()) {
            FollowedPlaylists followedPlaylists = iterator3.next();
            Iterator<Playlist> playlistIterator = followedPlaylists.getFollowedPlaylists().
                    iterator();
            while (playlistIterator.hasNext()) {
                Playlist playlist = playlistIterator.next();
                if (playlist.getOwner().equals(deleteArtist.getUsername())) {
                    playlistIterator.remove();
                }
            }
            if (followedPlaylists.getFollowedPlaylists().isEmpty()) {
                iterator.remove();
            }
        }
        for (Album album : deleteArtist.getAlbums()) {
            for (SongInput songInput : album.getSongs()) {
                Database.getInstance().getLibrary().getSongs().remove(songInput);
            }
            Database.getInstance().getAlbums().remove(album);
        }
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(deleteUser.getUsername())) {
                Database.getInstance().getLibrary().getUsers().remove(userInput);
                break;
            }
        }
        return true;
    }

    /**
     * Deletes a host user from the database, including podcasts and related results.
     *
     * @param deleteUser The UserInput object representing the host user to be deleted.
     * @return True if the host user was successfully deleted, false otherwise.
     */
    public boolean deleteHost(final UserInput deleteUser) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedPodcast() != null
                    && loadResults.getLoadedPodcast().getOwner() != null
                    && loadResults.getLoadedPodcast().getOwner().
                    equals(deleteUser.getUsername())) {
                return false;
            }
        }
        for (SelectResults currentPage : Database.getInstance().getCurrentPages()) {
            if (currentPage.getSelectHost() != null
                    && currentPage.getSelectHost().getUsername() != null
                    && currentPage.getSelectHost().getUsername().
                    equals(deleteUser.getUsername())) {
                return false;
            }
        }
        Host deleteHost = (Host) deleteUser;
        for (PodcastInput podcast : deleteHost.getPodcasts()) {
            Database.getInstance().getLibrary().getPodcasts().remove(podcast);
        }
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(deleteUser.getUsername())) {
                Database.getInstance().getLibrary().getUsers().remove(userInput);
                break;
            }
        }
        return true;
    }

}
