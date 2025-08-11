package interface_adapter.review;

import entity.Restaurant;
import entity.User;
import use_case.review.AddReviewInputBoundary;
import use_case.review.AddReviewInputData;

public class ReviewController {
    private AddReviewInputBoundary addReviewUseCaseInteractor;

    public ReviewController(AddReviewInputBoundary addReviewInteractor) {
        this.addReviewUseCaseInteractor = addReviewInteractor;
    }

    /**
     * Executes the review usecase.
     * @param rating rating of review
     * @param reviewText review text
     * @param restaurant restaurant receiving review
     * @param user user inputting review
     */
    public void execute(int rating, String reviewText, Restaurant restaurant, User user) {
        final AddReviewInputData inputData = new AddReviewInputData(rating, restaurant, user);
        addReviewUseCaseInteractor.execute(inputData);
    }
}
