package data_access;

import java.util.ArrayList;
import java.util.List;

import domain.OpeningHours;
import entity.Restaurant;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputData;
import use_case.search_nearby_locations.SearchLocationsNearbyDataAccessInterface;

public class FilterDataAccessObject implements FilterDataAccessInterface {
    private final SearchLocationsNearbyDataAccessInterface searchInterface;

    public FilterDataAccessObject() {
        this.searchInterface = new SearchLocationNearbyDataAccessObject();
    }

    @Override
    public ArrayList<Restaurant> getFilteredRestaurants(FilterInputData filterInputData) {
        final ArrayList<Restaurant> result = new ArrayList<>();
        final ArrayList<Restaurant> nearby = getNearbyRestaurants(filterInputData);
        final List<String> cuisines = filterInputData.getCuisine();
        final String rating = filterInputData.getRating();
        final String vegStat = filterInputData.getVegStat();
        final String availability = filterInputData.getAvailability();
        for (Restaurant r : nearby) {
            if (matchesAllFilters(r, cuisines, rating, vegStat, availability)) {
                result.add(r);
            }
        }
        return result;
    }

    private ArrayList<Restaurant> getNearbyRestaurants(FilterInputData data) {
        return searchInterface.getNearbyRestaurants(data.getAddress(), data.getRadius());
    }

    private boolean matchesAllFilters(Restaurant restaurant, List<String> cuisines, String rating,
                                      String vegStat, String availability) {
        boolean match = matchesCuisineFilter(cuisines, restaurant.getCuisine());
        match &= matchesRatingFilter(rating, restaurant.getRating());
        match &= matchesVegStatFilter(vegStat, restaurant.getVegStat());
        match &= matchesAvailabilityFilter(availability, restaurant.getOpeningHours());
        return match;
    }

    private boolean matchesCuisineFilter(List<String> userSelected, String restaurantCuisine) {
        boolean result = false;

        if (userSelected == null || userSelected.isEmpty()) {
            result = true;
        }
        else if (isCuisineValid(restaurantCuisine)) {
            final String[] parts = restaurantCuisine.split(";");
            for (String userCuisine : userSelected) {
                if (userCuisine == null) {
                    continue;
                }
                if (anyCuisineMatches(userCuisine, parts)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    private ArrayList<Restaurant> getNearbyRestaurants(FilterInputData filterInputData) {
        final int userRadius = filterInputData.getRadius();
        final String userAddress = filterInputData.getAddress();
        return searchLocationNearbyDataAccessObject.getNearbyRestaurantsResult(
                userAddress, userRadius).getRestaurant();
    }

    private boolean isCuisineValid(String cuisine) {
        return cuisine != null && !cuisine.isBlank() && !"not given".equalsIgnoreCase(cuisine);
    }

    private boolean anyCuisineMatches(String userCuisine, String[] parts) {
        boolean matchFound = false;
        for (String part : parts) {
            final boolean isValid = isValidCuisinePart(part);
            final boolean isEqual = part.trim().equalsIgnoreCase(userCuisine.trim());
            if (isValid && isEqual) {
                matchFound = true;
                break;
            }
        }
        return matchFound;
    }

    private boolean isValidCuisinePart(String part) {
        return part != null && !part.trim().isEmpty() && !part.trim().equalsIgnoreCase("not given");
    }

    private boolean matchesRatingFilter(String userRating, String restRating) {
        boolean result = false;
        if (userRating == null || userRating.isEmpty() || "None".equalsIgnoreCase(userRating)) {
            result = true;
        }
        else if (restRating != null && !restRating.isBlank() && !"not given".equalsIgnoreCase(restRating)) {
            try {
                final int userRate = Integer.parseInt(userRating);
                final int restRate = Integer.parseInt(restRating);
                result = restRate >= userRate;
            }
            catch (NumberFormatException error) {
                result = userRating.equalsIgnoreCase(restRating);
            }
        }
        return result;
    }

    private boolean matchesVegStatFilter(String userVeg, String restVeg) {
        boolean result = false;
        if (userVeg == null || userVeg.isEmpty() || "None".equalsIgnoreCase(userVeg)) {
            result = true;
        }
        else if (restVeg != null && !restVeg.isBlank() && !"not given".equalsIgnoreCase(restVeg)) {
            result = restVeg.equalsIgnoreCase(userVeg);
        }

        return result;
    }

    private boolean matchesAvailabilityFilter(String userAvailability, String restOpeningHours) {
        boolean result = false;

        if (userAvailability == null || userAvailability.isEmpty() || "None".equalsIgnoreCase(userAvailability)) {
            result = true;
        }
        else if (restOpeningHours == null || restOpeningHours.isBlank()
                || "not given".equalsIgnoreCase(restOpeningHours)) {
            result = "None".equalsIgnoreCase(userAvailability);
        }
        else if ("24/7".equalsIgnoreCase(restOpeningHours)) {
            result = !"Closed Now".equalsIgnoreCase(userAvailability);
        }
        else {
            final OpeningHours oh = new OpeningHours(restOpeningHours);
            final String status = oh.availabilityStatus();
            if ("Open Now".equalsIgnoreCase(userAvailability)) {
                result = "Open".equalsIgnoreCase(status);
            }
            else if ("Closed Now".equalsIgnoreCase(userAvailability)) {
                result = "Closed".equalsIgnoreCase(status);
            }
        }
        return result;
    }
}
