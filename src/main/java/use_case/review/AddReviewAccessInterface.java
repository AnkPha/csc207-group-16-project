package use_case.review;

import java.util.List;

import entity.Restaurant;
import entity.Review;
import entity.User;

/**
 * The interface of the DAO for the add review use case.
 */

public interface AddReviewAccessInterface {

    /**
     * Updates the system to record this user's review.
     * @param review the review that a user submitted.
     */
    void addReview(Review review);

    /**
     * Checks if User has already left a review for Restaurant.
     * @param user the user submitting a review.
     * @param restaurant the restaurant receiving a review.
     * @return true or false.
     */
    boolean existsReviewByUserAndRestaurant(User user, Restaurant restaurant);

    /**
     * Returns all reviews for a restaurant.
     * @param restaurant restaurant displaying reviews.
     * @return list of reviews.
     */
    List<Review> getRatingsForRestaurant(Restaurant restaurant);

    /**
     * Returns Average Rating of Restaurant.
     * @param restaurant restaurant.
     * @return averageRating average rating of reviews.
     */
    double getAverageRatingForRestaurant(Restaurant restaurant);
}
