package interface_adapter.search_nearby_locations;
import use_case.search_nearby_locations.SearchLocationsNearbyInputBoundary;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

public class SearchLocationsNearbyController {
        private final SearchLocationsNearbyInputBoundary interactor;

        public SearchLocationsNearbyController(SearchLocationsNearbyInputBoundary interactor){
            this.interactor = interactor;
        }

        void execute(String address, int limit, int radius){
            final SearchLocationsNearbyInputData input = new SearchLocationsNearbyInputData(address, limit, radius);
            interactor.execute(input);
        }
    }
