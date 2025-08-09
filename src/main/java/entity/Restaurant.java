package entity;

import java.util.ArrayList;

public class Restaurant {
    private final String name;
    private final String address;
    private final String cuisine;
    private final String vegStat;
    private final String openingHours;
    private final String website;
    private String rating;
    private ArrayList<Double> allReviewScores;

    private double lon;
    private double lat;

    public Restaurant(String name,
                      String address,
                      String cuisine,
                      String vegStat,
                      String openingHours,
                      String website,
                      double[] coords) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.openingHours = openingHours;
        this.website = website;
        this.rating = "No Ratings";
        this.allReviewScores = new ArrayList<>();
        this.lon = coords[1];
        this.lat = coords[0];
        this.allReviewScores = new ArrayList<>();
    }

    /**
     * A method that is used to add a rating.
     * @param score the score the user gave.
     */
    public void addRating(double score) {
        if (allReviewScores == null) {
            allReviewScores = new ArrayList<>();
        }
        allReviewScores.add(score);
        double numerator = 0;
        for (int i = 0; i < allReviewScores.size(); i++) {
            numerator += allReviewScores.get(i);
        }
        this.rating = Double.toString(numerator / allReviewScores.size());
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRating() {
        return rating;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getVegStat() {
        return vegStat;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getWebsite() {
        return website;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %s * %.1f\nLocation: (%f, %f)\nLink: %s",
                name, address, rating);
    }

}
