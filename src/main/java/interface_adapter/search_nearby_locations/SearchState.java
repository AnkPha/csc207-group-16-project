package interface_adapter.search_nearby_locations;

import entity.Restaurant;

import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * The State information representing the current address.
 */
public class SearchState {
    private String address = "";
    private String searchError;
    private String radius;
    private ArrayList<Restaurant> restaurants;

    public SearchState(interface_adapter.search_nearby_locations.SearchState copy) {
        address = copy.address;
        searchError = copy.searchError;
        radius = copy.radius;

    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SearchState() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRadius() { return radius; }

    public void setRadius(String radius) { this.radius = radius; }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) { this.restaurants = restaurants; }

    public ArrayList<Restaurant> getResturants(){
        return restaurants;
    }
}

