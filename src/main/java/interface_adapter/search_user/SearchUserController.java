package interface_adapter.search_user;

import use_case.friends.SearchUserInputBoundary;
import use_case.friends.SearchUserInputData;

public class SearchUserController {

    private final SearchUserInputBoundary interactor;

    public SearchUserController(SearchUserInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes a search for users based on the provided query string.
     * This method creates a {@code SearchUserInputData} object containing the query
     * and passes it to the interactor for processing.
     *
     * @param query the search term used to find matching users
     */
    public void execute(String query) {
        final SearchUserInputData inputData = new SearchUserInputData(query);
        interactor.execute(inputData);
    }
}
