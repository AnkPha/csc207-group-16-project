package use_case.filter;

/**
 * The output boundary for the Change Password Use Case.
 */
public interface FilterOutputBoundary {
    /**
     * Prepares the success view for the Filter Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(FilterOutputData outputData);

    /**
     * Prepares the failure view for the Filter Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
