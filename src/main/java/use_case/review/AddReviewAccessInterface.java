package use_case.review;

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
}
