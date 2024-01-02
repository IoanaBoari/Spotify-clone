package globalwaves.admin.delete;

import fileio.input.PodcastInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.user.host.Host;
import globalwaves.player.LoadResults;

public final class DeletePodcast {
    public DeletePodcast() {

    }

    /**
     * Deletes a podcast from the database and associated user playlists.
     *
     * @param deletePodcast The PodcastInput object representing the podcast to be deleted.
     * @return True if the podcast was successfully deleted, false otherwise.
     */
    public boolean deletePodcast(final PodcastInput deletePodcast) {
        for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
            if (loadResults.getLoadedPodcast() != null
                    && loadResults.getLoadedPodcast().getName().equals(deletePodcast.getName())) {
                return false;
            }
        }
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(deletePodcast.getOwner())) {
                ((Host) user).getPodcasts().remove(deletePodcast);
                break;
            }
        }
        for (PodcastInput podcast : Database.getInstance().getLibrary().getPodcasts()) {
            if (podcast.getName().equals(deletePodcast.getName())) {
                Database.getInstance().getLibrary().getPodcasts().remove(podcast);
                break;
            }
        }
        return true;
    }
}
