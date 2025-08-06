package interface_adapter.filter;

import java.util.ArrayList;
import java.util.List;

import entity.Restaurant;
import interface_adapter.ViewModel;

/**
 * The View Model for the Filter View.
 */
public class FilterViewModel extends ViewModel<FilterState> {
    private FilterState state;
    private ArrayList<Restaurant> restaurants;
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
//        firePropertyChanged();
    }

    public void setOpeningHours(String openingHours) {
        state.setOpeningHours(openingHours);
//        firePropertyChanged();
    }

    public void setRating(String rating) {
        state.setRating(rating);
//        firePropertyChanged();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = (ArrayList<Restaurant>) restaurants;
    }

    public ArrayList<Restaurant> getRestaurants(){
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
