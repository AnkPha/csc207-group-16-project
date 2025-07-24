package use_case.review;

import entity.Review;

/**
 * Output Data for the add review use case.
 */
public class AddReviewOutputData {
    private final boolean success;
    private final Review review;
    private final String errorMessage;

    public AddReviewOutputData(boolean success, Review review, String errorMessage) {
        this.success = success;
        this.review = review;
        this.errorMessage = errorMessage;
    }

    public Review getReview() {
        return review;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
