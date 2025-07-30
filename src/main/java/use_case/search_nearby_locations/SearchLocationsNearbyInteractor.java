package use_case.search_nearby_locations;

import java.util.List;
import java.util.ArrayList;


import entity.Restaurant;

public class SearchLocationsNearbyInteractor implements SearchLocationsNearbyInputBoundary {
    private final SearchLocationsNearbyDataAccessInterface locationsDataAccessObject;
    private final SearchLocationsNearbyOutputBoundary locationsPresenter;

    public SearchLocationsNearbyInteractor(SearchLocationsNearbyDataAccessInterface locationsDataAccessInterface,
                                           SearchLocationsNearbyOutputBoundary locationsOutputBoundary) {
        this.locationsDataAccessObject = locationsDataAccessInterface;
        this.locationsPresenter = locationsOutputBoundary;
    }

    public void execute(SearchLocationsNearbyInputData locationInputData) {
        // Getting the address from the parameter input
        final String address = locationInputData.getAddress();

        //Call the resturant api throuh the locationsDataAccessObject
        ArrayList<Restaurant> nearbyRestaurants = locationsDataAccessObject.getNearbyRestaurants(locationInputData.getAddress(), locationInputData.getRadius());

        // Return the locations towards the output class
        final SearchLocationsNearbyOutputData output = new SearchLocationsNearbyOutputData(nearbyRestaurants);
        locationsPresenter.prepareSuccessView(output);
    }

}
