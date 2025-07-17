package data_access;

import entity.Restaurant;
import use_case.filter.FilterDataAccessInterface;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

import java.util.ArrayList;

public class FilterDataAccessObject implements FilterDataAccessInterface {
    private final SearchLocationNearbyDataAccessObject searchLocationNearbyDataAccessObject;

    public FilterDataAccessObject() {
        this.searchLocationNearbyDataAccessObject = new SearchLocationNearbyDataAccessObject();
    }

    private boolean matchesFilter(String userFilter, String restFilter) {
        final boolean result;
        if (userFilter == null || userFilter.isEmpty() || "none".equalsIgnoreCase(userFilter)
                || restFilter == null || "not given".equalsIgnoreCase(restFilter)) {
            result = true;
        }
        else {
            result = restFilter.equalsIgnoreCase(userFilter);
        }
        return result;
    }

    @Override
    public ArrayList<Restaurant> getFilteredRestaurants(SearchLocationsNearbyInputData inputData, String cuisine,
                                                        String vegStat, String openingHours, String rating) {
        ArrayList<Restaurant> filteredRestaurants = new ArrayList<>();
        String userAddress = inputData.getAddress();
        int userRadius = inputData.getRadius();
        ArrayList<Restaurant> nearbyRestaurants = searchLocationNearbyDataAccessObject
                .getNearbyRestaurants(userAddress, userRadius);
        for (Restaurant r : nearbyRestaurants) {
            if (matchesFilter(cuisine, r.getCuisine()) && matchesFilter(vegStat, r.getVegStat())
                    && matchesFilter(openingHours, r.getOpeningHours()) && matchesFilter(rating, r.getRating())) {
                filteredRestaurants.add(r);
            }
        }
        return filteredRestaurants;
    }
}
