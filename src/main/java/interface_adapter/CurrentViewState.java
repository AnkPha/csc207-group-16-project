package interface_adapter;

import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import entity.Restaurant;
import interface_adapter.filter.FilterState;
import interface_adapter.search_nearby_locations.SearchState;

public class CurrentViewState {
    private SearchState searchState;
    private FilterState filterState;

    public List<Restaurant> getActiveRestaurants() {
        List<Restaurant> result = Collections.emptyList();
        System.out.println("[DEBUG] Getting active restaurants...");
        if (searchState != null && searchState.getFiltered() && filterState != null) {
            System.out.println("[DEBUG] Returning filtered restaurants: " + filterState.getRestaurants().size());
            result = filterState.getRestaurants();
        }
        else if (searchState != null && searchState.getResturants() != null) {
            System.out.println("[DEBUG] Returning search restaurants: " + searchState.getResturants().size());
            result = searchState.getResturants();
        }
        else {
            System.out.println("[DEBUG] No restaurants available.");
        }
        return result;
    }

    public void setSearchState(SearchState searchState) {
        this.searchState = searchState;
        System.out.println("[DEBUG] SearchState set: " + (searchState != null));
    }

    public void setFilterState(FilterState filterState) {
        this.filterState = filterState;
        System.out.println("[DEBUG] FilterState set: " + (filterState != null));
    }

    public SearchState getSearchState() {
        return searchState;
    }

    public DefaultListModel<String> getCuisineOptions() {
        final List<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("None");

        for (Restaurant r : nearbyRestaurants) {
            final String cuisine = r.getCuisine();
            if (cuisine == null || cuisine.isBlank()) {
                continue;
            }
            final String[] parts = cuisine.split(";");
            for (String part : parts) {
                final String trimmed = part.trim();
                if (!trimmed.isEmpty() && !"not given".equalsIgnoreCase(trimmed)
                        && !containsIgnoreCase(model, trimmed)) {
                    model.addElement(trimmed);
                }
            }
        }
        return model;
    }

    public DefaultComboBoxModel<String> getRatingOptions() {
        final List<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("None");

        for (Restaurant r : nearbyRestaurants) {
            final String rating = r.getRating();
            if (rating != null && !rating.isBlank() && !"not given".equalsIgnoreCase(rating)
                    && containsIgnoreCase(model, rating)) {
                model.addElement(rating);
            }
        }
        return model;
    }

    public DefaultComboBoxModel<String> getVegStatOptions() {
        final List<Restaurant> nearbyRestaurants = getActiveRestaurants();
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("None");

        for (Restaurant r : nearbyRestaurants) {
            final String vegStat = r.getVegStat();
            if (vegStat != null && !vegStat.isBlank() && !"not given".equalsIgnoreCase(vegStat)
                    && containsIgnoreCase(model, vegStat)) {
                model.addElement(vegStat);
            }
        }
        return model;
    }

    public DefaultComboBoxModel<String> getHourOptions() {
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("None");
        model.addElement("Open Now");
        model.addElement("Closed Now");
        return model;
    }

    private boolean containsIgnoreCase(DefaultListModel<String> model, String value) {
        boolean result = false;
        for (int i = 0; i < model.size(); i++) {
            if (model.get(i).equalsIgnoreCase(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean containsIgnoreCase(DefaultComboBoxModel<String> model, String value) {
        boolean result = false;
        for (int i = 0; i < model.getSize(); i++) {
            final Object el = model.getElementAt(i);
            if (el != null && el.toString().equalsIgnoreCase(value)) {
                result = true;
                break;
            }
        }
        return !result;
    }
}
