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
        final SearchState newState = new SearchState();
        newState.setRestaurants(outputData.getNearbyRestaurants());
        newState.setAddressCoords(outputData.getAddressCoords());
        newState.setStatus(outputData.getStatus());
        resultsViewModel.setState(newState);
    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
