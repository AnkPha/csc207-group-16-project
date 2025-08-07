package use_case.favorite_list;

public interface AddToFavoritesOutputBoundary {
    /**
     * Prepares the success view for the favorites list use case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddToFavoritesOutputData outputData);

    /**
     * Prepares the failure view for the favorites list use case.
     * @param outputData the output data
     */
    void prepareErrorView(AddToFavoritesOutputData outputData);
}
