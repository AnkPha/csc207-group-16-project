package use_case.filter;

import entity.Restaurant;

import java.util.ArrayList;

/**
 * Output Data for the Filter Use Case.
 */
public class FilterOutputData {
    private ArrayList<Restaurant> filteredRestaurants;

    public FilterOutputData(ArrayList<Restaurant> filteredRestaurants) {
        this.filteredRestaurants = filteredRestaurants;
    }

    public ArrayList<Restaurant> getFilteredRestaurants() {
        return filteredRestaurants;
    }
}
