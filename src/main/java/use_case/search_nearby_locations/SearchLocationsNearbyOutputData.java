package use_case.search_nearby_locations;
import entity.Restaurant;

import java.util.ArrayList;
import java.util.List;
public class SearchLocationsNearbyOutputData{
    private final ArrayList<Restaurant> nearbyRestaurants;
    public SearchLocationsNearbyOutputData(ArrayList<Restaurant> nearbyRestaurants){
        this.nearbyRestaurants = nearbyRestaurants;
    }
    public ArrayList<Restaurant> getNearbyRestaurants() {
        return nearbyRestaurants;
    }
}