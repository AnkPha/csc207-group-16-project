package entity;

import java.util.ArrayList;
import java.util.Objects;

public class Restaurant {
    private final String noRatings = "No Ratings";
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
        this.rating = noRatings;
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
        updateRating();
    }

    /**
     * Recalculates the average rating from all review scores.
     */
    private void updateRating() {
        if (allReviewScores.isEmpty()) {
            this.rating = noRatings;
        }
        else {
            double sum = 0;
            for (double score : allReviewScores) {
                sum += score;
            }
            final double average = sum / allReviewScores.size();
            this.rating = String.format("%.1f", average);
        }
    }

    /**
     * Gets the rating as a numeric value for filtering purposes.
     * @return the numeric rating, or 0.0 if no ratings exist
     */
    public double getNumericRating() {
        double result = 0.0;

        if (!noRatings.equals(rating)) {
            try {
                result = Double.parseDouble(rating);
            }
            catch (NumberFormatException numberFormatException) {
                result = 0.0;
            }
        }

        return result;
    }

    /**
     * Checks if this restaurant has any ratings.
     * @return true if ratings exist, false otherwise
     */
    public boolean hasRatings() {
        return !allReviewScores.isEmpty();
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

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (this == obj) {
            isEqual = true;
        }
        else if (obj != null && getClass() == obj.getClass()) {
            final Restaurant restaurant = (Restaurant) obj;
            isEqual = Objects.equals(name, restaurant.name)
                    && Objects.equals(address, restaurant.address);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

}
