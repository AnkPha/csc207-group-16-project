package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final Map<String, Review> reviews = new HashMap<>();

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Adds a review for the specified restaurant with the given rating and review text.
     * The review is assigned a unique ID based on the current number of reviews and is
     * timestamped with the current date and time.
     *
     * @param restaurant the {@code Restaurant} being reviewed
     * @param rating the numerical rating given to the restaurant
     * @param reviewText the written text of the review
     */
    public void addReview(Restaurant restaurant, int rating, String reviewText) {
        final int reviewId = reviews.size() + 1;
        final Review review = new Review(restaurant, reviewId, rating, java.time.LocalDateTime.now(), this, reviewText);
        reviews.put(restaurant.getName(), review);
    }

    /**
     * Checks whether this user has previously visited and reviewed a given restaurant.
     *
     * @param restaurant the name of the restaurant to check
     * @return {@code true} if a review exists for the restaurant, {@code false} otherwise
     */
    public boolean hasVisited(String restaurant) {
        return reviews.containsKey(restaurant);
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }
}
