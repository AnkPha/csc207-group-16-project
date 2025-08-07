package interface_adapter.send_friend_request;

import use_case.friends.SendFriendRequestInputBoundary;
import use_case.friends.SendFriendRequestInputData;

public class SendFriendRequestController {
    private final SendFriendRequestInputBoundary interactor;

    public SendFriendRequestController(SendFriendRequestInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String sender, String recipient) {
        final SendFriendRequestInputData inputData = new SendFriendRequestInputData(sender, recipient);
        interactor.execute(inputData);
    }
}
