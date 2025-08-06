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
    public void execute(String address, int radius, List<String> selectedCuisines, String selectedVegStat, String selectedHour, String selectedRating) {
//        final SearchLocationsNearbyInputData locations = filterInputData.getLocations();
//        final List<String> cuisine = filterInputData.getCuisine();
//        final String vegStat = filterInputData.getVegStat();
//        final String availability = filterInputData.getAvailability();
//        final String rating = filterInputData.getRating();
//        System.out.println("FILTER ADDRESS " + address + " RADIUS "+ radius);
        final FilterInputData userFilterInputData = new FilterInputData(address, radius, selectedCuisines, selectedVegStat,
                selectedHour, selectedRating);
        userFilterUseCaseInteractor.execute(userFilterInputData);
    }
}
