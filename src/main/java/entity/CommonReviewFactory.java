package entity;

import Search.Restaurant;

import java.time.LocalDateTime;

public class CommonReviewFactory implements ReviewFactory {
    private int nextReviewId = 1;

    @Override
    public Review createReview(User user, Restaurant restaurant, int rating) {
        final int reviewId = nextReviewId++;
        final LocalDateTime timestamp = LocalDateTime.now();
        return new Review(restaurant, reviewId, rating, timestamp, user);
    }
}
