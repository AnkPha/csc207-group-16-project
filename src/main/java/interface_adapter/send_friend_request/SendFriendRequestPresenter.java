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
        if (viewModel != null && viewModel.getState() != null) {
            final SendFriendRequestState state = viewModel.getState();
            state.setSuccess(outputData.getSuccess());
            state.setMessage(outputData.getMessage());
            viewModel.setState(state);
            viewModel.firePropertyChanged();
        }
    }
}
