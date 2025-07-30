package use_case.search_nearby_locations;
import entity.Restaurant;
import java.util.ArrayList;
/**
 *
 */
public interface SearchLocationsNearbyDataAccessInterface {
    /**
     *
     */
    ArrayList<Restaurant> getNearbyRestaurants(String address, int radius);
}
