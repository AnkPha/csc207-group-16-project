package interface_adapter.search_nearby_locations;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }

    /**
     * A method that sets the current state in the GUI.
     * @param state the state given
     */
    public void setState(SearchState state) {
        super.setState(state);
        this.firePropertyChanged("state");
    }

}
