package entity;

import java.util.ArrayList;

public class RestaurantListResult {
    private final int FAILED_AT_CALL = 0;
    private final int FOUND = 2;
    private final int NO_RESULTS = 1;
    private int status;
    private ArrayList<Restaurant> restaurants;
    public void setStatus(int status){
        if (status == FAILED_AT_CALL){
            this.status = FAILED_AT_CALL;
        }
        else if (status == NO_RESULTS) {
            this.status = NO_RESULTS;
        }
        else {
            this.status = FOUND;
        }
    }

    public int getStatus(){
        return status;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants){
        this.restaurants = restaurants;
    }

    public ArrayList<Restaurant> getRestaurant(){
        return restaurants;
    }
}
