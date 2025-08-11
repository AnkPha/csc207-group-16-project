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
    private static int instanceCounter = 0;
    private final int instanceId;

    public InMemoryReviewDataAccessObject() {
        this.instanceId = ++instanceCounter;
        System.out.println("*** CREATED InMemoryReviewDataAccessObject instance #" + instanceId + " ***");
    }
    /**
     * Adds a review for restaurant.
     * @param review the review that a user submitted.
     */
    @Override
    public void addReview(Review review) {

        reviews.put(review.getReviewId(), review);
        Restaurant restaurant = review.getRestaurant();
        restaurant.addRating(review.getRating());

        System.out.println("DEBUG: Added review with ID " + review.getReviewId()
                + " for restaurant: " + review.getRestaurant().getName() + " with rating: " + review.getRating());
        System.out.println("DEBUG: Restaurant's new rating: " + restaurant.getRating());
        System.out.println("DEBUG: Total reviews in system: " + reviews.size());
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
        List<Review> restaurantReviews = new ArrayList<>();

        System.out.println("*** INSTANCE #" + instanceId + ": Looking for reviews for restaurant: " + restaurant.getName() + " ***");
        System.out.println("*** INSTANCE #" + instanceId + ": Total reviews to search through: " + reviews.size() + " ***");

        // Find all reviews for this specific restaurant
        for (Review review : reviews.values()) {
            System.out.println("*** INSTANCE #" + instanceId + ": Comparing restaurant '" + review.getRestaurant().getName() +
                    "' with target '" + restaurant.getName() + "' ***");
            if (review.getRestaurant().equals(restaurant)) {
                restaurantReviews.add(review);
                System.out.println("*** INSTANCE #" + instanceId + ": Found matching review with rating: " + review.getRating() + " ***");
            }
        }

        System.out.println("*** INSTANCE #" + instanceId + ": Found " + restaurantReviews.size() + " reviews for " + restaurant.getName() + " ***");
        return restaurantReviews;
    }

    @Override
    public double getAverageRatingForRestaurant(Restaurant restaurant) {
        final List<Review> allReviewsRestaurant = getRatingsForRestaurant(restaurant);
        if (allReviewsRestaurant.isEmpty()) {
            System.out.println("*** INSTANCE #" + instanceId + ": No reviews found for " + restaurant.getName() + ", returning 0.0 ***");
            return 0.0;
        }
        double sumRating = 0.0;
        for (Review review : allReviewsRestaurant) {
            sumRating += review.getRating();
        }
        double average = sumRating / allReviewsRestaurant.size();
        System.out.println("*** INSTANCE #" + instanceId + ": Average rating for " + restaurant.getName() + " is: " + average + " ***");
        return average;
    }
}
