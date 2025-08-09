package use_case.friends;

public interface SendFriendRequestInputBoundary {

    /**
     * Executes the process of sending a friend request.
     *
     * @param inputData the {@code SendFriendRequestInputData} containing the sender
     *                  and recipient usernames
     */
    void execute(SendFriendRequestInputData inputData);
}
