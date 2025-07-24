package entity;

public class FriendRequest {
    private final String sender;
    private final String receiver;
    private final boolean isPending;

    public FriendRequest(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.isPending = true;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isPending() {
        return isPending;
    }
}
