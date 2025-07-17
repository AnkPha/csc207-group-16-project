package use_case.filter;

import entity.Restaurant;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

import java.util.ArrayList;

/**
 * The interface of the DAO for the Filter Use Case.
 */
public interface FilterDataAccessInterface {

    /**
     * Updates the system to filter using this user's preference.
     * @param inputData the user's address and radius
     * @param cuisine the user's preferred cuisine
     * @param vegStat the user's vegetarian status
     * @param openingHours the user's preferred openingHours
     * @param rating the user's preferred rating
     */
    ArrayList<Restaurant> getFilteredRestaurants(SearchLocationsNearbyInputData inputData, String cuisine,
                                                 String vegStat, String openingHours, String rating);
}