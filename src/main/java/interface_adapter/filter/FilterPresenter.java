package interface_adapter.filter;

import interface_adapter.filter.FilterState;
import interface_adapter.filter.FilterViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;
import use_case.filter.FilterOutputBoundary;

/**
 * The Presenter for the Filter Use Case. ALSO NOT DONE YET
 */
public class FilterPresenter implements FilterOutputBoundary {

    private final FilterViewModel viewModel;

    public FilterPresenter(FilterViewModel viewModel) {
        this.viewModel = viewModel;
    }
//
//    @Override
//    public void prepareSuccessView(FilterOutputData outputData) {
//        viewModel.setRestaurants(outputData.getFilteredRestaurants());
//        viewModel.updateView();
//    }
//
//    @Override
//    public void prepareFailView(String errorMessage) {
//        viewModel.showError(errorMessage);
//    }
}
