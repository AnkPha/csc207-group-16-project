package interface_adapter.search_user;

import use_case.friends.SearchUserInputBoundary;
import use_case.friends.SearchUserInputData;

public class SearchUserController {

    private final SearchUserInputBoundary interactor;

    public SearchUserController(SearchUserInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String query) {
        final SearchUserInputData inputData = new SearchUserInputData(query);
        interactor.execute(inputData);
    }
}
