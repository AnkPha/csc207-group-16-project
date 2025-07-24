package use_case.filter;

import entity.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The input data for the Filter Use Case.
 */
public class FilterInputData {

    private List<Restaurant> restaurantsList;
    private String cuisine;
    private String vegStat;
    private String openingHours;
    private String rating;

    public FilterInputData(String cuisine, String vegStat, String openingHours,
                           String rating) {
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.openingHours = openingHours;
        this.rating = rating;
    }

    String getCuisine() {
        return cuisine;
    }

    String getVegStat() {
        return vegStat;
    }

    String getOpeningHours() {
        return openingHours;
    }

    String getRating() {
        return rating; }

}
