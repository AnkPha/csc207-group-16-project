package interface_adapter.search_nearby_locations;


import interface_adapter.ViewModel;
import interface_adapter.search_nearby_locations.SearchState;
import entity.Restaurant;

import java.util.ArrayList;

/**
 * The View Model for the Logged In View.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    private ArrayList<Restaurant> restaurants;

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<Restaurant> getRestaurants(){
        return this.restaurants;
    }

    public void setState(SearchState state) {
        super.setState(state);
        System.out.println("SET STATE RIGHT NOW");
        this.firePropertyChanged("state");
    }

}
