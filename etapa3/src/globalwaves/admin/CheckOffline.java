package globalwaves.admin;

import fileio.input.ActionInput;
import fileio.input.UserInput;
import globalwaves.Database;

public final class CheckOffline {
    public CheckOffline() {

    }

    /**
     * Checks if the specified user in the given action is in offline mode.
     *
     * @param action The ActionInput containing user information.
     * @return 1 if the user is offline, 0 otherwise.
     */
    public int checkOffline(final ActionInput action) {
        for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getType().equals("user") && user.getMode().equals("offline")) {
                return 1;
            }
        }
        return 0;
    }
}
