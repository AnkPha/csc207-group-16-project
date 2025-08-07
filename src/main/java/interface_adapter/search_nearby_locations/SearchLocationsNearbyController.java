package interface_adapter.search_nearby_locations;

import use_case.search_nearby_locations.SearchLocationsNearbyInputBoundary;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

public class SearchLocationsNearbyController {
    private final SearchLocationsNearbyInputBoundary interactor;

    public SearchLocationsNearbyController(SearchLocationsNearbyInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * A method that executes the inputs.
     * @param address the address
     * @param radius the radius
     */
    public void execute(String address, int radius) {
        final SearchLocationsNearbyInputData input = new SearchLocationsNearbyInputData(address, radius);
        interactor.execute(input);
    }
}
