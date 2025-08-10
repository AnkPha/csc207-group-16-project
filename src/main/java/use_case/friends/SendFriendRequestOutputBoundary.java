package use_case.friends;

public interface SendFriendRequestOutputBoundary {

    /**
     * Presents the result of a send friend request action.
     *
     * @param outputData the {@code SendFriendRequestOutputData} containing the success status
     *                   and any relevant messages
     */
    void present(SendFriendRequestOutputData outputData);
}

