package data_access;

import entity.User;

import java.util.List;

/**
 * An interface for managing friend-related data operations in the application.
 * Implementations of this interface handle friend requests, friend lists,
 * and user existence checks for friend-related use cases.
 */
public interface FriendDataAccessInterface {
    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    boolean userExists(String username);

    User getUser(String username);

    /**
     * Sends a friend request from one user to another.
     *
     * @param fromUsername the username of the user sending the request
     * @param toUsername the username of the user receiving the request
     * @return true if the request was sent successfully; false otherwise
     */
    boolean sendFriendRequest(String fromUsername, String toUsername);

    /**
     * Gets the list of usernames who have sent a pending friend request to the given user.
     *
     * @param username the username whose pending friend requests are to be retrieved
     * @return a list of usernames with pending requests
     */
    List<String> getPendingRequests(String username);

    /**
     * Accepts a friend request from one user to another.
     *
     * @param username the username of the user who sent the request
     * @param fromUser the username of the user who is accepting the request
     */
    void acceptRequest(String username, String fromUser);

    /**
     * Rejects a friend request from one user to another.
     *
     * @param username the username of the user who sent the request
     * @param fromUser the username of the user who is rejecting the request
     */
    void rejectRequest(String username, String fromUser);

    /**
     * Gets the list of usernames who are friends with the given user.
     *
     * @param username the username whose friend list is to be retrieved
     * @return a list of usernames who are friends with the user
     */
    List<String> getFriendsList(String username);

    /**
     * Gets the list of usernames who are friends with the given user.
     *
     * @param query the username whose friend list is to be retrieved
     * @return a list of usernames who are friends with the user
     */
    List<String> searchUsers(String query);

}

