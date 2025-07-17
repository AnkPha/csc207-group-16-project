package use_case.filter;

import entity.Restaurant;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;
import java.util.ArrayList;

/**
 * The Filter Interactor.
 */
public class FilterInteractor implements FilterInputBoundary {
    private final FilterDataAccessInterface filterDataAccessObject;
    private final FilterOutputBoundary filterPresenter;

    public FilterInteractor(FilterDataAccessInterface filterDataAccessInterface,
                            FilterOutputBoundary filterOutputBoundary) {
        this.filterDataAccessObject = filterDataAccessInterface;
        this.filterPresenter = filterOutputBoundary;
    }

    @Override
    public void execute(SearchLocationsNearbyInputData searchLocationsNearbyInputData, FilterInputData filterInputData) {
        // How do we include the address and radius from SearchLocationsNearbyInputData?;
        String cuisine = filterInputData.getCuisine();
        String vegStat = filterInputData.getVegStat();
        String openingHours = filterInputData.getOpeningHours();
        String rating = filterInputData.getRating();

        ArrayList<Restaurant> filteredRestaurants = filterDataAccessObject.getFilteredRestaurants(searchLocationsNearbyInputData,
                cuisine, vegStat, openingHours, rating);
        FilterOutputData outputData = new FilterOutputData(filteredRestaurants);
        filterPresenter.prepareSuccessView(outputData);
    }
}
