package use_case.filter;

import java.util.ArrayList;

import entity.Restaurant;

/**
 * The interface of the DAO for the Filter Use Case.
 */
public interface FilterDataAccessInterface {

    /**
     * Updates the system to filter using this user's preference.
     * @param filterInputData the user's filter inputs
     * @return An ArrayList of Restaurants that adhere to the SearchLocationsNearby parameters and Filter parameters
     */
    ArrayList<Restaurant> getFilteredRestaurants(FilterInputData filterInputData);
}
