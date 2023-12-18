package globalwaves.generalstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.playlist.Playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetTop5Playlists implements Command {
    private final Integer five = 5;
    public GetTop5Playlists() {

    }
    /**
     * Outputs a list of the top five public playlists with the most followers.
     * The list is sorted in descending order of followers.
     *
     * @param action          The ActionInput object representing the user's action.
     *
     *
     */

    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> playistsNames = new ArrayList<>();
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        for (Playlist playlist : Database.getInstance().getPublicPlaylists()) {
            playlists.add(playlist);
        }
        int nrPlaylists = 0;
        if (playlists.size() < five) {
            nrPlaylists = playlists.size();
        } else {
            nrPlaylists = five;
        }
        Collections.sort(playlists, new Comparator<Playlist>() {
            @Override
            public int compare(final Playlist p1, final Playlist p2) {
                return Integer.compare(p2.getFollowers(), p1.getFollowers());
            }
        });
        for (Playlist playlistIterator : playlists) {
             if (playistsNames.size() < nrPlaylists
                     && !playistsNames.contains(playlistIterator.getName())) {
                 playistsNames.add(playlistIterator.getName());
             }
        }
        object.put("result", objectMapper.valueToTree(playistsNames));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
