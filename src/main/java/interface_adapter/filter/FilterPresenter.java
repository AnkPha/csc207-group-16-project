package interface_adapter.filter;

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
        final FilterState newState = new FilterState();
        newState.setRestaurants(outputData.getFilteredRestaurants());
        viewModel.setState(newState);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.showError(errorMessage);
    }
}
