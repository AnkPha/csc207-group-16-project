package data_access;

import java.io.IOException;
import java.util.ArrayList;

import entity.Restaurant;
import entity.RestaurantListResult;
import use_case.review.AddReviewAccessInterface;
import use_case.search_nearby_locations.SearchLocationsNearbyDataAccessInterface;

public class SearchLocationNearbyDataAccessObject implements SearchLocationsNearbyDataAccessInterface {
    private static final int FAILED_AT_CALL = 0;
    private static final int FOUND = 2;
    private static final int TIME_OUT = 4;
    private static final int NO_RESULTS = 1;
    private NominatimApi nominatimApi;
    private OverPassApi overpassApi;
    private AddReviewAccessInterface reviewDataAccessObject;
    private double[] addressCoords = {0.0, 0.0};

    public SearchLocationNearbyDataAccessObject(AddReviewAccessInterface reviewDao) {
        this.nominatimApi = new NominatimApi();
        this.overpassApi = new OverPassApi();
        this.reviewDataAccessObject = reviewDao;
    }

    @Override
    public RestaurantListResult getNearbyRestaurantsResult(String address, int radius) {
        final RestaurantListResult result = new RestaurantListResult();
        double[] coords;
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        try {
            coords = nominatimApi.geocode(address);

            if (coords == null || coords.length == FAILED_AT_CALL) {
                System.out.println("Failed to make call to API");
                result.setStatus(FAILED_AT_CALL);
            }
            if (coords.length == NO_RESULTS) {
                //  Not being able to find the coordinates
                System.out.println("Address does not exist");
                result.setStatus(NO_RESULTS);
            }
        }
        catch (IOException exception) {
            //  Most common problem is HTTP failure
            System.out.println("Failed to connect to API server");
            result.setStatus(FAILED_AT_CALL);
            coords = new double[FAILED_AT_CALL];
        }
        if (coords.length == FOUND) {
            //  Assuming the coordinates in coords is in the right order of lat and long
            result.setStatus(FOUND);
            this.addressCoords = coords;
            restaurantList = overpassApi.getNearbyRestaurants(coords[0], coords[1], radius);
            if (restaurantList != null) {
                // For each restaurant, get its rating from the review DAO and update the restaurant object
                for (Restaurant restaurant : restaurantList) {
                    final double averageRating = reviewDataAccessObject.getAverageRatingForRestaurant(restaurant);
                    if (averageRating > 0.0) {
                        restaurant.addRating(averageRating);
                    }
                }
            }
            else {
                result.setStatus(TIME_OUT);
                restaurantList = new ArrayList<>();
            }
        }
        result.setRestaurants(restaurantList);
        return result;
    }

    public double[] getAddressCoords() {
        return addressCoords;
    }

}
