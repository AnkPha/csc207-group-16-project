package use_case.friends;

public interface ViewRequestsOutputBoundary {

    /**
     * Presents the list of pending friend requests.
     *
     * @param outputData the {@code ViewRequestsOutputData} containing the list of usernames
     *                   and any additional display information
     */
    void present(ViewRequestsOutputData outputData);
}
