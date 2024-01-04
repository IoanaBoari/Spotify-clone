package globalwaves.user.artist.merch;

import fileio.input.ActionInput;

public final class Merch {
    private String username;
    private Integer createTimestamp;
    private String name;
    private String description;
    private Integer price;
    public Merch() {

    }
    public Merch(final ActionInput action) {
        this.username = action.getUsername();
        this.createTimestamp = action.getTimestamp();
        this.name = action.getName();
        this.description = action.getDescription();
        this.price = action.getPrice();
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }
}
