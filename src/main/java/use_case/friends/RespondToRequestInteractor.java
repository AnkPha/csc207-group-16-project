package use_case.friends;

import data_access.FriendDataAccessInterface;

public class RespondToRequestInteractor implements RespondToRequestInputBoundary {
    private final FriendDataAccessInterface dataAccess;
    private final RespondToRequestOutputBoundary presenter;

    public RespondToRequestInteractor(FriendDataAccessInterface dataAccess, RespondToRequestOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(RespondToRequestInputData inputData) {
        final String message;

        if (inputData.accepted) {
            dataAccess.acceptRequest(inputData.username, inputData.fromUser);
            message = "Friend request accepted.";
        }
        else {
            dataAccess.rejectRequest(inputData.username, inputData.fromUser);
            message = "Friend request rejected.";
        }

        presenter.present(new RespondToRequestOutputData(message));
    }
}
