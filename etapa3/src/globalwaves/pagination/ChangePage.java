package globalwaves.pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.CheckOffline;
import globalwaves.commands.Command;
import globalwaves.player.LoadResults;
import globalwaves.recommendation.UserPages;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;


public final class ChangePage implements Command {
    public ChangePage() {

    }

    /**
     * Executes the provided action, updating the user's current page
     * and generating a response message.
     * Checks if the user is offline using the CheckOffline class.
     * If the user is offline, returns an appropriate message.
     * Otherwise, updates the user's current page based on the provided action.
     * Handles specific cases for the "Home" and "LikedContent" pages.
     *
     * @param action The action input specifying the user, command details, and the next page.
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
        } else if (action.getNextPage().equals("Home")
                || action.getNextPage().equals("LikedContent")) {
            currentUser.setCurrentPage(action.getNextPage());
            for (SelectResults results : Database.getInstance().getCurrentPages()) {
                if (results.getUsername().equals(currentUser.getUsername())) {
                    Database.getInstance().getCurrentPages().remove(results);
                    break;
                }
            }
            SelectResults currentPage = new SelectResults();
            currentPage.setUsername(action.getUsername());
            currentPage.setLastCommand(action.getNextPage());
            for (UserPages userPages : Database.getInstance().getAllPages()) {
                if (userPages.getUsername().equals(action.getUsername())) {
                    userPages.getPages().add(currentPage);
                    userPages.setPageIdx(userPages.getPages().size() - 1);
                }
            }
            object.put("message", action.getUsername() + " accessed "
                    + action.getNextPage() + " successfully.");
        } else if (action.getNextPage().equals("Artist")) {
            for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                if (user.getUsername().equals(action.getUsername())) {
                    user.setCurrentPage("Artist");
                }
            }
            for (SelectResults currentPages : Database.getInstance().getCurrentPages()) {
                if (currentPages.getUsername().equals(action.getUsername())) {
                    Database.getInstance().getCurrentPages().remove(currentPages);
                    break;
                }
            }
            SelectResults newPage = new SelectResults();
            newPage.setUsername(action.getUsername());
            newPage.setLastCommand(action.getNextPage());
            String artistName = new String();
            for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
                if (loadResults.getUsername().equals(action.getUsername())) {
                    if (loadResults.getLoadedSong() != null) {
                        artistName = loadResults.getLoadedSong().getArtist();
                    } else if (loadResults.getLoadedAlbum() != null) {
                        artistName = loadResults.getLoadedAlbum().getUsername();
                    } else if (loadResults.getLoadedPlaylist() != null) {
                        int idx = loadResults.getLoadedPlaylist().getCurrentSongIndex();
                        artistName = loadResults.getLoadedPlaylist().getSongs().
                                get(idx).getArtist();
                    }
                    break;
                }
            }
            for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
                if (userInput.getUsername().equals(artistName)) {
                    Artist artist = (Artist) userInput;
                    newPage.setSelectArtist(artist);
                    break;
                }
            }
            for (UserPages userPages : Database.getInstance().getAllPages()) {
                if (userPages.getUsername().equals(action.getUsername())) {
                    userPages.getPages().add(newPage);
                    userPages.setPageIdx(userPages.getPages().size() - 1);
                }
            }
            Database.getInstance().getCurrentPages().add(newPage);
            object.put("message", action.getUsername() + " accessed "
                    + action.getNextPage() + " successfully.");
        } else if (action.getNextPage().equals("Host")) {
            for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                if (user.getUsername().equals(action.getUsername())) {
                    user.setCurrentPage("Host");
                }
            }
            for (SelectResults currentPages : Database.getInstance().getCurrentPages()) {
                if (currentPages.getUsername().equals(action.getUsername())) {
                    Database.getInstance().getCurrentPages().remove(currentPages);
                    break;
                }
            }
            SelectResults newPage = new SelectResults();
            newPage.setUsername(action.getUsername());
            newPage.setLastCommand(action.getNextPage());
            String hostName = new String();
            for (LoadResults loadResults : Database.getInstance().getLoadResultsArrayList()) {
                if (loadResults.getUsername().equals(action.getUsername())) {
                    if (loadResults.getLoadedPodcast() != null) {
                        hostName = loadResults.getLoadedPodcast().getOwner();
                    }
                    break;
                }
            }
            for (UserInput userInput : Database.getInstance().getLibrary().getUsers()) {
                if (userInput.getUsername().equals(hostName)) {
                    Host host = (Host) userInput;
                    newPage.setSelectHost(host);
                    break;
                }
            }
            for (UserPages userPages : Database.getInstance().getAllPages()) {
                if (userPages.getUsername().equals(action.getUsername())) {
                    userPages.getPages().add(newPage);
                    userPages.setPageIdx(userPages.getPages().size() - 1);
                }
            }
            Database.getInstance().getCurrentPages().add(newPage);
            object.put("message", action.getUsername() + " accessed "
                    + action.getNextPage() + " successfully.");
        } else {
            object.put("message", action.getUsername()
                    + " is trying to access a non-existent page.");
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
