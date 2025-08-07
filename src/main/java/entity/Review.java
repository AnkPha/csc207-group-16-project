package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Review {

    private static final int MINRATING = 1;
    private static final int MAXRATING = 5;
    private final Restaurant restaurant;
    private final int reviewId;
    private final User user;
    private final int rating;
    private final LocalDateTime timestamp;
    private final String reviewText;

    public Review(Restaurant restaurant, int reviewId, int rating, LocalDateTime timestamp, User user,
                  String reviewText) {
        if (MINRATING > rating || MAXRATING < rating) {
            throw new IllegalArgumentException("Rating must be between " + MINRATING + " and " + MAXRATING);
        }
        this.restaurant = restaurant;
        this.reviewId = reviewId;
        this.user = user;
        this.rating = rating;
        this.timestamp = timestamp;
        this.reviewText = reviewText;
    }

    public Review(Restaurant restaurant, int reviewId, int rating, LocalDateTime timestamp, User user) {
        this(restaurant, reviewId, rating, timestamp, user, "");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }
}
