package use_case.filter;

import java.util.List;

import use_case.search_nearby_locations.SearchLocationsNearbyInputData;
/**
 * The input data for the Filter Use Case.
 */

public class FilterInputData {

    private String address;
    private int radius;
    private List<String> cuisine;
    private String vegStat;
    private String availability;
    private String rating;

    public FilterInputData(String address, int radius, List<String> cuisine, String vegStat,
                           String availability, String rating) {
        this.address = address;
        this.radius = radius;
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.availability = availability;
        this.rating = rating;
    }

    public List<String> getCuisine() {

        return cuisine;
    }

    public String getVegStat() {

        return vegStat;
    }

    public String getAvailability() {

        return availability;
    }

    public String getRating() {
        return rating;
    }

    public int getRadius() { return radius; }

    public String getAddress(){ return address; }
}
