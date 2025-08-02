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
    private double[] addressCoords;
    private boolean foundAddress;
    private boolean filtered;

    public SearchState(interface_adapter.search_nearby_locations.SearchState copy) {
        address = copy.address;
        searchError = copy.searchError;
        radius = copy.radius;
        addressCoords = copy.getAddressCoords();
        foundAddress = copy.getFoundAddress();
        filtered = false;
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

    public void setAddressCoords(double[] addressCoords) { this.addressCoords = addressCoords; }

    public double[] getAddressCoords() { return addressCoords; }

    public void setFoundAddress(boolean foundAddress) { this.foundAddress = foundAddress; }
    public boolean getFoundAddress(){ return foundAddress; }

    public void setFiltered(boolean filtered) { this.filtered = filtered; }

    public boolean getFiltered() { return filtered; }
}

