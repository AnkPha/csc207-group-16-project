package interface_adapter.filter;

import interface_adapter.search_nearby_locations.SearchState;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

/**
 * The Presenter for the Filter Use Case.
 */
public class FilterPresenter implements FilterOutputBoundary {

    private final FilterViewModel viewModel;

    public FilterPresenter(FilterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FilterOutputData outputData) {
        FilterState newState = new FilterState();
        System.out.println("PRESENTER SIZE " + outputData.getFilteredRestaurants().size());
        newState.setResturants(outputData.getFilteredRestaurants()); // Assuming this method exists
        viewModel.setState(newState);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.showError(errorMessage);
    }
}
