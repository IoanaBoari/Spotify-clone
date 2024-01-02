package globalwaves.admin.end;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.SongInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.userstats.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class EndProgram {
    private ArrayList<EndProgramResults> endResults = new ArrayList<>();
    public EndProgram() {

    }

    /**
     *
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

                    // Iterați prin endResults pentru a verifica dacă artistul există deja
                    for (EndProgramResults endProgramResults : endResults) {
                        if (song.getArtist().equals(endProgramResults.getUsername())) {
                            artistExists = true;
                            break; // Ieșiți din bucla dacă artistul există deja
                        }
                    }

                    // Dacă artistul nu există, adăugați-l în endResults
                    if (!artistExists) {
                        endResults.add(new EndProgramResults(song.getArtist()));
                    }
                }
            }
        }
        // Sortați endResults folosind metoda compareTo definită în EndProgramResults
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
