package use_case.friends;

import java.util.List;

import data_access.FriendDataAccessInterface;

public class SearchUserInteractor implements SearchUserInputBoundary {
    private final FriendDataAccessInterface dataAccess;
    private final SearchUserOutputBoundary presenter;

    public SearchUserInteractor(FriendDataAccessInterface dataAccess, SearchUserOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchUserInputData inputData) {
        final List<String> results = dataAccess.searchUsers(inputData.query);
        presenter.present(new SearchUserOutputData(results));
    }
}
