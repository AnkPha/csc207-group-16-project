package interface_adapter.filter;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

/**
 * Controller for the Filter Use Case.
 */
public class FilterController {
    private FilterInputBoundary userFilterUseCaseInteractor;

    public FilterController(FilterInputBoundary userFilterUseCaseInteractor) {
        this.userFilterUseCaseInteractor = userFilterUseCaseInteractor;
    }

    /**
     * Executes the Filter Use Case.
     * @param locations the address and radius of user input.
     * @param cuisine the cuisine option to filter.
     * @param vegStat the vegetarian status option to filter.
     * @param hours the hours of operation option to filter.
     * @param rating the rate option to filter.
     */
    public void execute(SearchLocationsNearbyInputData locations, String cuisine, String vegStat, String hours,
                        String rating) {
        final FilterInputData userFilterInputData = new FilterInputData(cuisine, hours, vegStat, rating);

        userFilterUseCaseInteractor.execute(locations, userFilterInputData);
    }
}
