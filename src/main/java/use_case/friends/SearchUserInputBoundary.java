package use_case.friends;

public interface SearchUserInputBoundary {

    /**
     * Executes the process of searching for users.
     *
     * @param inputData the {@code SearchUserInputData} containing the search query
     */
    void execute(SearchUserInputData inputData);
}
