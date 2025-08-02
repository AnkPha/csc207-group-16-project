package interface_adapter.filter;

import entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Filter View Model.
 */
public class FilterState {
    private final List<String> cuisine = new ArrayList<>();
    private String vegStat = "";
    private String openingHours = "";
    private String rating = "";
    private ArrayList<Restaurant> restaurants;

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

    public void setVegStat(String vegStat) {
        this.vegStat = vegStat;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<Restaurant> getRestaurants(){
        return this.restaurants;
    }

    public void setResturants(ArrayList<Restaurant> restaurants){
        this.restaurants = restaurants;
    }


}
