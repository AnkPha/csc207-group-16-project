package entity;

import entity.Review;
import entity.ReviewFactory;
import entity.User;
import entity.Restaurant;
import java.time.LocalDateTime;

/**
 * Test implementation of ReviewFactory for testing purposes.
 */
public class TestReviewFactory implements ReviewFactory {
    private int reviewIdCounter = 1;

    @Override
    public Review createReview(User user, Restaurant restaurant, int rating) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        return new Review(restaurant, reviewIdCounter++, rating,
                LocalDateTime.now(), user, "Test review text");
    }
}
