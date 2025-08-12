package entity;

import Search.Restaurant;

/**
 * Factory for creating viewers.
 */
public interface ReviewFactory {
    /**
     * Creates a new review.
     * @param user the user that submitted review.
     * @param restaurant the restaurant receiving a review.
     * @param rating the rating of restaurant.
     * @return review the new review.
     */
    Review createReview(User user, Restaurant restaurant, int rating);
}
