package globalwaves.searchbar;

import fileio.input.Filters;
import fileio.input.UserInput;
import globalwaves.Database;
import globalwaves.admin.Host;

import java.util.ArrayList;

public final class SearchHost {
    public SearchHost() {

    }

    /**
     * Performs a search for hosts based on the specified filters.
     *
     * @param filters The filters to be applied to the search.
     * @return An ArrayList of hosts that match the specified filters.
     */
    public ArrayList<Host> doSearch(final Filters filters) {
        ArrayList<Host> searchedHosts = new ArrayList<>();
        if (filters.getName() != null) {
            for (UserInput user : Database.getInstance().getLibrary().getUsers()) {
                if (user.getType().equals("host") && user.getUsername().
                        startsWith(filters.getName())) {
                    Host host = (Host) user;
                    searchedHosts.add(host);
                }
            }
        }
        return searchedHosts;
    }
}
