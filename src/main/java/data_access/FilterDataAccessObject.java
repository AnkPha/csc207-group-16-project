package data_access;

import java.util.ArrayList;
import java.util.List;

import domain.OpeningHours;
import entity.Restaurant;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputData;
import use_case.review.AddReviewAccessInterface;

public class FilterDataAccessObject implements FilterDataAccessInterface {
    private static final String NOT_GIVEN = "not given";
    private static final String NONE = "None";
    private final SearchLocationNearbyDataAccessObject searchInterface;
    private final AddReviewAccessInterface reviewDataAccess;

    public FilterDataAccessObject(AddReviewAccessInterface reviewDataAccess) {
        this.reviewDataAccess = reviewDataAccess;
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
        return searchInterface.getNearbyRestaurantsResult(data.getAddress(), data.getRadius()).getRestaurant();
    }

    private boolean matchesAllFilters(Restaurant restaurant, List<String> cuisines, String rating,
                                      String vegStat, String availability) {
        boolean match = matchesCuisineFilter(cuisines, restaurant.getCuisine());
        match &= matchesRatingFilter(rating, restaurant);
        match &= matchesVegStatFilter(vegStat, restaurant.getVegStat());
        match &= matchesAvailabilityFilter(availability, restaurant.getOpeningHours());
        return match;
    }

    private boolean matchesCuisineFilter(List<String> userSelected, String restaurantCuisine) {
        boolean result = false;

        // Treat null, empty, or ["None"] as no filter = match everything
        if (userSelected == null || userSelected.isEmpty()
                || userSelected.size() == 1 && NONE.equalsIgnoreCase(userSelected.get(0))) {
            result = true;
        }
        else if (isCuisineValid(restaurantCuisine)) {
            final String[] parts = restaurantCuisine.split(";");
            for (String userCuisine : userSelected) {
                if (userCuisine != null && anyCuisineMatches(userCuisine, parts)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private boolean isCuisineValid(String cuisine) {
        return cuisine != null && !cuisine.isBlank() && !NOT_GIVEN.equalsIgnoreCase(cuisine);
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
        return part != null && !part.trim().isEmpty() && !part.trim().equalsIgnoreCase(NOT_GIVEN);
    }

    private boolean matchesRatingFilter(String userRating, Restaurant restaurant) {
        System.out.println("DEBUG FILTER: Checking rating filter for restaurant: " + restaurant.getName());
        System.out.println("DEBUG FILTER: User wants minimum rating: " + userRating);
        System.out.println("DEBUG FILTER: Restaurant rating string: '" + restaurant.getRating() + "'");

        // If no rating filter specified, match everything
        if (userRating == null || userRating.isEmpty() || NONE.equalsIgnoreCase(userRating)) {
            System.out.println("DEBUG FILTER: No rating filter, returning true");
            return true;
        }

        try {
            double minRating = Double.parseDouble(userRating);
            System.out.println("DEBUG FILTER: Parsed minimum rating: " + minRating);

            // Check if restaurant has ratings
            String restaurantRatingStr = restaurant.getRating();
            if ("No Ratings".equals(restaurantRatingStr)) {
                System.out.println("DEBUG FILTER: Restaurant has no ratings, returning false");
                return false;
            }

            // Parse restaurant's rating
            try {
                double restaurantRating = Double.parseDouble(restaurantRatingStr);
                System.out.println("DEBUG FILTER: Restaurant numeric rating: " + restaurantRating);
                boolean matches = restaurantRating >= minRating;
                System.out.println("DEBUG FILTER: Rating filter result: " + matches);
                return matches;
            } catch (NumberFormatException e) {
                System.out.println("DEBUG FILTER: Could not parse restaurant rating: " + restaurantRatingStr);
                return false;
            }

        } catch (NumberFormatException e) {
            System.out.println("DEBUG FILTER: Could not parse user rating: " + userRating);
            return false;
        }
    }

    private boolean matchesStaticRating(String userRating, String restRating) {
        // Fixed the condition - it was using OR instead of AND
        if (restRating != null && !restRating.isBlank() && !NONE.equalsIgnoreCase(restRating)) {
            try {
                final int userRate = Integer.parseInt(userRating);
                final int restRate = Integer.parseInt(restRating);
                return restRate >= userRate;
            }
            catch (NumberFormatException e) {
                return userRating.equalsIgnoreCase(restRating);
            }
        }
        return false;
    }

    private boolean matchesVegStatFilter(String userVeg, String restVeg) {
        boolean result = false;
        if (userVeg == null || userVeg.isEmpty() || NONE.equalsIgnoreCase(userVeg)) {
            result = true;
        }
        else if (restVeg != null && !restVeg.isBlank() && !NOT_GIVEN.equalsIgnoreCase(restVeg)) {
            result = restVeg.equalsIgnoreCase(userVeg);
        }

        return result;
    }

    private boolean matchesAvailabilityFilter(String userAvailability, String restOpeningHours) {
        boolean result = false;

        if (userAvailability == null || userAvailability.isEmpty() || NONE.equalsIgnoreCase(userAvailability)) {
            result = true;
        }
        else if (restOpeningHours == null || restOpeningHours.isBlank()
                || NOT_GIVEN.equalsIgnoreCase(restOpeningHours)) {
            result = NONE.equalsIgnoreCase(userAvailability);
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
