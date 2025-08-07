package entity;

import java.util.ArrayList;

public class RestaurantListResult {
    private static final int FAILED_AT_CALL = 0;
    private static final int FOUND = 2;
    private static final int NO_RESULTS = 1;
    private int status;
    private ArrayList<Restaurant> restaurants;

    /**
     * A method that sets the status of whether the API call was sucessful.
     * @param status the status
     */
    public void setStatus(int status) {
        if (status == FAILED_AT_CALL) {
            this.status = FAILED_AT_CALL;
        }
        else if (status == NO_RESULTS) {
            this.status = NO_RESULTS;
        }
        else {
            this.status = FOUND;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<Restaurant> getRestaurant() {
        return restaurants;
    }
}
