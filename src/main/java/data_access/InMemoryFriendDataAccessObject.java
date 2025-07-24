package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.User;
import entity.UserFactory;

/**
 * A simple in-memory implementation of FriendDataAccessInterface for testing.
 */
public class InMemoryFriendDataAccessObject implements FriendDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Set<String>> friendRequests = new HashMap<>();
    private final Map<String, Set<String>> friends = new HashMap<>();
    private final UserFactory userFactory;

    public InMemoryFriendDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getName(), user);
        friendRequests.put(user.getName(), new HashSet<>());
        friends.put(user.getName(), new HashSet<>());
    }

    public void createUser(String username) {
        if (!userExists(username)) {
            final User user = userFactory.create(username, "default_password");
            addUser(user);
        }
    }

    @Override
    public boolean sendFriendRequest(String fromUsername, String toUsername) {
        boolean canSend = true;

        if (!userExists(fromUsername)) {
            canSend = false;
        }
        else if (!userExists(toUsername)) {
            canSend = false;
        }
        else if (fromUsername.equals(toUsername)) {
            canSend = false;
        }
        else if (friends.get(fromUsername).contains(toUsername)) {
            canSend = false;
        }
        else if (friendRequests.get(toUsername).contains(fromUsername)) {
            canSend = false;
        }

        if (canSend) {
            friendRequests.get(toUsername).add(fromUsername);
        }

        return canSend;
    }

    @Override
    public List<String> getPendingRequests(String username) {
        return new ArrayList<>(friendRequests.getOrDefault(username, new HashSet<>()));
    }

    @Override
    public void acceptRequest(String username, String fromUser) {
        if (friendRequests.get(username).remove(fromUser)) {
            friends.get(username).add(fromUser);
            friends.get(fromUser).add(username);
        }
    }

    @Override
    public void rejectRequest(String username, String fromUser) {
        friendRequests.get(username).remove(fromUser);
    }

    @Override
    public List<String> getFriendsList(String username) {
        return new ArrayList<>(friends.getOrDefault(username, new HashSet<>()));
    }

    @Override
    public List<String> searchUsers(String query) {
        final List<String> results = new ArrayList<>();
        for (String username : users.keySet()) {
            if (username.toLowerCase().contains(query.toLowerCase())) {
                results.add(username);
            }
        }
        return results;
    }
}
