package interface_adapter.filter;

import java.util.List;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

/**
 * Controller for the Filter Use Case.
 */
public class FilterController {
    private final FilterInputBoundary userFilterUseCaseInteractor;

    public FilterController(FilterInputBoundary userFilterUseCaseInteractor) {
        this.userFilterUseCaseInteractor = userFilterUseCaseInteractor;
    }

    /**
     * Executes the Filter Use Case.
     * @param filterInputData  the input data for filtering.
     */
    public void execute(FilterInputData filterInputData) {
        final SearchLocationsNearbyInputData locations = filterInputData.getLocations();
        final List<String> cuisine = filterInputData.getCuisine();
        final String vegStat = filterInputData.getVegStat();
        final String availability = filterInputData.getAvailability();
        final String rating = filterInputData.getRating();

        final FilterInputData userFilterInputData = new FilterInputData(locations, cuisine, vegStat,
                availability, rating);
        userFilterUseCaseInteractor.execute(userFilterInputData);
    }
}
