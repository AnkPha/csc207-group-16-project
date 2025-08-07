package use_case.search_nearby_locations;

/**
 * An interface that is the input boundary for the input of searching.
 */
public interface SearchLocationsNearbyInputBoundary {
    /**
     * A method that executes the input data.
     * @param searchData the input data given.
     */
    void execute(SearchLocationsNearbyInputData searchData);
}
