package use_case.filter;

import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

import java.util.List;

/**
 * The input data for the Filter Use Case.
 */
public class FilterInputData {

    private SearchLocationsNearbyInputData locations;
    private List<String> cuisine;
    private String vegStat;
    private String openingHours;
    private String rating;

    public FilterInputData(SearchLocationsNearbyInputData locations, List<String> cuisine, String vegStat,
                           String openingHours, String rating) {
        this.locations = locations;
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.openingHours = openingHours;
        this.rating = rating;
    }

    public SearchLocationsNearbyInputData getLocations() {
        return locations;
    }

    public List<String> getCuisine() {

        return cuisine;
    }

    public String getVegStat() {

        return vegStat;
    }

    public String getOpeningHours() {

        return openingHours;
    }

    public String getRating() {
        return rating;
    }
}