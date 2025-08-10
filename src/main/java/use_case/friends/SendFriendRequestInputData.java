package use_case.friends;

public class SendFriendRequestInputData {
    private final String sender;
    private final String recipient;

    public SendFriendRequestInputData(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
