package use_case.search_nearby_locations;
import entity.Restaurant;
import java.util.List;
public class SearchLocationsNearbyOutputData{
    private final List<Restaurant> nearbyRestaurants;
    public SearchLocationsNearbyOutputData(List<Restaurant> nearbyRestaurants){
        this.nearbyRestaurants = nearbyRestaurants;
    }
    public List<Restaurant> getNearbyRestaurants() {
        return nearbyRestaurants;
    }
}