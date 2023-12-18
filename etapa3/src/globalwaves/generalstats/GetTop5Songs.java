package globalwaves.generalstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.SongInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetTop5Songs implements Command {
    private final Integer five = 5;
    public GetTop5Songs() {

    }
    /**
     * Outputs a list of the top five songs in the library based on the number of likes.
     * The list is sorted in descending order of likes.
     *
     * @param action   The ActionInput object representing the user's action.
     *
     */

    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> songsNames = new ArrayList<>();
        ArrayList<SongInput> songs = new ArrayList<>();
        for (SongInput song : Database.getInstance().getLibrary().getSongs()) {
            songs.add(song);
        }
        Collections.sort(songs, new Comparator<SongInput>() {
            @Override
            public int compare(final SongInput s1, final SongInput s2) {
                return Integer.compare(s2.getLikes(), s1.getLikes());
            }
        });
        for (SongInput songIterator : songs) {
            if (songsNames.size() < five && !songsNames.contains(songIterator.getName())) {
                songsNames.add(songIterator.getName());
            }
        }
        object.put("result", objectMapper.valueToTree(songsNames));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
