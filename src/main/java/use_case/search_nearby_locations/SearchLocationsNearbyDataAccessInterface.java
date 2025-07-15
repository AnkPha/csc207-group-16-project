package use_case.search_nearby_locations;
import entity.Restaurant;
import java.util.List;
/**
 *
 */
public interface SearchLocationsNearbyDataAccessInterface {
    /**
     *
     */
    List<Restaurant> getNearbyRestaurants(String address);
}
