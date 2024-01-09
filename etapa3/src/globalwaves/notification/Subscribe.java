package globalwaves.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.searchbar.SelectResults;
import globalwaves.user.artist.Artist;
import globalwaves.user.host.Host;


public final class Subscribe implements Command {
    public Subscribe() {
    }

    /**
     * Processes a user's request to subscribe or unsubscribe from an artist or host.
     * If the user exists, is on a valid page (artist or host),and the subscription status changes,
     * a success message is generated. If the user is not found or not on a valid page,
     * an appropriate message is provided.
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
        if (currentUser == null) {
            object.put("message", "The username " + action.getUsername() + " doesn't exist.");
        } else {
            SelectResults currentPage = null;
            for (SelectResults page : Database.getInstance().getCurrentPages()) {
                if (page.getUsername().equals(currentUser.getUsername())
                        && (page.getSelectArtist() != null
                        || page.getSelectHost() != null)) {
                    currentPage = page;
                    break;
                }
            }
            if (currentPage != null) {
                for (UserSubscriptions user : Database.getInstance().
                        getUserSubscriptionsArrayList()) {
                    if (user.getUsername().equals(currentUser.getUsername())
                            && currentPage.getSelectArtist() != null) {
                        for (Artist artist : user.getSubscribedArtists()) {
                            if (artist.getUsername().equals(currentPage.
                                    getSelectArtist().getUsername())) {
                                object.put("message", currentUser.getUsername()
                                        + " unsubscribed from "
                                        + artist.getUsername() + " successfully.");
                                user.getSubscribedArtists().remove(artist);
                                Menu.setOutputs(Menu.getOutputs().add(object));
                                return;
                            }
                        }
                    } else if (user.getUsername().equals(currentUser.getUsername())
                            && currentPage.getSelectHost() != null) {
                        for (Host host : user.getSubscribedHosts()) {
                            if (host.getUsername().equals(currentPage.
                                    getSelectHost().getUsername())) {
                                object.put("message", currentUser.getUsername()
                                        + " unsubscribed from "
                                        + host.getUsername() + " successfully.");
                                user.getSubscribedHosts().remove(host);
                                Menu.setOutputs(Menu.getOutputs().add(object));
                                return;
                            }
                        }
                    }
                }
                for (UserSubscriptions userSubscriptions : Database.getInstance().
                        getUserSubscriptionsArrayList()) {
                    if (userSubscriptions.getUsername().equals(currentUser.getUsername())) {
                        if (currentPage.getSelectArtist() != null) {
                            userSubscriptions.getSubscribedArtists().
                                    add(currentPage.getSelectArtist());
                            object.put("message", currentUser.getUsername()
                                    + " subscribed to " + currentPage.getSelectArtist().
                                    getUsername() + " successfully.");
                            Menu.setOutputs(Menu.getOutputs().add(object));
                            return;
                        } else if (currentPage.getSelectHost() != null) {
                            userSubscriptions.getSubscribedHosts().
                                    add(currentPage.getSelectHost());
                            object.put("message", currentUser.getUsername()
                                    + " subscribed to " + currentPage.getSelectHost().
                                    getUsername() + " successfully.");
                            Menu.setOutputs(Menu.getOutputs().add(object));
                            return;
                        }
                    }
                }
                UserSubscriptions newUserSubscriptions = new UserSubscriptions(currentUser.
                        getUsername());
                Database.getInstance().getUserSubscriptionsArrayList().add(newUserSubscriptions);
                if (currentPage.getSelectArtist() != null) {
                    newUserSubscriptions.getSubscribedArtists().add(currentPage.getSelectArtist());
                    object.put("message", currentUser.getUsername()
                            + " subscribed to " + currentPage.getSelectArtist().
                            getUsername() + " successfully.");
                    Menu.setOutputs(Menu.getOutputs().add(object));
                    return;
                } else if (currentPage.getSelectHost() != null) {
                    newUserSubscriptions.getSubscribedHosts().add(currentPage.getSelectHost());
                    object.put("message", currentUser.getUsername()
                            + " subscribed to " + currentPage.getSelectHost().
                            getUsername() + " successfully.");
                    Menu.setOutputs(Menu.getOutputs().add(object));
                    return;
                }
            } else {
                object.put("message",
                        "To subscribe you need to be on the page of an artist or host.");
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
