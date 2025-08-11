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

    private static int instanceCounter;
    private final Map<Integer, Review> reviews = new HashMap<>();
    private final int instanceId;

    private final String debugEnd = " ***";
    private final String debugStart = "*** INSTANCE #";

    public InMemoryReviewDataAccessObject() {
        this.instanceId = ++instanceCounter;
        System.out.println("*** CREATED InMemoryReviewDataAccessObject instance #" + instanceId + debugEnd);
    }

    /**
     * Adds a review for restaurant.
     * @param review the review that a user submitted.
     */
    @Override
    public void addReview(Review review) {

        reviews.put(review.getReviewId(), review);
        final Restaurant restaurant = review.getRestaurant();
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
        boolean exists = false;
        for (Review review : reviews.values()) {
            if (review.getUser().equals(user) && review.getRestaurant().equals(restaurant)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    @Override
    public List<Review> getRatingsForRestaurant(Restaurant restaurant) {
        final List<Review> restaurantReviews = new ArrayList<>();

        System.out.println(debugStart + instanceId + ": Looking for reviews for restaurant: "
                + restaurant.getName() + debugEnd);
        System.out.println(debugStart + instanceId + ": Total reviews to search through: "
                + reviews.size() + debugEnd);

        for (Review review : reviews.values()) {
            System.out.println(debugStart + instanceId + ": Comparing restaurant '"
                    + review.getRestaurant().getName() + "' with target '" + restaurant.getName() + debugEnd);
            if (review.getRestaurant().equals(restaurant)) {
                restaurantReviews.add(review);
                System.out.println(debugStart + instanceId + ": Found matching review with rating: "
                        + review.getRating() + debugEnd);
            }
        }

        System.out.println(debugStart + instanceId + ": Found " + restaurantReviews.size()
                + " reviews for " + restaurant.getName() + debugEnd);
        return restaurantReviews;
    }

    @Override
    public double getAverageRatingForRestaurant(Restaurant restaurant) {
        final List<Review> allReviewsRestaurant = getRatingsForRestaurant(restaurant);
        double average = 0.0;
        if (allReviewsRestaurant.isEmpty()) {
            System.out.println(debugStart + instanceId + ": No reviews found for " + restaurant.getName()
                    + ", returning 0.0 ***");
        }
        else {
            double sumRating = 0.0;
            for (Review review : allReviewsRestaurant) {
                sumRating += review.getRating();
            }
            average = sumRating / allReviewsRestaurant.size();
            System.out.println(debugStart + instanceId + ": Average rating for "
                    + restaurant.getName() + " is: " + average + debugEnd);
        }
        return average;
    }
}
