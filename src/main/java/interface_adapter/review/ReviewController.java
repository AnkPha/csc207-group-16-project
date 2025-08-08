package interface_adapter.review;

import use_case.review.AddReviewInputBoundary;
import use_case.review.AddReviewInputData;
import entity.Restaurant;
import entity.User;

public class ReviewController {
    private AddReviewInputBoundary addReviewUseCaseInteractor;

    public ReviewController(AddReviewInputBoundary addReviewInteractor) {
        this.addReviewUseCaseInteractor = addReviewUseCaseInteractor;
    }

    public void execute(int rating, String reviewText, Restaurant restaurant, User user) {
        final AddReviewInputData inputData = new AddReviewInputData(rating, restaurant, user);
        addReviewUseCaseInteractor.execute(inputData);
    }
}
