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

    public void addReview(Restaurant restaurant, int rating, String reviewText) {
        final int reviewId = reviews.size() + 1;
        final Review review = new Review(restaurant, reviewId, rating, java.time.LocalDateTime.now(), this, reviewText);
        reviews.put(restaurant.getName(), review);
    }

    public boolean hasVisited(String restaurant) {
        return reviews.containsKey(restaurant);
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }
}
