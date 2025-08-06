package use_case.filter;

import java.util.ArrayList;

import entity.Restaurant;

/**
 * Output Data for the Filter Use Case.
 */
public class FilterOutputData {
    private final ArrayList<Restaurant> filteredRestaurants;

    public FilterOutputData(ArrayList<Restaurant> filteredRestaurants) {
        this.filteredRestaurants = filteredRestaurants;
    }

    public ArrayList<Restaurant> getFilteredRestaurants() {
        return filteredRestaurants;
    }
}
