package interface_adapter.filter;

import java.util.List;

import interface_adapter.ViewModel;

/**
 * The View Model for the Filter View.
 */
public class FilterViewModel extends ViewModel<FilterState> {
    private FilterState state;

    public FilterViewModel() {
        super("filter");
        this.state = new FilterState();
        setState(state);
    }

    public FilterState getState() {
        return state;
    }

    public void setCuisine(List<String> cuisines) {
        state.getCuisine().clear();
        if (cuisines != null) {
            state.getCuisine().addAll(cuisines);
        }
        firePropertyChanged();
    }

    public void setVegStat(String vegStat) {
        state.setVegStat(vegStat);
        firePropertyChanged();
    }

    public void setOpeningHours(String openingHours) {
        state.setOpeningHours(openingHours);
        firePropertyChanged();
    }

    public void setRating(String rating) {
        state.setRating(rating);
        firePropertyChanged();
    }

    public void setRestaurants(java.util.List<?> restaurants) {
        firePropertyChanged();
    }

    public void showError(String errorMessage) {
        System.err.println("Filter Error: " + errorMessage);
    }
}
