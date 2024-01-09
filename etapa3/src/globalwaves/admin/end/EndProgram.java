package globalwaves.admin.end;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.SongInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.user.artist.merch.Merch;
import globalwaves.user.artist.merch.OwnedMerch;
import globalwaves.userstats.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class EndProgram {
    private ArrayList<EndProgramResults> endResults = new ArrayList<>();
    public EndProgram() {

    }

    /**
     * Ends the program, generates program results, and adds the results to the outputs.
     * The results include revenue details, ranking, and the most profitable song for each artist.
     */
    public void endProgram() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", "endProgram");
        ObjectNode resultNode = objectMapper.createObjectNode();
        Iterator<EndProgramResults> iterator = endResults.iterator();

        for (Listener listener : Database.getInstance().getListeners()) {
            if (!listener.getSongsloaded().isEmpty()) {
                for (SongInput song : listener.getSongsloaded()) {
                    boolean artistExists = false;

                    for (EndProgramResults endProgramResults : endResults) {
                        if (song.getArtist().equals(endProgramResults.getUsername())) {
                            artistExists = true;
                            break;
                        }
                    }

                    // If the artist does not exist, add them to endResults
                    if (!artistExists) {
                        endResults.add(new EndProgramResults(song.getArtist()));
                    }
                }
            }
        }
        for (OwnedMerch ownedMerch : Database.getInstance().getOwnedMerchArrayList()) {
            if (!ownedMerch.getOwnedmerchandise().isEmpty()) {
                for (Merch merch : ownedMerch.getOwnedmerchandise()) {
                    boolean artistExists = false;

                    for (EndProgramResults endProgramResults : endResults) {
                        if (merch.getUsername().equals(endProgramResults.getUsername())) {
                            artistExists = true;
                            break;
                        }
                    }

                    if (!artistExists) {
                        endResults.add(new EndProgramResults(merch.getUsername()));
                    }
                }
            }
        }
        // Sort endResults using the compareTo method defined in EndProgramResults
        Collections.sort(endResults);
        for (int i = 0; i < endResults.size(); i++) {
            EndProgramResults result = endResults.get(i);
            result.setRanking(i + 1);
        }
        for (EndProgramResults result : endResults) {
            ObjectNode artistNode = objectMapper.createObjectNode();
            artistNode.put("merchRevenue", result.getMerchRevenue());
            artistNode.put("songRevenue", result.getSongRevenue());
            artistNode.put("ranking", result.getRanking());
            artistNode.put("mostProfitableSong", result.getMostProfitableSong());

            resultNode.set(result.getUsername(), artistNode);
        }
        object.set("result", resultNode);
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
