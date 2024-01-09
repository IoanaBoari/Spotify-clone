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
     * Performs a wrapped operation specific to the class that extends this one,
     * using the provided UserInput.
     * This method is meant to be implemented by subclasses to define custom behavior.
     *
     * @param currentUser The UserInput object representing the current user.
     */
    public void doWrapped(final UserInput currentUser) {

    }

    /**
     * Sorts a Map by values in descending order, and then by keys in natural order.
     *
     * @param map The Map to be sorted.
     * @param <K> The type of keys in the Map, extending Comparable.
     * @param <V> The type of values in the Map, extending Comparable.
     * @return A new Map sorted by values in descending order, and then by keys in natural order.
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
     * Executes the command specified in the given action input and provides a response.
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
        WrappedFactory.WrappedType wrappedType = WrappedFactory.WrappedType.
                valueOf(currentUser.getType());
        Wrapped wrapped = WrappedFactory.createWrapped(wrappedType);
        wrapped.doWrapped(currentUser);
        switch (currentUser.getType()) {
            case "user" -> {
                if (((NormalWrapped) wrapped).getTopSongs().isEmpty()
                        && ((NormalWrapped) wrapped).getTopEpisodes().isEmpty()) {
                    object.put("message", "No data to show for user "
                            + currentUser.getUsername() + ".");
                    break;
                }
                ObjectNode artistsNode = objectMapper.createObjectNode();
                int count = 0;
                for (Map.Entry<String, Integer> entry : ((NormalWrapped) wrapped).
                        getTopArtists().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    artistsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topArtists", artistsNode);
                ObjectNode genresNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : ((NormalWrapped) wrapped).
                        getTopGenres().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    genresNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topGenres", genresNode);
                ObjectNode songsNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : ((NormalWrapped) wrapped).
                        getTopSongs().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    songsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topSongs", songsNode);
                ObjectNode albumsNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : ((NormalWrapped) wrapped).
                        getTopAlbums().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    albumsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topAlbums", albumsNode);
                ObjectNode episodesNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry :((NormalWrapped) wrapped).
                        getTopEpisodes().entrySet()) {
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
                if (((ArtistWrapped) wrapped).getTopSongs().isEmpty()) {
                    object.put("message", "No data to show for artist "
                            + currentUser.getUsername() + ".");
                    break;
                }
                ObjectNode albumsNode = objectMapper.createObjectNode();
                int count = 0;
                for (Map.Entry<String, Integer> entry : ((ArtistWrapped) wrapped).
                        getTopAlbums().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    albumsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topAlbums", albumsNode);
                ObjectNode songsNode = objectMapper.createObjectNode();
                count = 0;
                for (Map.Entry<String, Integer> entry : ((ArtistWrapped) wrapped).
                        getTopSongs().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    songsNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topSongs", songsNode);
                ArrayList<String> fansNames = new ArrayList<>();
                count = 0;
                for (Map.Entry<String, Integer> entry : ((ArtistWrapped) wrapped).
                        getTopFans().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    fansNames.add(entry.getKey());
                    count++;
                }
                resultNode.put("topFans",  objectMapper.valueToTree(fansNames));
                resultNode.put("listeners", ((ArtistWrapped) wrapped).getListeners());
                object.set("result", resultNode);
            }
            case "host" -> {
                if (((HostWrapped) wrapped).getTopEpisodes().isEmpty()) {
                    object.put("message", "No data to show for host "
                            + currentUser.getUsername() + ".");
                    break;
                }
                ObjectNode episodesNode = objectMapper.createObjectNode();
                int count = 0;
                for (Map.Entry<String, Integer> entry : ((HostWrapped) wrapped).
                        getTopEpisodes().entrySet()) {
                    if (count == five) {
                        break;
                    }
                    episodesNode.put(entry.getKey(), entry.getValue());
                    count++;
                }
                resultNode.set("topEpisodes", episodesNode);
                resultNode.put("listeners", ((HostWrapped) wrapped).getListeners());
                object.set("result", resultNode);
            }
            default -> {
            }
        }

        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
