package fileio.input;

public class UserInput {
    private String username;
    private int age;
    private String city;
    private String type;
    private String mode;
    private String currentPage;
    public UserInput() {
        this.type = "user";
        this.mode = "online";
        this.currentPage = "Home";
    }
    public UserInput(final ActionInput action) {
        this.username = action.getUsername();
        this.age = action.getAge();
        this.city = action.getCity();
        this.type = action.getType();
        this.currentPage = "Home";
        if (this.type.equals("user")) {
            this.mode = "online";
        } else {
            this.mode = null;
        }
    }
    public final String getUsername() {
        return username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final int getAge() {
        return age;
    }

    public final void setAge(final int age) {
        this.age = age;
    }

    public final String getCity() {
        return city;
    }

    public final void setCity(final String city) {
        this.city = city;
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getMode() {
        return mode;
    }

    public final void setMode(final String mode) {
        this.mode = mode;
    }

    public final String getCurrentPage() {
        return currentPage;
    }

    public final void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }
}

