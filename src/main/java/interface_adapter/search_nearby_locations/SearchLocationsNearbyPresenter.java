package interface_adapter.search_nearby_locations;
import use_case.search_nearby_locations.SearchLocationsNearbyOutputBoundary;
import use_case.search_nearby_locations.SearchLocationsNearbyOutputData;
/**
 * The Presenter for the Search nearby locations Use Case.
 */
public class SearchLocationsNearbyPresenter implements SearchLocationsNearbyOutputBoundary {

    private final SearchResultsViewModel resultsViewModel;

    public SearchLocationsNearbyPresenter(SearchResultsViewModel resultsViewModel) {
        this.resultsViewModel = resultsViewModel;
    }

    @Override
    public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
        // currently there isn't anything to change based on the output data,
        // since the output data only contains the username, which remains the same.
        // We still fire the property changed event, but just to let the view know that
        // it can alert the user that their password was changed successfully..
        // resultsViewModel.firePropertyChanged("password");

    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
