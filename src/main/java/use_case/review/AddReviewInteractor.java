package use_case.review;

import entity.Review;
import entity.ReviewFactory;

public class AddReviewInteractor implements AddReviewInputBoundary {
    private final AddReviewAccessInterface reviewDataAccessObject;
    private final AddReviewOutputBoundary reviewPresenter;
    private final ReviewFactory reviewFactory;

    public AddReviewInteractor(AddReviewAccessInterface reviewDataAccessObject,
                               AddReviewOutputBoundary reviewPresenter, ReviewFactory reviewFactory) {
        this.reviewDataAccessObject = reviewDataAccessObject;
        this.reviewPresenter = reviewPresenter;
        this.reviewFactory = reviewFactory;
    }

    @Override
    public void execute(AddReviewInputData inputData) {
        final Review review = reviewFactory.createReview(
                inputData.getUser(),
                inputData.getRestaurant(),
                inputData.getRating()
        );
        reviewDataAccessObject.addReview(review);

        final AddReviewOutputData outputData = new AddReviewOutputData(true, review, null);
        reviewPresenter.prepareSuccessView(outputData);
    }
}
