package interface_adapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import entity.Restaurant;
import interface_adapter.filter.FilterState;
import interface_adapter.search_nearby_locations.SearchState;

public class CurrentViewState {
    private static final int FOUND = 2;
    private SearchState searchState;
    private FilterState filterState;

    /**
     * A method that gets the active restaurants depending on whether it is a
     * search or filter.
     * @return result returns a list of the restaurants
     */
    public ArrayList<Restaurant> getActiveRestaurants() {
        final ArrayList<Restaurant> result;
        if (searchState.getFiltered()) {
            result = filterState.getRestaurants();
        }
        else {
            result = searchState.getResturants();
        }
        return result;
    }

    /**
     * A method that gets the current status.
     * @return the status of the program when viewing
     */
    public int getStatus() {
        final int result;
        if (searchState.getFiltered()) {
            result = FOUND;
        }
        else {
            result = searchState.getStatus();
        }
        return result;
    }

    public void setSearchState(SearchState searchState) {
        this.searchState = searchState;
    }

    public void setFilterState(FilterState filterState) {
        this.filterState = filterState;
    }

    public boolean isFiltered() {
        return searchState.getFiltered();
    }

    public SearchState getSearchState() {
        return searchState;
    }

    /**
     * A method that gets the cuisine options.
     * @return A list of unique cuisines
     */
    public DefaultListModel getCuisineOptions() {
        final ArrayList<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final List<String> options = new ArrayList<>();
        final DefaultListModel<String> model = new DefaultListModel<>();

        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String cuisine = r.getCuisine();
            if (cuisine != null && !cuisine.isBlank() && !options.contains(cuisine)) {
                options.add(cuisine);
                model.addElement(cuisine);
            }
        }
        return model;
    }

    /**
     * A method that returns a list of rating options.
     * @return a list of rating options
     */
    public DefaultComboBoxModel<String> getRatingOptions() {
        final ArrayList<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final List<String> options = new ArrayList<>();
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String rating = r.getRating();
            if (rating != null && !rating.isBlank() && !options.contains(rating)) {
                options.add(rating);
                model.addElement(rating);
            }
        }
        return model;
    }

    /**
     * A method that returns the veg stat options.
     * @return a list of veg stat options
     */
    public DefaultComboBoxModel<String> getVegStatOptions() {
        final ArrayList<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final List<String> options = new ArrayList<>();
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String vegStat = r.getVegStat();
            if (vegStat != null && !vegStat.isBlank() && !options.contains(vegStat)) {
                options.add(vegStat);
                model.addElement(vegStat);
            }
        }
        return model;
    }

    /**
     * A method that gets the option of hours.
     * @return a list of hourly options
     */
    public DefaultComboBoxModel<String> getHourOptions() {
        final ArrayList<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final List<String> options = new ArrayList<>();
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        options.add("");

        for (Restaurant r : nearbyRestaurants) {
            final String openingHours = r.getOpeningHours();
            if (openingHours != null && !openingHours.isBlank() && !options.contains(openingHours)) {
                options.add(openingHours);
                model.addElement(openingHours);
            }
        }
        return model;
    }
}

