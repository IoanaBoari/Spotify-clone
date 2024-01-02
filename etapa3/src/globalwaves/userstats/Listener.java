package globalwaves.userstats;

import fileio.input.ActionInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import globalwaves.Database;
import globalwaves.player.LoadResults;

import java.util.ArrayList;

public final class Listener {
    private String username;
    private ArrayList<SongInput> songsloaded;
    private ArrayList<EpisodeInput> episodesloaded;
    public static final class Builder {
        private String username;
        private ArrayList<SongInput> songsloaded;
        private ArrayList<EpisodeInput> episodesloaded;
        public Builder(final String username) {
            this.username = username;
        }

        /**
         *
         * @param songsloaded
         * @return
         */
        public Builder songsloaded(final ArrayList<SongInput> songsloaded) {
            this.songsloaded = songsloaded;
            return this;
        }

        /**
         *
         * @param episodesloaded
         * @return
         */
        public Builder episodesloaded(final ArrayList<EpisodeInput> episodesloaded) {
            this.episodesloaded = episodesloaded;
            return this;
        }

        /**
         *
         * @return
         */
        public Listener build() {
            return new Listener(this);
        }
    }
    private Listener(final Builder builder) {
        this.username = builder.username;
        this.songsloaded = builder.songsloaded;
        this.episodesloaded = builder.episodesloaded;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public ArrayList<SongInput> getSongsloaded() {
        return songsloaded;
    }

    public void setSongsloaded(final ArrayList<SongInput> songsloaded) {
        this.songsloaded = songsloaded;
    }

    public ArrayList<EpisodeInput> getEpisodesloaded() {
        return episodesloaded;
    }

    public void setEpisodesloaded(final ArrayList<EpisodeInput> episodesloaded) {
        this.episodesloaded = episodesloaded;
    }

    public Listener() {

    }

    /**
     *
     * @param action
     * @param loadResults
     */
    public static void updateListener(final ActionInput action, final LoadResults loadResults) {
        for (Listener listener : Database.getInstance().getListeners()) {
            if (listener.getUsername().equals(action.getUsername())) {
                if (loadResults.getLoadedSong() != null
                        && !listener.getSongsloaded().get(listener.getSongsloaded().size() - 1).
                        equals(loadResults.getLoadedSong())) {
                    listener.getSongsloaded().add(loadResults.getLoadedSong());
                } else if (loadResults.getLoadedPodcast() != null) {
                    int idx = loadResults.getLoadedPodcast().getCurrentEpisodeIndex();

                    if (!listener.getEpisodesloaded().
                            get(listener.getEpisodesloaded().size() - 1).equals(loadResults.
                            getLoadedPodcast().getEpisodes().get(idx))) {
                        listener.getEpisodesloaded().add(loadResults.getLoadedPodcast().
                                getEpisodes().get(idx));
                    }
                } else if (loadResults.getLoadedPlaylist() != null) {
                    int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                    if (!listener.getSongsloaded().
                            get(listener.getSongsloaded().size() - 1).equals(loadResults.
                            getLoadedPlaylist().getSongs().get(idx))) {
                        listener.getSongsloaded().add(loadResults.
                                getLoadedPlaylist().getSongs().get(idx));
                    }
                } else if (loadResults.getLoadedAlbum() != null) {
                    int idx = loadResults.getLoadedAlbum().getCurrentSongIndex();
                    if (!listener.getSongsloaded().
                            get(listener.getSongsloaded().size() - 1).equals(loadResults.
                            getLoadedAlbum().getSongs().get(idx))) {
                        listener.getSongsloaded().add(loadResults.
                                getLoadedAlbum().getSongs().get(idx));
                    }
                }
                break;
            }
        }
    }
}
