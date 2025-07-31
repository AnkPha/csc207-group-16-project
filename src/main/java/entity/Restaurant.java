package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurant {
    private final String name;
    private final String address;
    private final String cuisine;
    private final String vegStat;
    private final String openingHours;
    private final String website;
    private double rating;

    public Restaurant(String name, String address, double rating, String cuisine, String vegStat,
                      String openingHours, String website) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.openingHours = openingHours;
        this.website = website;
        this.rating = rating;
    }

    public void addRating(double rating) {
        this.rating = rating;
    }


    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getRating() { return rating; }
    public String getCuisine() { return cuisine; }
    public String getVegStat() { return vegStat; }
    public String getOpeningHours() { return openingHours; }
    public String getWebsite() { return website; }

    @Override
    public String toString() {
        return String.format("%s (%s): %s ⭐️ %.1f\nLocation: (%f, %f)\nLink: %s",
                name, address, rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant restaurant = (Restaurant) o;
        return Objects.equals(name, restaurant.name) &&
                Objects.equals(address, restaurant.address);
    }
}
