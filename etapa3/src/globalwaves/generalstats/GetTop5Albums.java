package globalwaves.generalstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.user.artist.Album;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class GetTop5Albums implements Command {
    private final Integer five = 5;
    public GetTop5Albums() {

    }

    /**
     * Executes the provided action, generating a response message containing a list of album names
     * sorted by total likes and album name. The result includes up to the first five albums.
     *
     * @param action The action input specifying the user, command details, and the timestamp.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("timestamp", action.getTimestamp());
        ArrayList<String> albumsNames = new ArrayList<>();
        ArrayList<Album> albums = new ArrayList<>();
        for (Album album : Database.getInstance().getAlbums()) {
            albums.add(album);
        }
        int nrAlbums = 0;
        if (albums.size() < five) {
            nrAlbums = albums.size();
        } else {
            nrAlbums = five;
        }
        Collections.sort(albums, new Comparator<Album>() {
            @Override
            public int compare(final Album album1, final Album album2) {
                int likesComparison = Integer.compare(album2.getTotalLikes(),
                        album1.getTotalLikes());
                if (likesComparison == 0) {
                    return album1.getName().compareTo(album2.getName());
                } else {
                    return likesComparison;
                }
            }
        });
        for (Album albumIterator : albums) {
            if (albumsNames.size() < nrAlbums && !albumsNames.contains(albumIterator.getName())) {
                albumsNames.add(albumIterator.getName());
            }
        }
        object.put("result", objectMapper.valueToTree(albumsNames));
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
