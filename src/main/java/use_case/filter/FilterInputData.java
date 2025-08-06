package use_case.filter;

import java.util.List;

public class FilterInputData {

    private final String address;
    private final int radius;
    private final List<String> cuisine;
    private final String vegStat;
    private final String availability;
    private final String rating;

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

    public int getRadius() {
        return radius;
    }

    public String getAddress() {
        return address;
    }
}
