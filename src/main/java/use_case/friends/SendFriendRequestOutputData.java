package use_case.friends;

public class SendFriendRequestOutputData {
    private final boolean success;
    private final String message;

    public SendFriendRequestOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

