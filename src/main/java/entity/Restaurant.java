package entity;

import java.util.List;
import java.util.Objects;

public class Restaurant {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String address;
    private final String price;
    private final double rating;
    private final String yelpUrl;
    private final List<Review> reviews;

    public Restaurant(String name, double latitude, double longitude,
                      String address, String price, double rating, String yelpUrl, List<Review> reviews) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.price = price;
        this.rating = rating;
        this.yelpUrl = yelpUrl;
        this.reviews = reviews;
    }

    public String getName() {
        return name; }
    public double getLatitude() {
        return latitude; }
    public double getLongitude() {
        return longitude; }
    public String getAddress() {
        return address; }
    public String getPrice() {
        return price; }
    public double getRating() {
        return rating; }
    public String getYelpUrl() {
        return yelpUrl; }
    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %s ⭐️ %.1f\nLocation: (%f, %f)\nLink: %s",
                name, price, address, rating, latitude, longitude, yelpUrl);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof Restaurant)) {
            return false;
        }

        final Restaurant restaurant = (Restaurant) obj;
        return restaurant.getName().equals(this.name)
                && restaurant.getAddress().equals(this.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

}
