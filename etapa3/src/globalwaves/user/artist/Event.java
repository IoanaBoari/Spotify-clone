package globalwaves.user.artist;

import fileio.input.ActionInput;

public final class Event {
    private String username;
    private Integer createTimestamp;
    private String name;
    private String description;
    private String date;
    public Event() {

    }
    public Event(final ActionInput action) {
        this.username = action.getUsername();
        this.createTimestamp = action.getTimestamp();
        this.name = action.getName();
        this.description = action.getDescription();
        this.date = action.getDate();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Integer getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(final Integer createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
