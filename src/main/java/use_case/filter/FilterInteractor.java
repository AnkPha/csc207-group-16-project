package use_case.filter;

import java.util.ArrayList;

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

    @Override
    public void execute(FilterInputData filterInputData) {
        final ArrayList<Restaurant> filteredRestaurants =
                filterDataAccessObject.getFilteredRestaurants(filterInputData);
        final FilterOutputData outputData = new FilterOutputData(filteredRestaurants);
        filterPresenter.prepareSuccessView(outputData);
    }
}
