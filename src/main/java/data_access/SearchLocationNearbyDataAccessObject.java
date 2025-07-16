package data_access;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Restaurant;
import use_case.search_nearby_locations.SearchLocationsNearbyDataAccessInterface;

import java.util.ArrayList;

public class SearchLocationNearbyDataAccessObject implements SearchLocationsNearbyDataAccessInterface{
    private NominatimAPI nominatimAPI;
    private OverPassAPI overpassAPI;

    public SearchLocationNearbyDataAccessObject(){
        this.nominatimAPI = new NominatimAPI();
        this.overpassAPI = new OverPassAPI();
    }

    @Override
    public ArrayList<Restaurant> getNearbyRestaurants(String address, int radius) {
        double[] coords;
        ArrayList<Restaurant> resturantList = new ArrayList<Restaurant>();
        try{
            System.out.println("TRIED TO RUN");
            coords = nominatimAPI.geocode(address);
            System.out.println("TRIED TO RUN 2");

            if (coords.length != 2){
                //Not being able to find the coordinates
                System.out.println("Address does not exist");
            }
        }
        catch (Exception e){
            //Most common problem is HTTP failiure
            System.out.println("Failed to get coords");
            coords = new double[0];
        }
        if (coords.length == 2) {
            //Assuming the coordinates in coords is in the right order of lat and long
            System.out.println("Before Call");
            return overpassAPI.getNearbyRestaurants(coords[0], coords[1], radius);

        }

        return resturantList;
    }
}
