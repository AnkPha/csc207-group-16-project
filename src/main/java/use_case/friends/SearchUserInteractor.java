package use_case.friends;

import java.util.List;
import java.util.Map;

import data_access.FriendDataAccessInterface;
import entity.Review;

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
        final Map<String, List<Review>> reviews = dataAccess.getReviewsForUsers(results);
        presenter.present(new SearchUserOutputData(results, reviews));
    }
}
