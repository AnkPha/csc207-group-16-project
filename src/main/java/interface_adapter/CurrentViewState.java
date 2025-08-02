package interface_adapter;
import interface_adapter.search_nearby_locations.SearchState;
import interface_adapter.filter.FilterState;
import entity.Restaurant;
import use_case.filter.FilterInputData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class CurrentViewState {
    private SearchState searchState;
    private FilterState filterState;

    public ArrayList<Restaurant> getActiveRestaurants() {
        if (searchState.getFiltered()){
            return filterState.getRestaurants();
        }
        else{
            return searchState.getResturants();
        }
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

    public SearchState getSearchState(){
        return searchState;
    }

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
//        return options;


//        final List<String> cuisines = filterDataAccessObject.getCuisineOptions(f);

//        ArrayList<Restaurant>
//        model.addElement("");
//        for (String c : cuisines) {
//            if (c != null && !c.isEmpty()) {
//                model.addElement(c);
//            }
//        }
        return model;
//        cuisineList.setModel(model);
    }

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


//    public void updateOtherFilterOptions() {
//        final FilterInputData f = new FilterInputData(searchInputData, null, null, null, null);
////        final List<String> ratingOptions = filterDataAccessObject.getRatingOptions(f);
//        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//
//        ratingComboBox.removeAllItems();
//        ratingComboBox.addItem("");
//        for (String rating : ratingOptions) {
//            if (rating != null && !rating.isEmpty()) {
//                ratingComboBox.addItem(rating);
//            }
//        }
////        final List<String> vegStatOptions = filterDataAccessObject.getVegStatOptions(f);
//        vegStatComboBox.removeAllItems();
//        vegStatComboBox.addItem("");
//        for (String veg : vegStatOptions) {
//            if (veg != null && !veg.isEmpty()) {
//                vegStatComboBox.addItem(veg);
//            }
//        }
////        final List<String> hourOptions = filterDataAccessObject.getOpeningHourOptions(f);
//        hourComboBox.removeAllItems();
//        hourComboBox.addItem("");
//        for (String hour : hourOptions) {
//            if (hour != null && !hour.isEmpty()) {
//                hourComboBox.addItem(hour);
//            }
//        }
//    }
}

