package globalwaves.commands;

import fileio.input.ActionInput;
import globalwaves.admin.AddUser;
import globalwaves.admin.DeleteUser;
import globalwaves.admin.ShowAlbums;
import globalwaves.admin.ShowPodcasts;
import globalwaves.generalstats.*;
import globalwaves.pagination.ChangePage;
import globalwaves.pagination.PrintCurrentPage;
import globalwaves.user.artist.*;
import globalwaves.user.host.AddAnnouncement;
import globalwaves.user.host.AddPodcast;
import globalwaves.user.host.RemoveAnnouncement;
import globalwaves.user.host.RemovePodcast;
import globalwaves.user.normalUser.SwitchConnectionStatus;
import globalwaves.userstats.Wrapped;

import java.util.ArrayList;
import java.util.List;

public final class Invoker {
    private List<Command> commands = new ArrayList<>();

    /**
     * The execute method acts as an invoker, delegating commands to corresponding command objects.
     * It uses a switch statement to identify the command
     * and execute the appropriate command object.
     *
     * @param action The action input containing information necessary for executing the command.
     */
    public void execute(final ActionInput action) {
        switch (action.getCommand()) {
            case "search" -> execute(new DoSearch(), action);
            case "select" -> execute(new DoSelect(), action);
            case "load" -> execute(new DoLoad(), action);
            case "status" -> execute(new DoStatus(), action);
            case "playPause" -> execute(new DoPlayPause(), action);
            case "createPlaylist" -> execute(new DoCreatePlaylist(), action);
            case "switchVisibility" -> execute(new DoSwitchVisibility(), action);
            case "showPlaylists" -> execute(new DoShowPlaylists(), action);
            case "addRemoveInPlaylist" -> execute(new DoAddRemoveInPlaylist(), action);
            case "like" -> execute(new DoLike(), action);
            case "showPreferredSongs" -> execute(new DoShowPreferredSongs(), action);
            case "follow" -> execute(new DoFollow(), action);
            case "getTop5Playlists" -> execute(new GetTop5Playlists(), action);
            case "getTop5Songs" -> execute(new GetTop5Songs(), action);
            case "repeat" -> execute(new DoRepeat(), action);
            case "next" -> execute(new DoNext(), action);
            case "prev" -> execute(new DoPrev(), action);
            case "forward" -> execute(new DoForward(), action);
            case "backward" -> execute(new DoBackward(), action);
            case "shuffle" -> execute(new DoShuffle(), action);
            case "switchConnectionStatus" -> execute(new SwitchConnectionStatus(), action);
            case "getOnlineUsers" -> execute(new GetOnlineUsers(), action);
            case "addUser" -> execute(new AddUser(), action);
            case "addAlbum" -> execute(new AddAlbum(), action);
            case "removeAlbum" -> execute(new RemoveAlbum(), action);
            case "showAlbums" -> execute(new ShowAlbums(), action);
            case "printCurrentPage" -> execute(new PrintCurrentPage(), action);
            case "addEvent" -> execute(new AddEvent(), action);
            case "removeEvent" -> execute(new RemoveEvent(), action);
            case "addMerch" -> execute(new AddMerch(), action);
            case "getAllUsers" -> execute(new GetAllUsers(), action);
            case "getTop5Albums" -> execute(new GetTop5Albums(), action);
            case "deleteUser" -> execute(new DeleteUser(), action);
            case "addPodcast" -> execute(new AddPodcast(), action);
            case "removePodcast" -> execute(new RemovePodcast(), action);
            case "addAnnouncement" -> execute(new AddAnnouncement(), action);
            case "removeAnnouncement" -> execute(new RemoveAnnouncement(), action);
            case "showPodcasts" -> execute(new ShowPodcasts(), action);
            case "changePage" -> execute(new ChangePage(), action);
            case "getTop5Artists" -> execute(new GetTop5Artists(), action);
            case "wrapped" -> execute(new Wrapped(), action);
            case "buyPremium" -> {

            }
            case "cancelPremium" -> {

            }
            case "adBreak" -> {

            }
            case "subscribe" -> {

            }
            case "getNotifications" -> {

            }
            case "buyMerch" -> {

            }
            case "seeMerch" -> {

            }
            case "updateRecommendations" -> {

            }
            case "previousPage" -> {

            }
            case "loadRecommendations" -> {

            }
            case "nextPage" -> {

            }
            default -> {
            }
        }
    }

    /**
     * Executes the provided command with the given action
     * and adds the command to the list of executed commands.
     *
     * @param command The command to execute.
     * @param action  The action input containing information necessary for executing the command.
     */
    public void execute(final Command command, final ActionInput action) {
        commands.add(command);
        command.execute(action);
    }
}
