package use_case.friends;

public class RespondToRequestInputData {
    public final String username;
    public final String fromUser;
    public final boolean accepted;

    public RespondToRequestInputData(String username, String fromUser, boolean accepted) {
        this.username = username;
        this.fromUser = fromUser;
        this.accepted = accepted;
    }
}