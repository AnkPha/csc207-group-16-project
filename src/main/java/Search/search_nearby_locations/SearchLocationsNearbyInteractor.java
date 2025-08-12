package Search.search_nearby_locations;

import Search.RestaurantListResult;

/**
 * A class that represents as the search use case Interactor.
 */
public class SearchLocationsNearbyInteractor implements SearchLocationsNearbyInputBoundary {
    private final SearchLocationsNearbyDataAccessInterface locationsDataAccessObject;
    private final SearchLocationsNearbyOutputBoundary locationsPresenter;

    public SearchLocationsNearbyInteractor(SearchLocationsNearbyDataAccessInterface locationsDataAccessInterface,
                                           SearchLocationsNearbyOutputBoundary locationsOutputBoundary) {
        this.locationsDataAccessObject = locationsDataAccessInterface;
        this.locationsPresenter = locationsOutputBoundary;
    }

    /**
     * A method that executes the input data.
     * @param locationInputData the input data
     */
    public void execute(SearchLocationsNearbyInputData locationInputData) {
        // Getting the address from the parameter input

        // Call the restaurant api through the locationsDataAccessObject
        final RestaurantListResult result = locationsDataAccessObject.getNearbyRestaurantsResult(
                locationInputData.getAddress(),
                locationInputData.getRadius());
        final double[] addressCoords = locationsDataAccessObject.getAddressCoords();
        // Return the locations towards the output class
        final SearchLocationsNearbyOutputData output = new SearchLocationsNearbyOutputData(
                result.getRestaurant(),
                addressCoords, result.getStatus());
        locationsPresenter.prepareSuccessView(output);
    }

}
