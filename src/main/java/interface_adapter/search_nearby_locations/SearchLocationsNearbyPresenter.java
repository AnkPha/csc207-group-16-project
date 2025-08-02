package interface_adapter.search_nearby_locations;
import use_case.search_nearby_locations.SearchLocationsNearbyOutputBoundary;
import use_case.search_nearby_locations.SearchLocationsNearbyOutputData;
/**
 * The Presenter for the Search nearby locations Use Case.
 */
public class SearchLocationsNearbyPresenter implements SearchLocationsNearbyOutputBoundary {

    private final SearchViewModel resultsViewModel;

    public SearchLocationsNearbyPresenter(SearchViewModel resultsViewModel) {
        this.resultsViewModel = resultsViewModel;
    }

    @Override
    public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
        SearchState newState = new SearchState();
        newState.setRestaurants(outputData.getNearbyRestaurants()); // Assuming this method exists
        newState.setAddressCoords(outputData.getAddressCoords());          // Optional
//        newState.setRadius(outputData.getRadius());            // Optional
        System.out.println("ABOUT TO CALL SET STATE");
        resultsViewModel.setState(newState); // This fires the "state" property change
    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }


}
