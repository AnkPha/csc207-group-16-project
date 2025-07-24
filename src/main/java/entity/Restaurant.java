package entity;

public class Restaurant {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String address;
    private final String price;
    private final double rating;
    private final String yelpUrl;

    public Restaurant(String name, double latitude, double longitude,
                          String address, String price, double rating, String yelpUrl) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.price = price;
        this.rating = rating;
        this.yelpUrl = yelpUrl;
    }

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getAddress() { return address; }
    public String getPrice() { return price; }
    public double getRating() { return rating; }
    public String getYelpUrl() { return yelpUrl; }

    @Override
    public String toString() {
        return String.format("%s (%s): %s ⭐️ %.1f\nLocation: (%f, %f)\nLink: %s",
                name, price, address, rating, latitude, longitude, yelpUrl);
    }

}
