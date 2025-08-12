package Search.search_nearby_locations;

/**
 * An interface that acts as the output boundary.
 */
public interface SearchLocationsNearbyOutputBoundary {
    /**
     * A method that prepares the success view.
     * @param outputData the output data that needs to be displayed
     */
    void prepareSuccessView(SearchLocationsNearbyOutputData outputData);

    /**
     * A method that prepares the fail view.
     * @param errorMessage the error message that is displayed
     */
    void prepareFailView(String errorMessage);

}
