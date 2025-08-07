package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.CommonUser;
import entity.Review;
import entity.User;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void changePassword(User user) {
        // Replace the old entry with the new password
        users.put(user.getName(), user);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    /**
     * Adds a new review for a restaurant by the specified user.
     *
     * @param username     The username of the user submitting the review.
     * @param restaurantName The name of the restaurant being reviewed.
     * @param rating       The rating given to the restaurant (1–5).
     * @param reviewText   The text content of the review.
     */
    public void addReview(String username, String restaurantName, int rating, String reviewText) {
        final User user = users.get(username);
        if (user instanceof CommonUser) {
            final double[] coords = {43.6532, -79.3832};
            final entity.Restaurant restaurant = new entity.Restaurant(
                    restaurantName,
                    "123 Sample St",
                    "Mixed Cuisine",
                    "Vegetarian Options",
                    "10AM - 10PM",
                    "http://example.com",
                    coords

            );
            ((CommonUser) user).addReview(restaurant, rating, reviewText);
        }
    }

    /**
     * Retrieves a map of restaurant reviews submitted by the specified user.
     *
     * @param username The username of the user whose reviews are being retrieved.
     * @return A map where the key is the restaurant name and the value is the corresponding Review object.
     *         If the user does not exist or has not submitted any reviews, an empty map is returned.
     */
    public Map<String, Review> getUserReviews(String username) {
        final User user = users.get(username);
        if (user instanceof CommonUser) {
            return ((CommonUser) user).getReviews();
        }
        return new HashMap<>();
    }

    /**
     * Checks whether the specified user has submitted a review for the given restaurant.
     *
     * @param username   The username of the user.
     * @param restaurant The name of the restaurant.
     * @return true if the user has reviewed the restaurant; false otherwise.
     */
    public boolean hasVisited(String username, String restaurant) {
        final User user = users.get(username);
        return user instanceof CommonUser && ((CommonUser) user).hasVisited(restaurant);
    }

    /**
     * Gets all usernames in the system.
     *
     * @return A list of all usernames in the system
     */
    public java.util.List<String> getAllUsernames() {
        return new java.util.ArrayList<>(users.keySet());
    }

    /**
     * Populates the in-memory user database with sample users and restaurant reviews.
     * This is used for testing or demo purposes only.
     */
    public void populateSampleUsers() {
        save(new CommonUser("alice", "pw"));
        addReview("alice", "Pho House", 5, "Amazing pho and spring rolls!");
        addReview("alice", "Noodle Bar", 4, "Solid, quick, and cheap");

        save(new CommonUser("bob", "pw"));
        addReview("bob", "Green Thai", 5, "Best pad see ew I've ever had");
        addReview("bob", "Pho House", 3, "Good but a little salty");

        save(new CommonUser("carol", "pw"));
        addReview("carol", "Burger Spot", 4, "Tasty, affordable, great late-night place");
    }
}
