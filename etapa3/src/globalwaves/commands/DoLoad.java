package globalwaves.commands;

import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.actionoutput.LoadOutput;
import globalwaves.actionoutput.StatusOutput;
import globalwaves.admin.UpdateStats;
import globalwaves.player.LoadResults;
import globalwaves.player.PodcastLoaded;
import globalwaves.searchbar.SelectResults;
import globalwaves.userstats.Listener;

import java.util.ArrayList;

public final class DoLoad implements Command {
    public DoLoad() {

    }

    /**
     * Processes the "load" action based on the provided ActionInput.
     * Manages the loading operation using the LoadOutput class and interacts with lists
     * of SelectResults, and LoadResults for state tracking.
     * Updates the loadResultsArrayList and podcastsLoaded accordingly.
     *
     * @param action                  The ActionInput containing information
     *                                about the user's request.
     */
    public void execute(final ActionInput action) {
        new UpdateStats().doUpdateCurrent(action);
        LoadOutput loadOutput = null;
        for (SelectResults results : Database.getInstance().getSelectResultsArrayList()) {
            if (results.getUsername().equals(action.getUsername())) {
                loadOutput = new LoadOutput(results);
                Menu.setLastAction("select");
                Database.getInstance().getSelectResultsArrayList().remove(results);
                break;
            }
        }
        if (loadOutput == null) {
            loadOutput = new LoadOutput();
        }
        loadOutput.doOutput(action, Menu.getLastAction());
        LoadResults loadResults = new LoadResults(action, loadOutput.getSongLoaded(),
                loadOutput.getPodcastLoaded(), loadOutput.getPlaylistLoaded(),
                loadOutput.getAlbumLoaded());
        if (loadResults.getLoadedPodcast() != null) {
            if (Database.getInstance().getPodcastsLoaded().size() != 0) {
                for (PodcastLoaded podcast : Database.getInstance().getPodcastsLoaded()) {
                    StatusOutput statusOutput = new StatusOutput(loadResults);
                    statusOutput.doOutput(action);
                    if (podcast.getUsername().equals(loadResults.getUsername())
                            && podcast.getPodcast().getName().
                            equals(loadResults.getLoadedPodcast().getName())) {
                        loadResults.getStats().setRemainedTime(podcast.getTime());
                    }
                }
            }
        }
        loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
        if (Menu.getLastAction().equals("select")) {
            Database.getInstance().getLoadResultsArrayList().add(loadResults);
            int aux = 0;
            for (Listener listener : Database.getInstance().getListeners()) {
                if (listener.getUsername().equals(action.getUsername())) {
                    aux = 1;
                    break;
                }
            }
            if (aux == 0) {
                Listener newListener = new Listener.Builder(action.getUsername())
                        .songsloaded(new ArrayList<>())
                        .episodesloaded(new ArrayList<>())
                        .build();
                Database.getInstance().getListeners().add(newListener);
            }
            for (Listener listener : Database.getInstance().getListeners()) {
                if (listener.getUsername().equals(action.getUsername())) {
                    if (loadResults.getLoadedSong() != null) {
                        listener.getSongsloaded().add(loadResults.getLoadedSong());
                    } else if (loadResults.getLoadedPodcast() != null) {
                        int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
                        listener.getEpisodesloaded().add(loadResults.getLoadedPodcast().
                                getEpisodes().get(idx));
                    } else if (loadResults.getLoadedPlaylist() != null) {
                        int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                        listener.getSongsloaded().add(loadResults.
                                getLoadedPlaylist().getSongs().get(idx));
                    } else if (loadResults.getLoadedAlbum() != null) {
                        int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
                        listener.getSongsloaded().add(loadResults.
                                getLoadedAlbum().getSongs().get(idx));

                    }
                    break;
                }
            }
            int premium = 0;
            for (UserInput user : Database.getInstance().getPremiumUsers()) {
                if (user.getUsername().equals(action.getUsername())) {
                    premium = 1;
                }
            }
            if (premium == 0) {
                int aux1 = 0;
                for (Listener listener : Database.getInstance().getFreeListeners()) {
                    if (listener.getUsername().equals(action.getUsername())) {
                        aux1 = 1;
                        break;
                    }
                }
                if (aux1 == 0) {
                    Listener newListener = new Listener.Builder(action.getUsername())
                            .songsloaded(new ArrayList<>())
                            .episodesloaded(new ArrayList<>())
                            .build();
                    Database.getInstance().getFreeListeners().add(newListener);
                }
                for (Listener freeListener : Database.getInstance().getFreeListeners()) {
                    if (freeListener.getUsername().equals(action.getUsername())) {
                        if (loadResults.getLoadedSong() != null) {
                            freeListener.getSongsloaded().add(loadResults.getLoadedSong());
                        } else if (loadResults.getLoadedPodcast() != null) {
                            int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
                            freeListener.getEpisodesloaded().add(loadResults.getLoadedPodcast().
                                    getEpisodes().get(idx));
                        } else if (loadResults.getLoadedPlaylist() != null) {
                            int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                            freeListener.getSongsloaded().add(loadResults.
                                    getLoadedPlaylist().getSongs().get(idx));
                        } else if (loadResults.getLoadedAlbum() != null) {
                            int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
                            freeListener.getSongsloaded().add(loadResults.
                                    getLoadedAlbum().getSongs().get(idx));

                        }
                        break;
                    }
                }
                Menu.setLastAction(loadResults.getLastCommand());
                return;
            }
            int aux2 = 0;
            for (Listener listener : Database.getInstance().getPremiumListeners()) {
                if (listener.getUsername().equals(action.getUsername())) {
                    aux2 = 1;
                    break;
                }
            }
            if (aux2 == 0) {
                Listener newListener = new Listener.Builder(action.getUsername())
                        .songsloaded(new ArrayList<>())
                        .episodesloaded(new ArrayList<>())
                        .build();
                Database.getInstance().getPremiumListeners().add(newListener);
            }
            for (Listener premiumListener : Database.getInstance().getPremiumListeners()) {
                if (premiumListener.getUsername().equals(action.getUsername())) {
                    if (loadResults.getLoadedSong() != null) {
                        premiumListener.getSongsloaded().add(loadResults.getLoadedSong());
                    } else if (loadResults.getLoadedPodcast() != null) {
                        int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
                        premiumListener.getEpisodesloaded().add(loadResults.getLoadedPodcast().
                                getEpisodes().get(idx));
                    } else if (loadResults.getLoadedPlaylist() != null) {
                        int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                        premiumListener.getSongsloaded().add(loadResults.
                                getLoadedPlaylist().getSongs().get(idx));
                    } else if (loadResults.getLoadedAlbum() != null) {
                        int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
                        premiumListener.getSongsloaded().add(loadResults.
                                getLoadedAlbum().getSongs().get(idx));

                    }
                    break;
                }
            }
            Menu.setLastAction(loadResults.getLastCommand());
        }
    }
}
