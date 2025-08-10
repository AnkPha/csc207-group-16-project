package use_case.friends;

public interface ViewRequestsInputBoundary {

    /**
     * Executes the process of retrieving pending friend requests.
     *
     * @param inputData the {@code ViewRequestsInputData} containing the requesting user's details
     */
    void execute(ViewRequestsInputData inputData);
}
