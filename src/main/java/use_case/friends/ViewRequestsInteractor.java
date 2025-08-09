package use_case.friends;

import java.util.List;

import data_access.FriendDataAccessInterface;

public class ViewRequestsInteractor implements ViewRequestsInputBoundary {
    private final FriendDataAccessInterface dataAccess;
    private final ViewRequestsOutputBoundary presenter;

    public ViewRequestsInteractor(FriendDataAccessInterface dataAccess, ViewRequestsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewRequestsInputData inputData) {
        final List<String> pendingRequests = dataAccess.getPendingRequests(inputData.getUsername());
        presenter.present(new ViewRequestsOutputData(pendingRequests));
    }
}
