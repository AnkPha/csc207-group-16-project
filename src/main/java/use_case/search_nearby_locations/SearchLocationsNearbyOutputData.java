package use_case.search_nearby_locations;
import entity.Restaurant;

import java.util.ArrayList;
import java.util.List;
public class SearchLocationsNearbyOutputData{
    private final ArrayList<Restaurant> nearbyRestaurants;
    private final double[] addressCoords;
    public SearchLocationsNearbyOutputData(ArrayList<Restaurant> nearbyRestaurants, double[] addressCoords){
        this.nearbyRestaurants = nearbyRestaurants;
        this.addressCoords = addressCoords;
    }
    public ArrayList<Restaurant> getNearbyRestaurants() {
        return nearbyRestaurants;
    }

    public double[] getAddressCoords() { return addressCoords; }
}