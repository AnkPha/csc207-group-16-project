package entity;

import java.time.LocalDateTime;

public class Review {

    private static final int MINRATING = 1;
    private static final int MAXRATING = 5;
    private final Restaurant restaurant;
    private final int reviewId;
    private final User user;
    private final int rating;
    private final LocalDateTime timestamp;
    private final String reviewText;

    public Review(Restaurant restaurant, int reviewId, int rating, LocalDateTime timestamp, User user) {
        if (MINRATING > rating || MAXRATING < rating) {
            throw new IllegalArgumentException("Rating must be between " + MINRATING + " and " + MAXRATING);
        }
        this.restaurant = restaurant;
        this.reviewId = reviewId;
        this.user = user;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int getReviewId() {
        return reviewId;
    }

    public User getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getReviewText() {
        return reviewText;
    }
}
