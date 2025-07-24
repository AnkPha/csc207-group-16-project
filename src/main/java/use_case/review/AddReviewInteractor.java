package use_case.review;

import entity.Review;
import entity.UserFactory;

public class AddReviewInteractor implements AddReviewInputBoundary {
    private final AddReviewAccessInterface reviewDataAccessObject;
    private final AddReviewOutputBoundary reviewPresenter;


    public AddReviewInteractor(AddReviewAccessInterface reviewDataAccessObject,
                               AddReviewOutputBoundary reviewPresenter) {
        this.reviewDataAccessObject = reviewDataAccessObject;
        this.reviewPresenter = reviewPresenter;
    }

    @Override
    public void execute(AddReviewInputData inputData) {
        final Review review
    }
}
