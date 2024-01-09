package globalwaves.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.commands.Command;
import globalwaves.user.artist.Album;
import globalwaves.user.artist.Event;
import globalwaves.user.artist.merch.Merch;

import java.util.Iterator;

public final class GetNotifications implements Command {
    public GetNotifications() {
    }

    /**
     * Processes a user's request to view new notifications.
     * Generates notification messages for new albums, merchandise, and events, if any,
     * and adds them to the user's notification list.
     * Clears the user's new notifications after processing.
     *
     * @param action The ActionInput containing information necessary for executing the command.
     */
    public void execute(final ActionInput action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();
        object.put("command", action.getCommand());
        object.put("user", action.getUsername());
        object.put("timestamp", action.getTimestamp());
        ArrayNode notificationNode = objectMapper.createArrayNode();
        for (Notification notification : Database.getInstance().getNewNotifications()) {
            if (notification.getUsername().equals(action.getUsername())) {
                if (notification.getNewAlbums() != null) {
                    Iterator<Album> iterator = notification.getNewAlbums().iterator();
                    while (iterator.hasNext()) {
                        Album album = iterator.next();
                        ObjectNode albumNode = objectMapper.createObjectNode();
                        albumNode.put("name", "New Album");
                        albumNode.put("description", "New Album from "
                                + album.getUsername() + ".");
                        iterator.remove();
                        notificationNode.add(albumNode);
                    }
                }
                if (notification.getNewMerch() != null) {
                    Iterator<Merch> iterator = notification.getNewMerch().iterator();
                    while (iterator.hasNext()) {
                        Merch merch = iterator.next();
                        ObjectNode merchNode = objectMapper.createObjectNode();
                        merchNode.put("name", "New Merchandise");
                        merchNode.put("description", "New Merchandise from "
                                + merch.getUsername() + ".");
                        iterator.remove();
                        notificationNode.add(merchNode);
                    }
                }
                if (notification.getNewEvents() != null) {
                    Iterator<Event> iterator = notification.getNewEvents().iterator();
                    while (iterator.hasNext()) {
                        Event event = iterator.next();
                        ObjectNode eventNode = objectMapper.createObjectNode();
                        eventNode.put("name", "New Event");
                        eventNode.put("description", "New Event from "
                                + event.getUsername() + ".");
                        iterator.remove();
                        notificationNode.add(eventNode);
                    }
                }
            }
        }
        object.set("notifications", notificationNode);
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
