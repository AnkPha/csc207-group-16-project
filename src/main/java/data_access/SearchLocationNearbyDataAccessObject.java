package data_access;

import java.io.IOException;
import java.util.ArrayList;

import entity.Restaurant;
import entity.RestaurantListResult;
import use_case.search_nearby_locations.SearchLocationsNearbyDataAccessInterface;

public class SearchLocationNearbyDataAccessObject implements SearchLocationsNearbyDataAccessInterface {
    private static final int FAILED_AT_CALL = 0;
    private static final int FOUND = 2;
    private static final int NO_RESULTS = 1;
    private NominatimApi nominatimApi;
    private OverPassApi overpassApi;
    private double[] addressCoords = {0.0, 0.0};

    public SearchLocationNearbyDataAccessObject() {
        this.nominatimApi = new NominatimApi();
        this.overpassApi = new OverPassApi();
    }

    @Override
    public RestaurantListResult getNearbyRestaurantsResult(String address, int radius) {
        final RestaurantListResult result = new RestaurantListResult();
        double[] coords;
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        try {
            System.out.println("TRIED TO RUN");
            coords = nominatimApi.geocode(address);
            System.out.println("TRIED TO RUN 2");

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
            System.out.println("Before Call " + result.getStatus());
            this.addressCoords = coords;
            restaurantList = overpassApi.getNearbyRestaurants(coords[0], coords[1], radius);
            System.out.println("SIZE IS "
                    + restaurantList.size()
                    + " AND RADIUS IS " + radius + " ADDRESS IS " + address);
        }
        result.setRestaurants(restaurantList);
        System.out.println("END OF DAO " + result.getStatus());
        return result;
    }

    public double[] getAddressCoords() {
        return addressCoords;
    }

}
