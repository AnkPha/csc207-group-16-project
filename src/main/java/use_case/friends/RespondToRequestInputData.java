package use_case.friends;

public class RespondToRequestInputData {
    private final String username;
    private final String fromUser;
    private final boolean accepted;

    public RespondToRequestInputData(String username, String fromUser, boolean accepted) {
        this.username = username;
        this.fromUser = fromUser;
        this.accepted = accepted;
    }

    public String getUsername() {
        return username;
    }

    public String getFromUser() {
        return fromUser;
    }

    public boolean getAccepted() {
        return accepted;
    }
}
