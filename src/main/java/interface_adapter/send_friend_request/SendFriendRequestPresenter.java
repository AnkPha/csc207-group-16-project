package interface_adapter.send_friend_request;

import use_case.friends.SendFriendRequestOutputBoundary;
import use_case.friends.SendFriendRequestOutputData;

public class SendFriendRequestPresenter implements SendFriendRequestOutputBoundary {
    private final SendFriendRequestViewModel viewModel;

    public SendFriendRequestPresenter(SendFriendRequestViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SendFriendRequestOutputData outputData) {
        // Existing presenter logic
        if (viewModel == null) {
            System.err.println("SendFriendRequestViewModel is null!");
            return;
        }
        final SendFriendRequestState state = viewModel.getState();
        if (state == null) {
            System.err.println("SendFriendRequestState is null!");
            return;
        }
        state.setSuccess(outputData.getSuccess());
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
