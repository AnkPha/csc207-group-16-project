package Search.search_nearby_locations;

import Search.RestaurantListResult;

/**
 * An interface which represents the DAO for searching.
 */
public interface SearchLocationsNearbyDataAccessInterface {
    /**
     * A method that gets the restaurants nearby.
     * @param address the address you are looking around
     * @param radius the radius you are looking around
     * @return RestaurantListResult an object that contains a list of
     *      restaurants and whether the search was sucessful.
     */
    RestaurantListResult getNearbyRestaurantsResult(String address, int radius);

    /**
     * A method that gets the address's coordinates.
     * @return double[] The coordinates in the form of a double array size 2.
     */
    double[] getAddressCoords();
}
