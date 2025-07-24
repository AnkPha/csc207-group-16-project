package interface_adapter.main_menu;

public class MainAppState {
    private String username = "";
    private String password = "";

    public MainAppState(MainAppState copy) {
        this.username = copy.username;
        this.password = copy.password;
    }

    public MainAppState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
