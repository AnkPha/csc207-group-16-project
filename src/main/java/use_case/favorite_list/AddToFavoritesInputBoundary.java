package use_case.favorite_list;

public interface AddToFavoritesInputBoundary {
    /**
     * A method that executes the use case given the input data.
     * @param inputData the input data given
     */
    void execute(AddToFavoritesInputData inputData);
}
