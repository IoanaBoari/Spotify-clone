package globalwaves.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.commands.Command;
import globalwaves.player.LoadResults;
import globalwaves.searchbar.SearchResults;
import globalwaves.searchbar.SelectResults;

public final class LoadRecommendations implements Command {
    public LoadRecommendations() {
    }

    /**
     * Executes the action to load playback based on user recommendations.
     * Checks if the user is offline and loads the appropriate recommendation.
     *
     * @param action The ActionInput containing information necessary for executing the command.
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
        if (new CheckOffline().checkOffline(action) == 1) {
            object.put("message", action.getUsername() + " is offline.");
        } else {
            boolean found = false;
            for (Recommendations recommendations : Database.getInstance().getRecommendations()) {
                if (recommendations.getUsername().equals(action.getUsername())) {
                    if (recommendations.getLastRecommendation() == null) {
                        break;
                    }
                    found = true;
                    for (LoadResults loadResults : Database.getInstance().
                            getLoadResultsArrayList()) {
                        if (loadResults.getUsername().equals(action.getUsername())) {
                            Database.getInstance().getLoadResultsArrayList().
                                    remove(loadResults);
                            break;
                        }
                    }
                    for (SelectResults selectResults : Database.getInstance().
                            getSelectResultsArrayList()) {
                        if (selectResults.getUsername().equals(action.getUsername())) {
                            Database.getInstance().getSelectResultsArrayList().
                                    remove(selectResults);
                            break;
                        }
                    }
                    for (SearchResults searchResults : Database.getInstance().
                            getSearchResultsArrayList()) {
                        if (searchResults.getUsername().equals(action.getUsername())) {
                            Database.getInstance().getSearchResultsArrayList().
                                    remove(searchResults);
                            break;
                        }
                    }
                    switch (recommendations.getLastRecommendation()) {
                        case "random_song" -> {
                            LoadResults loadRecommendation = new LoadResults(action,
                                    recommendations.getSongRecommendations().get(recommendations.
                                            getSongRecommendations().size() - 1),
                                    null, null, null);
                            Database.getInstance().getLoadResultsArrayList().
                                    add(loadRecommendation);
                        }
                        case "random_playlist" -> {
                            LoadResults loadRecommendation = new LoadResults(action, null, null,
                                    recommendations.getPlaylistRecommend().get(recommendations.
                                            getPlaylistRecommend().size() - 1), null);
                            Database.getInstance().getLoadResultsArrayList().
                                    add(loadRecommendation);
                        }
                        case "fans_playlist" -> {
                            LoadResults loadRecommendation = new LoadResults(action, null, null,
                                    recommendations.getPlaylistRecommend().get(recommendations.
                                            getPlaylistRecommend().size() - 1), null);
                            Database.getInstance().getLoadResultsArrayList().
                                    add(loadRecommendation);
                        }
                        default -> {
                        }
                    }
                }
            }
            if (found) {
                object.put("message", "Playback loaded successfully.");
            } else {
                object.put("message", "No recommendations available.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
