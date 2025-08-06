package interface_adapter.search_user;

import use_case.friends.SearchUserOutputBoundary;
import use_case.friends.SearchUserOutputData;

public class SearchUserPresenter implements SearchUserOutputBoundary {

    private final SearchUserViewModel viewModel;

    public SearchUserPresenter(SearchUserViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SearchUserOutputData outputData) {
        viewModel.setResults(outputData.results(), outputData.getUserReviews());
    }
}
