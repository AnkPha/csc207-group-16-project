package Search.search_nearby_locations;

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
