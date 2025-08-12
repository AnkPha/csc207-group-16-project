package interface_adapter.filter;

import java.util.List;

import Search.Restaurant;
import Search.ViewModel;

public class FilterViewModel extends ViewModel<FilterState> {
    private FilterState state;
    private List<Restaurant> restaurants;

    public FilterViewModel() {
        super("filter");
        this.state = new FilterState();
        setState(state);
    }

    public FilterState getState() {
        return state;
    }

    /**
     * Sets the cuisines for filtering.
     * @param cuisines A List of Strings representing cuisines.
     */
    public void setCuisine(List<String> cuisines) {
        state.getCuisine().clear();
        if (cuisines != null) {
            state.getCuisine().addAll(cuisines);
        }
        firePropertyChanged();
    }

    /**
     * Sets the vegetarian status for filtering.
     * @param vegStat A String representing vegetarian status.
     */
    public void setVegStat(String vegStat) {
        state.setVegStat(vegStat);
        firePropertyChanged();
    }

    /**
     * Sets the opening hour for filtering.
     * @param openingHours A String representing opening hours.
     */
    public void setOpeningHours(String openingHours) {
        state.setOpeningHours(openingHours);
        firePropertyChanged();
    }

    /**
     * Sets the availability for filtering.
     * @param availability A String representing availability.
     */
    public void setAvailability(String availability) {
        state.setAvailability(availability);
        firePropertyChanged();
    }

    /**
     * Sets the rating for filtering.
     * @param rating A String representing rating.
     */
    public void setRating(String rating) {
        state.setRating(rating);
        firePropertyChanged();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    /**
     * Shows error message for filtering errors.
     * @param errorMessage A String that represents the error shown when there is a filtering error.
     */
    public void showError(String errorMessage) {
        System.err.println("Filter Error: " + errorMessage);
    }

    @Override
    public void setState(FilterState state) {
        this.state = state;
        firePropertyChanged("state");
    }
}
