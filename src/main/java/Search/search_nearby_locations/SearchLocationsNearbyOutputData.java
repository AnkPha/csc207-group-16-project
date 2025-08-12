package Search.search_nearby_locations;

import java.util.ArrayList;

import Search.Restaurant;

public class SearchLocationsNearbyOutputData {
    private final ArrayList<Restaurant> nearbyRestaurants;
    private final double[] addressCoords;
    private final int status;

    public SearchLocationsNearbyOutputData(ArrayList<Restaurant> nearbyRestaurants,
                                           double[] addressCoords,
                                           int status) {
        this.nearbyRestaurants = nearbyRestaurants;
        this.addressCoords = addressCoords;
        this.status = status;
    }

    public ArrayList<Restaurant> getNearbyRestaurants() {
        return nearbyRestaurants;
    }

    public double[] getAddressCoords() {
        return addressCoords;
    }

    public int getStatus() {
        return status;
    }
}
