package data_access;

import entity.Restaurant;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputData;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

import java.util.ArrayList;
import java.util.List;

public class FilterDataAccessObject implements FilterDataAccessInterface {
    private final SearchLocationNearbyDataAccessObject searchLocationNearbyDataAccessObject;

    public FilterDataAccessObject() {
        this.searchLocationNearbyDataAccessObject = new SearchLocationNearbyDataAccessObject();
    }

    private boolean matchesCuisineFilter(List<String> userSelectedCuisines, String restCuisine) {
        boolean match = false;
        final boolean noFilterSelected = userSelectedCuisines == null || userSelectedCuisines.isEmpty()
                || userSelectedCuisines.size() == 1 && (userSelectedCuisines.get(0) == null
                || userSelectedCuisines.get(0).isEmpty());
        if (noFilterSelected && "not given".equals(restCuisine)) {
            match = true;
        }
        else {
            if (userSelectedCuisines != null) {
                for (String cuisine : userSelectedCuisines) {
                    if (cuisine != null && cuisine.equalsIgnoreCase(restCuisine)) {
                        match = true;
                        break;
                    }
                }
            }
        }
        return match;
    }

    private boolean matchesVegStatFilter(String userVegStat, String restVegStat) {
        boolean match = false;
        final boolean noFilterSelected = userVegStat == null || userVegStat.isEmpty();
        if (noFilterSelected && "not given".equals(restVegStat)) {
            match = true;
        }
        /// yes could change
        else if ("yes".equalsIgnoreCase(userVegStat) && "yes".equalsIgnoreCase(restVegStat)) {
            match = true;
        }
        return match;
    }

    private boolean matchesRatingFilter(String userRating, String restRating) {
        boolean match = false;
        final boolean noFilterSelected = userRating == null || userRating.isEmpty();
        if (noFilterSelected && "not given".equals(restRating)) {
            match = true;
        }
        /// idk will change
        else if ("idk".equalsIgnoreCase(userRating) && "idk".equalsIgnoreCase(restRating)) {
            match = true;
        }
        return match;
    }

    private boolean matchesOpeningHourFilter(String userHour, String restHour) {
        /// THIS WILL CHANGE ENTIRELY
        boolean match = false;
        final boolean noFilterSelected = userHour == null || userHour.isEmpty();
        if (noFilterSelected && "not given".equals(restHour)) {
            match = true;
        }
        else if ("yes".equalsIgnoreCase(userHour) && "yes".equalsIgnoreCase(restHour)) {
            match = true;
        }
        return match;
    }

    private ArrayList<Restaurant> getNearbyRestaurants(FilterInputData filterInputData) {
        final SearchLocationsNearbyInputData locationData = filterInputData.getLocations();
        final int userRadius = locationData.getRadius();
        final String userAddress = locationData.getAddress();
        return searchLocationNearbyDataAccessObject.getNearbyRestaurants(
                userAddress, userRadius);
    }

    @Override
    public ArrayList<Restaurant> getFilteredRestaurants(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        ArrayList<Restaurant> filteredRestaurants = new ArrayList<>();
        for (Restaurant r : nearbyRestaurants) {
            if (matchesCuisineFilter(filterInputData.getCuisine(), r.getCuisine())
                    && matchesVegStatFilter(filterInputData.getVegStat(), r.getVegStat())
                    && matchesOpeningHourFilter(filterInputData.getOpeningHours(), r.getOpeningHours())
                    && matchesRatingFilter(filterInputData.getRating(), r.getRating())) {
                filteredRestaurants.add(r);
            }
        }
        return filteredRestaurants;
    }

    public List<String> getCuisineOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        List<String> options = new ArrayList<>();
        options.add("");
        for (Restaurant r : nearbyRestaurants) {
            if (!options.contains(r.getCuisine())) {
                options.add(r.getCuisine());
            }
        }
        return options;
    }

    public List<String> getVegStatOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        List<String> options = new ArrayList<>();
        options.add("");
        for (Restaurant r : nearbyRestaurants) {
            if (!options.contains(r.getVegStat())) {
                options.add(r.getVegStat());
            }
        }
        return options;
    }

    public List<String> getOpeningHourOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        List<String> options = new ArrayList<>();
        options.add("");
        for (Restaurant r : nearbyRestaurants) {
            if (!options.contains(r.getOpeningHours())) {
                options.add(r.getOpeningHours());
            }
        }
        return options;
    }

    public List<String> getRatingOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        List<String> options = new ArrayList<>();
        options.add("");
        for (Restaurant r : nearbyRestaurants) {
            if (!options.contains(r.getRating())) {
                options.add(r.getRating());
            }
        }
        return options;
    }
}

