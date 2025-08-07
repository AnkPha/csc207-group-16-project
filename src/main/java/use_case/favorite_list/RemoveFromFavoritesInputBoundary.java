package use_case.favorite_list;

public interface RemoveFromFavoritesInputBoundary {
    /**
     * A method that executes the remove from favorites use case given the input data.
     * @param inputData the input data given
     */
    void execute(RemoveFromFavoritesInputData inputData);
}

