package use_case.friends;

public class SendFriendRequestInputData {
    public final String sender;
    public final String recipient;

    public SendFriendRequestInputData(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }
}