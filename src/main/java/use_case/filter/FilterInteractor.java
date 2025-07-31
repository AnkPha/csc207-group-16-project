package use_case.filter;

import entity.Restaurant;
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
    public void execute(FilterInputData filterInputData) {
        ArrayList<Restaurant> filteredRestaurants = filterDataAccessObject.getFilteredRestaurants(filterInputData);
        FilterOutputData outputData = new FilterOutputData(filteredRestaurants);
        filterPresenter.prepareSuccessView(outputData);
    }
}
