package interface_adapter.send_friend_request;

import use_case.friends.SendFriendRequestInputBoundary;
import use_case.friends.SendFriendRequestInputData;

public class SendFriendRequestController {
    private final SendFriendRequestInputBoundary interactor;

    public SendFriendRequestController(SendFriendRequestInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates the process of sending a friend request from the specified sender to the specified recipient.
     * This method creates a {@code SendFriendRequestInputData} object containing the sender and recipient
     * usernames, and passes it to the interactor for processing.
     *
     * @param sender the username of the user sending the friend request
     * @param recipient the username of the user receiving the friend request
     */
    public void execute(String sender, String recipient) {
        final SendFriendRequestInputData inputData = new SendFriendRequestInputData(sender, recipient);
        interactor.execute(inputData);
    }
}
