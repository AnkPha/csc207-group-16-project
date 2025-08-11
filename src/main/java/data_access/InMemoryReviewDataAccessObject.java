package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Restaurant;
import entity.Review;
import entity.User;
import use_case.review.AddReviewAccessInterface;

public class InMemoryReviewDataAccessObject implements AddReviewAccessInterface {

    private final Map<Integer, Review> reviews = new HashMap<>();

    /**
     * Adds a review for restaurant.
     * @param review the review that a user submitted.
     */
    @Override
    public void addReview(Review review) {

        reviews.put(review.getReviewId(), review);
    }

    /**
     * Checks if user has already submitted review for restaurant.
     * @param user       the user submitting a review.
     * @param restaurant the restaurant receiving a review.
     * @return if review already exists
     */
    @Override
    public boolean existsReviewByUserAndRestaurant(User user, Restaurant restaurant) {
        for (Review review : reviews.values()) {
            if (review.getUser().equals(user) && review.getRestaurant().equals(restaurant)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Review> getRatingsForRestaurant(Restaurant restaurant) {
        final ArrayList<Review> listOfReviews = new ArrayList<>();
        for (Review review : reviews.values()) {
            if (review.getRestaurant().getName().equals(restaurant.getName())) {
                listOfReviews.add(review);
            }
        }
        return listOfReviews;
    }

    @Override
    public double getAverageRatingForRestaurant(Restaurant restaurant) {
        final List<Review> allReviewsrestaurant = getRatingsForRestaurant(restaurant);
        if (allReviewsrestaurant.isEmpty()) {
            return 0.0;
        }
        double sumRating = 0.0;
        for (Review review : allReviewsrestaurant) {
            sumRating += review.getRating();
        }
        return sumRating / allReviewsrestaurant.size();
    }
}
