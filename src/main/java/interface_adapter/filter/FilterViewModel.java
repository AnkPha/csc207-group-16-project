package interface_adapter.filter;

import java.util.List;

import entity.Restaurant;
import interface_adapter.ViewModel;

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

    public void setAvailability(String availability) {
        state.setAvailability(availability);
        firePropertyChanged();
    }

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

    public void showError(String errorMessage) {
        System.err.println("Filter Error: " + errorMessage);
    }

    @Override
    public void setState(FilterState state) {
        this.state = state;
        firePropertyChanged("state");
    }
}
