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

    private FilterViewModel filterViewModelS;

    public FilterPresenter(FilterViewModel filterViewModel) {
        this.filterViewModelS = filterViewModel;
    }

    @Override
    public void prepareSuccessView(FilterOutputData outputData) {
        // currently there isn't anything to change based on the output data,
        // since the output data only contains the username, which remains the same.
        // We still fire the property changed event, but just to let the view know that
        // it can alert the user that their password was changed successfully.
    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
