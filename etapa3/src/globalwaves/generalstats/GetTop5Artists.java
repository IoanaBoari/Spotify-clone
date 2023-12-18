package globalwaves.generalstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.Artist;
import globalwaves.commands.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class GetTop5Artists implements Command {
    private final Integer five = 5;
    public GetTop5Artists() {

    }

    /**
     * Executes the provided action, generating a response message
     * containing a list of artist usernames
     * sorted by total likes and artist username. The result includes up to the first five artists.
     *
     * @param action The action input specifying the user, command details, and the timestamp.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> artistsNames = new ArrayList<>();
        ArrayList<Artist> artists = new ArrayList<>();
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getType().equals("artist")) {
                artists.add((Artist) user);
            }
        }
        int nrArtists = 0;
        if (artists.size() < five) {
            nrArtists = artists.size();
        } else {
            nrArtists = five;
        }
        Collections.sort(artists, new Comparator<Artist>() {
            @Override
            public int compare(final Artist artist1, final Artist artist2) {
                int likesComparison = Integer.compare(artist2.getAllLikes(),
                        artist1.getAllLikes());
                if (likesComparison == 0) {
                    return artist1.getUsername().compareTo(artist2.getUsername());
                } else {
                    return likesComparison;
                }
            }
        });
        for (Artist artistIterator : artists) {
            if (artistsNames.size() < nrArtists && !artistsNames.
                    contains(artistIterator.getUsername())) {
                artistsNames.add(artistIterator.getUsername());
            }
        }
        object.put("result", objectMapper.valueToTree(artistsNames));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
