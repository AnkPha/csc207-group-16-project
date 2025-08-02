package use_case.filter;

import java.util.ArrayList;
import java.util.List;

import entity.Restaurant;

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

    //address, Integer.parseInt(radiusText), selectedCuisines, selectedVegStat, selectedHour, selectedRating@Override
    //String address, int radius, List<String> selectedCuisines, String selectedVegStat, String selectedHour, String selectedRating
    public void execute(FilterInputData filterInputData) {
        final ArrayList<Restaurant> filteredRestaurants =
                filterDataAccessObject.getFilteredRestaurants(filterInputData);
        System.out.println("INTERACOR SIZE " + filteredRestaurants.size());
        final FilterOutputData outputData = new FilterOutputData(filteredRestaurants);
        System.out.println("ATTEMPTING TO RUN FILTER INTERACTOR");
        filterPresenter.prepareSuccessView(outputData);
    }
}
