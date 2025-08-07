package use_case.favorite_list;

public interface RemoveFromFavoritesOutputBoundary {
    /**
     * Prepares the success view for the remove from favorites use case.
     * @param outputData the output data
     */
    void prepareSuccessView(RemoveFromFavoritesOutputData outputData);

    /**
     * Prepares the failure view for the remove from favorites use case.
     * @param outputData the output data
     */
    void prepareErrorView(RemoveFromFavoritesOutputData outputData);
}
