package globalwaves.user.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.Menu;
import globalwaves.admin.Artist;
import globalwaves.commands.Command;

public final class AddEvent implements Command {
    private final Integer february = 2;
    private final Integer three = 3;
    private final Integer five = 5;
    private final Integer six = 6;
    private final Integer minYear = 1900;
    private final Integer maxYear = 2023;
    private final Integer minMonth = 1;
    private final Integer maxMonth = 12;
    private final Integer maxDaysFebruary = 28;
    private final Integer maxDays = 31;

    public AddEvent() {

    }

    /**
     * Checks if the provided date string has a valid format (dd-MM-yyyy).
     *
     * @param date The date string to be validated.
     * @return True if the date has a valid format, false otherwise.
     */
    private static boolean isValidDateFormat(final String date) {
        String format = "\\d{2}-\\d{2}-\\d{4}";
        return date.matches(format);
    }

    /**
     * Checks if the provided date string has a valid format and represents a valid date.
     *
     * @param date The date string to be validated.
     * @return True if the date has a valid format and is a valid date, false otherwise.
     */
    private boolean isValidDate(final String date) {
        int year = Integer.parseInt(date.substring(six));
        int month = Integer.parseInt(date.substring(three, five));
        int day = Integer.parseInt(date.substring(0, 2));
        if (year < minYear || year > maxYear) {
            return false;
        }
        if (month < minMonth || month > maxMonth) {
            return false;
        }
        if (month == february && day > maxDaysFebruary) {
            return false;
        }
        if (day > maxDays) {
            return false;
        }
        return true;
    }

    /**
     * Executes the provided action, adding a new event for an artist
     *
     * Checks if the specified username exists in the library's users.
     * If found and the user is an artist, adds a new event for the artist user.
     * If the user is not found, not an artist, or already has an event with the same name,
     * an appropriate error message is generated. Additionally, validates the date format
     * and checks if the date is a valid date.
     *
     * @param action The action input specifying the user, command details, and event information.
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
            if (!currentUser.getType().equals("artist")) {
                object.put("message", action.getUsername() + " is not an artist.");
            } else {
                Artist artist = (Artist) currentUser;
                for (Event event : artist.getEvents()) {
                    if (event.getName().equals(action.getName())) {
                        object.put("message", action.getUsername()
                                + " has another album with the same name.");
                        Menu.setOutputs(Menu.getOutputs().add(object));
                        return;
                    }
                }
                if (isValidDateFormat(action.getDate()) && isValidDate(action.getDate())) {
                    Event newEvent = new Event(action);
                    artist.getEvents().add(newEvent);
                    object.put("message", action.getUsername()
                            + " has added new event successfully.");
                } else {
                    object.put("message", "Event for " + action.getUsername()
                            + " does not have a valid date.");
                }
            }
        }
        Menu.setOutputs(Menu.getOutputs().add(object));
    }
}
