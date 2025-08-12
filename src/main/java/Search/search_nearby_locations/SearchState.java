package Search.search_nearby_locations;

import java.util.ArrayList;

import Search.Restaurant;

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
    private int status;

    public SearchState(SearchState copy) {
        address = copy.address;
        searchError = copy.searchError;
        radius = copy.radius;
        addressCoords = copy.getAddressCoords();
        foundAddress = copy.getFoundAddress();
        filtered = false;
        status = copy.status;
        restaurants = copy.restaurants;
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

    public String getRadius() {
        return radius;
    }

    /**
     * Sets the radius.
     * @param radius the radius
     */
    public void setRadius(String radius) {
        this.radius = radius;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setAddressCoords(double[] addressCoords) {
        this.addressCoords = addressCoords;
    }

    public double[] getAddressCoords() {
        return addressCoords;
    }

    public void setFoundAddress(boolean foundAddress) {
        this.foundAddress = foundAddress;
    }

    public boolean getFoundAddress() {
        return foundAddress;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public boolean getFiltered() {
        return filtered;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

