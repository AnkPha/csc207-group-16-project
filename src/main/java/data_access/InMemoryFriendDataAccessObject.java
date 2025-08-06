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

    private final InMemoryUserDataAccessObject userDao;
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Set<String>> friendRequests = new HashMap<>();
    private final Map<String, Set<String>> friends = new HashMap<>();
    private final UserFactory userFactory;

    public InMemoryFriendDataAccessObject(InMemoryUserDataAccessObject userDao, UserFactory userFactory) {
        this.userDao = userDao;
        this.userFactory = userFactory;
    }

    @Override
    public boolean userExists(String username) {
        return userDao.existsByName(username);
    }

    @Override
    public User getUser(String username) {
        return userDao.get(username);
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
        final Set<String> pending = friendRequests.get(username);
        if (pending != null && pending.remove(fromUser)) {
            friends.get(username).add(fromUser);
            friends.get(fromUser).add(username);
        }
    }

    @Override
    public void rejectRequest(String username, String fromUser) {
        final Set<String> pending = friendRequests.get(username);
        if (pending != null) {
            pending.remove(fromUser);
        }
    }

    @Override
    public List<String> getFriendsList(String username) {
        return new ArrayList<>(friends.getOrDefault(username, new HashSet<>()));
    }

    @Override
    public List<String> searchUsers(String query) {
        final List<String> results = new ArrayList<>();

        // Use the new getAllUsernames method instead of hardcoded users
        final List<String> allUsers = userDao.getAllUsernames();

        for (String username : allUsers) {
            if (username.toLowerCase().contains(query.toLowerCase())) {
                results.add(username);
            }
        }

        return results;
    }

    /**
     * Retrieves all restaurant reviews for the specified list of usernames.
     *
     * @param usernames A list of usernames to retrieve reviews for.
     * @return A map where each key is a username, and the value is a list of RestaurantReview objects
     *         that the user has submitted. If a user has no reviews, the list will be empty.
     */
    @Override
    public Map<String, List<entity.Review>> getReviewsForUsers(List<String> usernames) {
        final Map<String, List<entity.Review>> result = new HashMap<>();
        for (String username : usernames) {
            if (userDao.existsByName(username)) {
                result.put(username, new ArrayList<>(userDao.getUserReviews(username).values()));
            }
            else {
                result.put(username, new ArrayList<>());
            }
        }
        return result;
    }
}
