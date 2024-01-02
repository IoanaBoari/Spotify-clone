package globalwaves.userstats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.UpdateStats;
import globalwaves.commands.Command;
import globalwaves.user.artist.ArtistWrapped;
import globalwaves.user.host.HostWrapped;
import globalwaves.user.normalUser.NormalWrapped;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Wrapped implements Command {
    private final Integer five = 5;
    /**
     *
     * @param currentUser
     */
    public void doWrapped(final UserInput currentUser) {

    }

    /**
     *
     * @param map
     * @return
     * @param <K>
     * @param <V>
     */
    protected static <K extends Comparable<? super K>, V extends Comparable<? super V>>
    Map<K, V> sortMapByValueThenKey(final Map<K, V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByValue().reversed()
                        .thenComparing(Map.Entry.<K, V>comparingByKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
    /**
     *
     * @param action The action input containing information necessary for executing the command.
     */
    public void execute(final ActionInput action) {
        new UpdateStats().doUpdateCurrent(action);
        String name  = action.getUsername();
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            action.setUsername(user.getUsername());
            new UpdateStats().doUpdateCurrent(action);
        }
        action.setUsername(name);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        ObjectNode resultNode = objectMapper.createObjectNode();
        UserInput currentUser = null;
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(action.getUsername())) {
                currentUser = user;
                break;
            }
        }
        switch (currentUser.getType()) {
            case "user" -> {
                NormalWrapped wrapped = new NormalWrapped();
                wrapped.doWrapped(currentUser);
                if (wrapped.getTopSongs().isEmpty() && wrapped.getTopEpisodes().isEmpty()) {
                    object.put("message", "No data to show for user "
                            + currentUser.getUsername() + ".");
                    break;
                }
                ObjectNode artistsNode = objectMapper.createObjectNode();
                int count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopArtists().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    artistsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topArtists", artistsNode);
                ObjectNode genresNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopGenres().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    genresNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topGenres", genresNode);
                ObjectNode songsNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopSongs().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    songsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topSongs", songsNode);
                ObjectNode albumsNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopAlbums().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    albumsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topAlbums", albumsNode);
                ObjectNode episodesNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopEpisodes().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    episodesNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topEpisodes", episodesNode);
                object.set("result", resultNode);
            }
            case "artist" -> {
                ArtistWrapped wrapped = new ArtistWrapped();
                wrapped.doWrapped(currentUser);
                if (wrapped.getTopSongs().isEmpty()) {
                    object.put("message", "No data to show for artist "
                            + currentUser.getUsername() + ".");
                    break;
                }
                ObjectNode albumsNode = objectMapper.createObjectNode();
                int count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopAlbums().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    albumsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topAlbums", albumsNode);
                ObjectNode songsNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopSongs().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    songsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topSongs", songsNode);
                ArrayList<String> fansNames = new ArrayList<>();
                count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopFans().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    fansNames.add(entry.getKey());
                    count++;
                }
                resultNode.put("topFans",  objectMapper.valueToTree(fansNames));
                resultNode.put("listeners", wrapped.getListeners());
                object.set("result", resultNode);
            }
            case "host" -> {
                HostWrapped wrapped = new HostWrapped();
                wrapped.doWrapped(currentUser);
                if (wrapped.getTopEpisodes().isEmpty()) {
                    object.put("message", "No data to show for host "
                            + currentUser.getUsername() + ".");
                    break;
                }
                ObjectNode episodesNode = objectMapper.createObjectNode();
                int count = 0;
                for (Map.Entry<String, Integer> entry : wrapped.getTopEpisodes().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    episodesNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topEpisodes", episodesNode);
                resultNode.put("listeners", wrapped.getListeners());
                object.set("result", resultNode);
            }
            default -> {
            }
        }

        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
