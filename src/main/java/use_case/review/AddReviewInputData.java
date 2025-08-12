package use_case.review;

import Search.Restaurant;
import entity.User;

public class AddReviewInputData {
    private final int rating;
    private final Restaurant restaurant;
    private final User user;

    public AddReviewInputData(int rating, Restaurant restaurant, User user) {
        this.rating = rating;
        this.restaurant = restaurant;
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getUser() {
        return user;
    }
}
