package use_case.friends;

public interface RespondToRequestOutputBoundary {
    /**
     * Presents the result of responding to a friend request.
     *
     * @param outputData the {@code RespondToRequestOutputData} containing
     *                   the result details, such as success status and message
     */
    void present(RespondToRequestOutputData outputData);
}
