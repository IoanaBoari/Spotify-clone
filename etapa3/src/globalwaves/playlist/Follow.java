package globalwaves.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.searchbar.SelectResults;

public final class Follow {
    private FollowedPlaylists followedPlaylists = new FollowedPlaylists();

    public FollowedPlaylists getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(final FollowedPlaylists followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }
    public Follow(final FollowedPlaylists playlists) {
        this.followedPlaylists = playlists;
    }
    public Follow() {

    }

    /**
     * Outputs a message and follows or unfollows a public playlist based on user action.
     *
     * @param action The ActionInput representing the user's action.
     * @param outputs The ArrayNode containing the output messages.
     * @param lastAction The last performed action.
     * @param selectResults The SelectResults containing the selected result.
     */
    public void doOutput(final ActionInput action, final ArrayNode outputs,
                         final String lastAction, final SelectResults selectResults) {
        this.followedPlaylists.setUser(action.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            if (!lastAction.equals("select")) {
                object.put("message", "Please select a source before following or unfollowing.");
            } else {
                if (selectResults.getSelectPlaylist() == null
                        || selectResults.getSelectPlaylist().getName() == null) {
                    object.put("message", "The selected source is not a playlist.");
                } else {
                    if (selectResults.getSelectPlaylist() != null) {
                        if (selectResults.getSelectPlaylist().getOwner().
                                equals(action.getUsername())) {
                            object.put("message",
                                    "You cannot follow or unfollow your own playlist.");
                        } else {
                            int aux = 0;
                            if (this.followedPlaylists.getFollowedPlaylists() != null) {
                                for (Playlist playlist : this.followedPlaylists.
                                        getFollowedPlaylists()) {
                                    if (playlist.getName().equals(selectResults.getSelectPlaylist().
                                            getName())) {
                                        aux = 1;
                                        break;
                                    }
                                }
                                if (aux == 0) {
                                    selectResults.getSelectPlaylist().setFollowers(selectResults.
                                            getSelectPlaylist().getFollowers() + 1);
                                    this.followedPlaylists.getFollowedPlaylists().
                                            add(selectResults.getSelectPlaylist());
                                    object.put("message", "Playlist followed successfully.");
                                } else {
                                    selectResults.getSelectPlaylist().setFollowers(selectResults.
                                            getSelectPlaylist().getFollowers() - 1);
                                    this.followedPlaylists.getFollowedPlaylists().
                                            remove(selectResults.getSelectPlaylist());
                                    object.put("message", "Playlist unfollowed successfully.");
                                }
                            } else {
                                selectResults.getSelectPlaylist().setFollowers(selectResults.
                                        getSelectPlaylist().getFollowers() + 1);
                                this.followedPlaylists.getFollowedPlaylists().
                                        add(selectResults.getSelectPlaylist());
                                object.put("message", "Playlist followed successfully.");
                            }
                        }
                    }
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
