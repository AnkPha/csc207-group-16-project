package data_access;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Restaurant;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputData;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

public class FilterDataAccessObject implements FilterDataAccessInterface {
    private static final Map<String, DayOfWeek> DAY_MAP = Map.of(
            "Mo", DayOfWeek.MONDAY,
            "Tu", DayOfWeek.TUESDAY,
            "We", DayOfWeek.WEDNESDAY,
            "Th", DayOfWeek.THURSDAY,
            "Fr", DayOfWeek.FRIDAY,
            "Sa", DayOfWeek.SATURDAY,
            "Su", DayOfWeek.SUNDAY);
    private final SearchLocationNearbyDataAccessObject searchLocationNearbyDataAccessObject;

    public FilterDataAccessObject() {
        this.searchLocationNearbyDataAccessObject = new SearchLocationNearbyDataAccessObject();
    }

    private boolean matchesCuisineFilter(List<String> userSelectedCuisines, String restCuisine) {
        boolean match = false;
        final boolean noFilterSelected = userSelectedCuisines == null || userSelectedCuisines.isEmpty()
                || userSelectedCuisines.size() == 1 && (userSelectedCuisines.get(0) == null
                || userSelectedCuisines.get(0).isEmpty());
        if (noFilterSelected && "not given".equalsIgnoreCase(restCuisine)) {
            match = true;
        }
        else if (userSelectedCuisines != null) {
            for (String cuisine : userSelectedCuisines) {
                if (cuisine != null && cuisine.equalsIgnoreCase(restCuisine)) {
                    match = true;
                    break;
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
        else if ("yes".equalsIgnoreCase(userVegStat) && "yes".equalsIgnoreCase(restVegStat)) {
            match = true;
        }
        return match;
    }

    private boolean matchesRatingFilter(String userRating, String restRating) {
        boolean match = false;
        final boolean noFilterSelected = userRating == null || userRating.isEmpty();
        if (noFilterSelected && "not given".equalsIgnoreCase(restRating)) {
            match = true;
        }
        else {
            try {
                if (userRating != null) {
                    final int userRate = Integer.parseInt(userRating);
                    final int restRate = Integer.parseInt(restRating);
                    if (restRate >= userRate) {
                        match = true;
                    }
                }
            }
            catch (NumberFormatException error) {
                if (userRating.equalsIgnoreCase(restRating)) {
                    match = true;
                }
            }
        }
        return match;
    }

    private boolean matchesAvailabilityFilter(String userAvailability, String restHours) {
        boolean match = false;
        final boolean noFilter = userAvailability == null || userAvailability.isEmpty();
        final boolean notGiven = restHours == null || restHours.equalsIgnoreCase("not given");
        final boolean isOpen = !notGiven && isOpenNow(restHours);

        if (noFilter) {
            match = true;
        }
        else if ("Open".equalsIgnoreCase(userAvailability) && isOpen) {
            match = true;
        }
        else if ("Closed".equalsIgnoreCase(userAvailability) && !isOpen) {
            match = true;
        }
        return match;
    }

    private ArrayList<Restaurant> getNearbyRestaurants(FilterInputData filterInputData) {
        final int userRadius = filterInputData.getRadius();
        final String userAddress = filterInputData.getAddress();
        return searchLocationNearbyDataAccessObject.getNearbyRestaurantsResult(
                userAddress, userRadius).getRestaurant();
    }

    @Override
    public ArrayList<Restaurant> getFilteredRestaurants(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        System.out.println("BEFORE FILTER SIZE " + nearbyRestaurants.size());
        final ArrayList<Restaurant> filteredRestaurants = new ArrayList<>();
        for (Restaurant r : nearbyRestaurants) {
            System.out.println("CUISINE MATH? " + matchesCuisineFilter(filterInputData.getCuisine(), r.getCuisine())
                                + "IGNORE/AVAILABLE? " + matchesAvailabilityFilter(filterInputData.getAvailability(), r.getOpeningHours())
                                + "RAITING? " + matchesRatingFilter(filterInputData.getRating(), r.getRating())
                                + "VEGSTAT? " + matchesVegStatFilter(filterInputData.getVegStat(), r.getVegStat())
            );
            if (matchesCuisineFilter(filterInputData.getCuisine(), r.getCuisine())
                    && matchesRatingFilter(filterInputData.getRating(), r.getRating())) {
                filteredRestaurants.add(r);
//                    && matchesAvailabilityFilter(filterInputData.getAvailability(), r.getOpeningHours())
                //                    && matchesVegStatFilter(filterInputData.getVegStat(), r.getVegStat())
            }
        }
        System.out.println("AFTER FILTER SIZE " + filteredRestaurants.size());
        return filteredRestaurants;
    }

    public List<String> getCuisineOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        final List<String> options = new ArrayList<>();
        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String cuisine = r.getCuisine();
            if (cuisine != null && !cuisine.isBlank() && !options.contains(cuisine)) {
                options.add(cuisine);
            }
        }
        return options;
    }

    public List<String> getVegStatOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        final List<String> options = new ArrayList<>();
        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String vegStat = r.getVegStat();
            if (vegStat != null && !vegStat.isBlank() && !options.contains(vegStat)) {
                options.add(vegStat);
            }
        }
        return options;
    }

    public List<String> getOpeningHourOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        final List<String> options = new ArrayList<>();
        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String hours = r.getOpeningHours();
            if (hours != null && !hours.isBlank() && !options.contains(hours)) {
                options.add(hours);
            }
        }
        return options;
    }

    public List<String> getRatingOptions(FilterInputData filterInputData) {
        final ArrayList<Restaurant> nearbyRestaurants = getNearbyRestaurants(filterInputData);
        final List<String> options = new ArrayList<>();
        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String rating = r.getRating();
            if (rating != null && !rating.isBlank() && !options.contains(rating)) {
                options.add(rating);
            }
        }
        return options;
    }

    // For matchAvailability
    public boolean isOpenNow(String openingHours) {
        boolean isOpen = false;

        if (openingHours != null && !openingHours.isBlank()) {
            final LocalDateTime now = LocalDateTime.now();
            final DayOfWeek currentDay = now.getDayOfWeek();
            final LocalTime currentTime = now.toLocalTime();

            final String[] entries = openingHours.split(";");
            for (String entry : entries) {
                final String newEntry = entry.trim();
                if (!newEntry.isEmpty()) {
                    if (entryAppliesNow(newEntry, currentDay, currentTime)) {
                        isOpen = true;
                        break;
                    }
                }
            }
        }
        return isOpen;
    }

    private boolean entryAppliesNow(String entry, DayOfWeek currentDay, LocalTime currentTime) {
        boolean applies = false;

        final String[] parts = entry.split(" ", 2);
        if (parts.length == 2) {
            final Set<DayOfWeek> days = parseDays(parts[0]);
            if (days.contains(currentDay)) {
                final String[] times = parts[1].split("-", 2);
                if (times.length == 2) {
                    try {
                        final LocalTime start = LocalTime.parse(times[0]);
                        final LocalTime end = LocalTime.parse(times[1]);
                        applies = isTimeWithinRange(currentTime, start, end);
                    }
                    catch (DateTimeParseException ignored) {
                        // do nothing, applies stays false
                    }
                }
            }
        }

        return applies;
    }

    private boolean isTimeWithinRange(LocalTime currentTime, LocalTime start, LocalTime end) {
        final boolean withinRange;

        if (end.isBefore(start)) {
            withinRange = !currentTime.isBefore(start) || !currentTime.isAfter(end);
        }
        else {
            withinRange = !currentTime.isBefore(start) && !currentTime.isAfter(end);
        }

        return withinRange;
    }

    private Set<DayOfWeek> parseDays(String dayPart) {
        final Set<DayOfWeek> days = new HashSet<>();

        if (dayPart != null && !dayPart.isBlank()) {
            final String[] ranges = dayPart.split(",");
            for (String range : ranges) {
                final String newRange = range.trim();
                if (newRange.contains("-")) {
                    final String[] bounds = newRange.split("-", 2);
                    if (bounds.length == 2 && DAY_MAP.containsKey(bounds[0]) && DAY_MAP.containsKey(bounds[1])) {
                        final int start = DAY_MAP.get(bounds[0]).getValue();
                        final int end = DAY_MAP.get(bounds[1]).getValue();
                        int i = start;
                        while (true) {
                            days.add(DayOfWeek.of(i));
                            if (i == end) {
                                break;
                            }
                            i = i % 7 + 1;
                        }
                    }
                }
                else if (DAY_MAP.containsKey(newRange)) {
                    days.add(DAY_MAP.get(range));
                }
            }
        }

        return days;
    }
}
