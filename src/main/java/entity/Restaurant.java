package entity;

public class Restaurant {
    private final String name;
    private final String address;
    private final String price;
    private final double rating;
    private double lon;
    private double lat;

    public Restaurant(String name,String address,String cuisine, String vegStat, String openingHours, String website, double lat, double lon) {

        this.name = name;
        this.address = address;
        this.price = price;
        this.rating = rating;
        this.cuisine = cuisine;
        this.vegStat = vegStat;
        this.openingHours = openingHours;
        this.website = website;
        this.rating = "No Ratings";
        this.lon = lon;
        this.lat = lat;
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
    public double getRating() { return rating; }
    public String getRating() { return rating; }
    public String getCuisine() { return cuisine; }
    public String getVegStat() { return vegStat; }
    public String getOpeningHours() { return openingHours; }
    public String getWebsite() { return website; }
    public double getLon() { return lon; }
    public double getLat() { return lat; }


    @Override
    public String toString() {
        return String.format("%s (%s): %s ⭐️ %.1f\nLocation: (%f, %f)\nLink: %s",
                name, price, address, rating, latitude, longitude, yelpUrl);
    }

}
