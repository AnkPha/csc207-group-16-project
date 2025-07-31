package interface_adapter.filter;

import interface_adapter.ViewModel;

/**
 * The View Model for the Filter View.
 */
public class FilterViewModel extends ViewModel<FilterState> {
    private FilterState state;

    public FilterViewModel() {
        super("filter");
        setState(new FilterState());
    }
}
