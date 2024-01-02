package globalwaves.user.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.*;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;

import java.util.HashMap;
import java.util.Map;

public final class AddPodcast implements Command {
    public AddPodcast() {

    }

    /**
     * Adds a new podcast for a host on the provided action and generates a response message.
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is a host, a new podcast is added.
     * If the user is not found, not a host, or already has a podcast with the same name,
     * an appropriate error message is generated.
     * Additionally, checks for duplicate episodes in the provided list.
     *
     * @param action The action input specifying the user, command details,
     *               and podcast information.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        UserInput currentUser = null;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                currentUser = userInput;
                break;
            }
        }
        if (currentUser == null) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            if (!currentUser.getType().equals("host")) {
                object.put("message",  action.getUsername() + " is not a host.");
            } else {
                Host host = (Host) currentUser;
                for (PodcastInput podcast : host.getPodcasts()) {
                    if (podcast.getName().equals(action.getName())) {
                        object.put("message", action.getUsername()
                                + " has another podcast with the same name.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                Map<String, Integer> episodeCount = new HashMap<>();
                for (EpisodeInput episode : action.getEpisodes()) {
                    String episodeName = episode.getName();
                    episodeCount.put(episodeName, episodeCount.getOrDefault(episodeName, 0) + 1);
                }
                boolean existsAtLeastTwice = episodeCount.values().stream().
                        anyMatch(count -> count >= 2);
                if (existsAtLeastTwice) {
                    object.put("message", action.getUsername()
                            + " has the same episode in this podcast.");
                } else {
                    PodcastInput newPodcast = new PodcastInput(action);
                    host.getPodcasts().add(newPodcast);
                    Database.getInstance().getLibrary().getPodcasts().add(newPodcast);
                    object.put("message", action.getUsername()
                            + " has added new podcast successfully.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
