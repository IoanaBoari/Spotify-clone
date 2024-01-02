package globalwaves.user.host;

import fileio.input.ActionInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import globalwaves.user.host.Announcement;

import java.util.ArrayList;

public final class Host extends UserInput {
    private ArrayList<PodcastInput> podcasts = new ArrayList<>();
    private ArrayList<Announcement> announcements = new ArrayList<>();
    public Host(final ActionInput action) {
        super(action);
    }
    public Host() {

    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(final ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }
}
