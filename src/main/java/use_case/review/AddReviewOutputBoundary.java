package use_case.review;

/**
 * The output boundary for the add review use case.
 */
public interface AddReviewOutputBoundary {
    /**
     * Prepares the success view for the add review Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddReviewOutputData outputData);

    /**
     * Prepares the failure view for the Add Review Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
