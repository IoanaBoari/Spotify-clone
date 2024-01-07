package globalwaves.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.recommendation.Recommendations;
import globalwaves.recommendation.UserPages;
import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;

public final class AddUser implements Command {
    public AddUser() {

    }

    /**
     * Executes the add user operation based on the user type (normal user, artist, host).
     *
     * @param action The ActionInput containing user information.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        int find = 0;
        for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
            if (userInput.getUsername().equals(action.getUsername())) {
                find = 1;
                break;
            }
        }
        if (find == 1) {
            object.put("message", "The username " + action.getUsername() + " is already taken.");
        } else {
            if (action.getType().equals("user")) {
                UserInput newUser = new UserInput(action);
                Database.getInstance().getLibrary().getUsers().add(newUser);
                Recommendations newRecommendations = new Recommendations(newUser.getUsername());
                Database.getInstance().getRecommendations().add(newRecommendations);
                UserPages newPage = new UserPages(newUser.getUsername());
                Database.getInstance().getAllPages().add(newPage);
            } else if (action.getType().equals("artist")) {
                Artist newArtist = new Artist(action);
                Database.getInstance().getLibrary().getUsers().add(newArtist);
                Recommendations newRecommendations = new Recommendations(newArtist.getUsername());
                Database.getInstance().getRecommendations().add(newRecommendations);
                UserPages newPage = new UserPages(newArtist.getUsername());
                Database.getInstance().getAllPages().add(newPage);
            } else if (action.getType().equals("host")) {
                Host newHost = new Host(action);
                Database.getInstance().getLibrary().getUsers().add(newHost);
                Recommendations newRecommendations = new Recommendations(newHost.getUsername());
                Database.getInstance().getRecommendations().add(newRecommendations);
                UserPages newPage = new UserPages(newHost.getUsername());
                Database.getInstance().getAllPages().add(newPage);
            }
            object.put("message", "The username " + action.getUsername()
                    + " has been added successfully.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
