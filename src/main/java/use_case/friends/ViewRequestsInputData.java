package use_case.friends;

public class ViewRequestsInputData {
    private final String username;

    public ViewRequestsInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
