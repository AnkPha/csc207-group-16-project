package use_case.review;

/**
 * The add review use case.
 */

public interface AddReviewInputBoundary {

    /**
     * Execute the add review use case.
     * @param addReviewInputData the input data for this use case.
     */
    void execute(AddReviewInputData addReviewInputData);
}
