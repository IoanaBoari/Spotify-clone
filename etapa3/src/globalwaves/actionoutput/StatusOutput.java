package globalwaves.actionoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.player.LoadResults;
import globalwaves.player.Stats;

import static globalwaves.userstats.Listener.updateListener;

public final class StatusOutput {
    private LoadResults loadResults = new LoadResults();

    public LoadResults getLoadResults() {
        return loadResults;
    }

    public void setLoadResults(final LoadResults loadResults) {
        this.loadResults = loadResults;
    }

    public StatusOutput() {
        this.loadResults.setStats(new Stats());
    }

    /**
     * Constructs a StatusOutput object based on the provided LoadResults,
     * updating the internal state.
     *
     * @param results The LoadResults object representing the current state of the player.
     */

    public StatusOutput(final LoadResults results) {
        if (results.getLoadedSong() != null) {
            this.loadResults.setLoadedSong(results.getLoadedSong());
        } else if (results.getLoadedPlaylist() != null) {
            this.loadResults.setLoadedPlaylist(results.getLoadedPlaylist());
        } else if (results.getLoadedPodcast() != null) {
            this.loadResults.setLoadedPodcast(results.getLoadedPodcast());
        } else if (results.getLoadedAlbum() != null) {
            this.loadResults.setLoadedAlbum(results.getLoadedAlbum());
        }
        this.loadResults.setTimestamp(results.getTimestamp());
        this.loadResults.setUsername(results.getUsername());
        this.loadResults.setLastCommand(results.getLastCommand());
        this.loadResults.setStats(new Stats(results));
        if (results.getStats().getCurrentTimestamp() != null) {
            this.loadResults.getStats().setCurrentTimestamp(results.
                    getStats().getCurrentTimestamp());
        }
        this.loadResults.getStats().setPaused(results.getStats().isPaused());
        this.loadResults.getStats().setShuffle(results.getStats().isShuffle());
        this.loadResults.getStats().setRepeat(results.getStats().getRepeat());
    }
    /**
     * Updates the player statistics based on the provided action and current state
     * and outputs the current statistics if the command is "status".
     *
     * @param action   The ActionInput object representing the user's command.
     *
     */
    public void doOutput(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        int currentRemainedTime = 0;
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
            }
        }
        if (!this.loadResults.getStats().isPaused()) {
            if (this.loadResults.getStats().getRemainedTime() != null) {
                currentRemainedTime = this.loadResults.getStats().getRemainedTime()
                        - (action.getTimestamp() - this.loadResults.
                        getStats().getCurrentTimestamp());
                this.loadResults.getStats().setCurrentTimestamp(action.getTimestamp());
                if (currentRemainedTime <= 0 && this.loadResults.getStats().getRepeat().
                        equals("No Repeat")) {
                    this.loadResults.getStats().setRemainedTime(0);
                    this.loadResults.getStats().setName("");
                    this.loadResults.getStats().setPaused(true);
                    this.loadResults.getStats().setRepeat("No Repeat");
                } else {
                    this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                    this.loadResults.setStats(new Stats(this.loadResults));
                }
            }
            if (this.loadResults.getLoadedSong() != null) {
                if (currentRemainedTime <= 0) {
                    if (this.loadResults.getStats().getRepeat().equals("Repeat Once")) {
                        this.loadResults.getStats().setRemainedTime(this.loadResults.
                                getLoadedSong().getDuration() + currentRemainedTime);
                        this.loadResults.getStats().setRepeat("No Repeat");
                    } else if (this.loadResults.getStats().getRepeat().equals("Repeat Infinite")) {
                        currentRemainedTime = currentRemainedTime % this.loadResults.
                                getLoadedSong().getDuration();
                        this.loadResults.getStats().setRemainedTime(currentRemainedTime
                                + this.loadResults.getLoadedSong().getDuration());
                    }
                    updateListener(action, this.loadResults);
                }
            } else if (this.loadResults.getLoadedPlaylist() != null) {
                if (this.loadResults.getStats().getRepeat().equals("Repeat All")) {
                        while (currentRemainedTime <= 0) {
                            if (loadResults.getLoadedPlaylist().getCurrentSongIndex()
                                    < loadResults.getLoadedPlaylist().getSongs().size() - 1) {
                                this.loadResults.getLoadedPlaylist().playNextSong();
                            } else {
                                this.loadResults.getLoadedPlaylist().setCurrentSongIndex(0);
                            }
                            int idx = this.loadResults.getLoadedPlaylist().getCurrentSongIndex();
                            currentRemainedTime += this.loadResults.getLoadedPlaylist().getSongs().
                                    get(idx).getDuration();
                        }
                        this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                        this.loadResults.setStats(new Stats(this.loadResults));
                    } else if (this.loadResults.getStats().getRepeat().
                            equals("Repeat Current Song")) {
                        int idx = this.loadResults.getLoadedPlaylist().getCurrentSongIndex();
                        while (currentRemainedTime <= 0) {
                            currentRemainedTime += this.loadResults.
                                    getLoadedPlaylist().getSongs().get(idx).getDuration();
                        }
                        this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                    } else if (this.loadResults.getStats().getRepeat().equals("No Repeat")
                        && currentRemainedTime <= 0) {
                        int idx = this.loadResults.getLoadedPlaylist().getCurrentSongIndex();
                        updateListener(action, this.loadResults);
                        this.loadResults.getStats().setPaused(false);
                        while (currentRemainedTime <= 0) {
                            if (idx < this.loadResults.getLoadedPlaylist().getSongs().size() - 1) {
                                this.loadResults.getLoadedPlaylist().playNextSong();
                                updateListener(action, this.loadResults);
                                idx = this.loadResults.getLoadedPlaylist().getCurrentSongIndex();
                                currentRemainedTime += this.loadResults.getLoadedPlaylist().
                                        getSongs().get(idx).getDuration();
                            } else {
                                break;
                            }
                        }
                    if (currentRemainedTime <= 0) {
                        this.loadResults.getStats().setRemainedTime(0);
                        this.loadResults.getStats().setName("");
                        this.loadResults.getStats().setPaused(true);
                    } else {
                        this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                        this.loadResults.setStats(new Stats(this.loadResults));
                    }
                }
            } else if (this.loadResults.getLoadedAlbum() != null) {
                if (this.loadResults.getStats().getRepeat().equals("Repeat All")) {
                    while (currentRemainedTime <= 0) {
                        if (loadResults.getLoadedAlbum().getCurrentSongIndex()
                                < loadResults.getLoadedAlbum().getSongs().size() - 1) {
                            this.loadResults.getLoadedAlbum().playNextSong();
                        } else {
                            this.loadResults.getLoadedAlbum().setCurrentSongIndex(0);
                        }
                        int idx = this.loadResults.getLoadedAlbum().getCurrentSongIndex();
                        currentRemainedTime += this.loadResults.getLoadedAlbum().getSongs().
                                get(idx).getDuration();
                    }
                    this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                    this.loadResults.setStats(new Stats(this.loadResults));
                } else if (this.loadResults.getStats().getRepeat().
                        equals("Repeat Current Song")) {
                    int idx = this.loadResults.getLoadedAlbum().getCurrentSongIndex();
                    while (currentRemainedTime <= 0) {
                        currentRemainedTime += this.loadResults.
                                getLoadedAlbum().getSongs().get(idx).getDuration();
                    }
                    this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                } else if (this.loadResults.getStats().getRepeat().equals("No Repeat")) {
                    int idx = this.loadResults.getLoadedAlbum().getCurrentSongIndex();
                    updateListener(action, this.loadResults);
                    this.loadResults.getStats().setPaused(false);
                    while (currentRemainedTime <= 0) {
                        if (idx < this.loadResults.getLoadedAlbum().getSongs().size() - 1) {
                            this.loadResults.getLoadedAlbum().playNextSong();
                            updateListener(action, this.loadResults);
                            idx = this.loadResults.getLoadedAlbum().getCurrentSongIndex();
                            currentRemainedTime += this.loadResults.getLoadedAlbum().
                                    getSongs().get(idx).getDuration();
                        } else {
                            break;
                        }
                    }
                    if (currentRemainedTime <= 0) {
                        this.loadResults.getStats().setRemainedTime(0);
                        this.loadResults.getStats().setName("");
                        this.loadResults.getStats().setPaused(true);
                    } else {
                        this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                        this.loadResults.setStats(new Stats(this.loadResults));
                    }
                }
            } else if (this.loadResults.getLoadedPodcast() != null) {
                if (currentRemainedTime <= 0) {
                    int idx = this.loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
                    updateListener(action, this.loadResults);
                    this.loadResults.getStats().setPaused(false);
                    while (currentRemainedTime <= 0 && idx < this.loadResults.
                            getLoadedPodcast().getEpisodes().size() - 1) {
                        this.loadResults.getLoadedPodcast().playNextEpisode();
                        updateListener(action, this.loadResults);
                        idx = this.loadResults.getLoadedPodcast().getCurrentEpisodeIndex();
                        currentRemainedTime = this.loadResults.getLoadedPodcast().getEpisodes().
                                get(idx).getDuration() + currentRemainedTime;
                    }
                    if (currentRemainedTime <= 0) {
                        this.loadResults.getStats().setRemainedTime(0);
                        this.loadResults.getStats().setName("");
                        this.loadResults.getStats().setPaused(true);
                    } else {
                        this.loadResults.getStats().setRemainedTime(currentRemainedTime);
                    }
                    this.loadResults.setStats(new Stats(this.loadResults));
                }
            }
        }
        if (this.loadResults == null || this.loadResults.getStats().getName() == null) {
            this.loadResults.getStats().setRemainedTime(0);
            this.loadResults.getStats().setName("");
            this.loadResults.getStats().setPaused(true);
        }
        if (this.loadResults.getStats().getRemainedTime() == 0) {
            if (this.loadResults.getLoadedPlaylist() != null && !this.loadResults.
                    getLoadedPlaylist().getInitialOrder().isEmpty()) {
                this.loadResults.getLoadedPlaylist().setSongs(this.loadResults.
                        getLoadedPlaylist().getInitialOrder());
            } else if (this.loadResults.getLoadedAlbum() != null && !this.loadResults.
                    getLoadedAlbum().getInitialOrder().isEmpty()) {
                this.loadResults.getLoadedAlbum().setSongs(this.loadResults.
                        getLoadedAlbum().getInitialOrder());
            }
        }
        if (action.getCommand().equals("status")) {
            object.put("command", action.getCommand());
            object.put("user", action.getUsername());
            object.put("timestamp", action.getTimestamp());
            ObjectNode node = objectMapper.createObjectNode();
            node.put("name", this.loadResults.getStats().getName());
            node.put("remainedTime", this.loadResults.getStats().getRemainedTime());
            node.put("repeat", this.loadResults.getStats().getRepeat());
            if (this.loadResults.getStats().getRemainedTime() == 0
                    && this.loadResults.getStats().isShuffle()) {
                this.loadResults.getStats().setShuffle(false);
            }
            node.put("shuffle", this.loadResults.getStats().isShuffle());
            if (currentUser != null && currentUser.getType().equals("user")
                    && currentUser.getMode().equals("offline")) {
                if (this.loadResults.getStats().getName().equals("")) {
                    node.put("paused", this.loadResults.getStats().isPaused());
                } else {
                    node.put("paused", false);
                }
            } else {
                node.put("paused", this.loadResults.getStats().isPaused());
            }
            object.set("stats", node);
            Menu.setOutputs(Menu.getOutputs().add(object));
        }
    }
}
