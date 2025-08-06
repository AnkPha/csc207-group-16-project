package interface_adapter.filter;

import entity.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class FilterState {
    private final List<String> cuisine = new ArrayList<>();
    private String vegStat = "";
    private String openingHours = "";
    private String availability = "";
    private String rating = "";
    private ArrayList<Restaurant> restaurants;

    public List<String> getCuisine() {
        return cuisine;
    }

    public String getVegStat() {
        return vegStat;
    }

    public void setVegStat(String vegStat) {
        this.vegStat = vegStat;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setResturants(ArrayList<Restaurant> restos) {
        this.restaurants = restos;
    }
}
