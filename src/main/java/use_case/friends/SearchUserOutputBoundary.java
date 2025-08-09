package use_case.friends;

public interface SearchUserOutputBoundary {

    /**
     * Presents the results of a user search.
     *
     * @param outputData the {@code SearchUserOutputData} containing the list of matching users
     *                   and any additional display information
     */
    void present(SearchUserOutputData outputData);
}
