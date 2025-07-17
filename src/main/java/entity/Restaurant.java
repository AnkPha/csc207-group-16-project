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

    public Restaurant(String name,String address,String cuisine, String vegStat, String openingHours, String website) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.openingHours = openingHours;
        this.website = website;
        this.rating = "No Ratings";
    }
    public void addRating(double score){
        allReviewScores.add(score);
        double numerator = 0;
        for(int i = 0; i < allReviewScores.size(); i++){
            numerator+= allReviewScores.get(i);
        }
        this.rating = Double.toString(numerator/allReviewScores.size());
    }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getRating() { return rating; }
    public String getCuisine() { return cuisine; }
    public String getVegStat() { return vegStat; }
    public String getOpeningHours() { return openingHours; }
    public String getWebsite() { return website; }

    @Override
    public String toString() {
        return String.format("%s (%s): %s ⭐️ %.1f\nLocation: (%f, %f)\nLink: %s",
                name, address, rating);
    }

}
