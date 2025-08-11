package use_case.friends;

import data_access.FriendDataAccessInterface;

public class SendFriendRequestInteractor implements SendFriendRequestInputBoundary {
    private final FriendDataAccessInterface dataAccess;
    private final SendFriendRequestOutputBoundary presenter;

    public SendFriendRequestInteractor(FriendDataAccessInterface dataAccess,
                                       SendFriendRequestOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SendFriendRequestInputData inputData) {
        final boolean success = dataAccess.sendFriendRequest(inputData.getSender(), inputData.getRecipient());
        final String message;
        if (success) {
            message = "Request sent.";
        }
        else {
            message = "Request failed (maybe already friends or pending).";
        }
        presenter.present(new SendFriendRequestOutputData(success, message));
    }
}
