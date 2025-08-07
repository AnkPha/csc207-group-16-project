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

    /**
     * Executes the filter options chosen by User through calls to the filterDAO method "getFilteredRestaurants".
     * @param filterInputData A FilterInputData initialization that includes filter options, address, and radius.
     */
    public void execute(FilterInputData filterInputData) {
        final ArrayList<Restaurant> filteredRestaurants =
                filterDataAccessObject.getFilteredRestaurants(filterInputData);
        System.out.println("INTERACT SIZE " + filteredRestaurants.size());
        final FilterOutputData outputData = new FilterOutputData(filteredRestaurants);
        System.out.println("ATTEMPTING TO RUN FILTER INTERACTOR");
        filterPresenter.prepareSuccessView(outputData);
    }
}
