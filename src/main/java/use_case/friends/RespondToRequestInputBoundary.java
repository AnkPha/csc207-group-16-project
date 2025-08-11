package use_case.friends;

public interface RespondToRequestInputBoundary {

    /**
     * Executes the friend request response process based on the provided input data.
     *
     * @param inputData the {@code RespondToRequestInputData} containing the
     *                  usernames involved and the acceptance/rejection decision
     */
    void execute(RespondToRequestInputData inputData);
}
