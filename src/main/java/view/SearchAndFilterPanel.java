//package view;
//
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.*;
//
//import data_access.FilterDataAccessObject;
//import entity.Restaurant;
//import interface_adapter.filter.FilterController;
//import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
//import use_case.filter.FilterInputData;
//import use_case.search_nearby_locations.SearchLocationsNearbyInputData;
//
//class SearchAndFilterPanel extends JPanel {
//    private final JTextField addressField;
//    private final JTextField radiusField;
//
//    private final JList<String> cuisineList;
//    private final JComboBox<String> ratingComboBox;
//    private final JComboBox<String> vegStatComboBox;
//    private final JComboBox<String> hourComboBox;
//
//    private List<Restaurant> searchingRestaurants = new ArrayList<>();
//    private final FilterDataAccessObject filterDataAccessObject;
//    private SearchLocationsNearbyInputData searchInputData;
//
//    public SearchAndFilterPanel(FilterController filtering, SearchLocationsNearbyController searching,
//                         FilterDataAccessObject filterDataAccessObject) {
//
//        this.filterDataAccessObject = filterDataAccessObject;
//
//        this.setLayout(new BorderLayout());
//
//        // Title
//        final JLabel title = new JLabel("Search Restaurants by Map and Filters");
//        this.add(title, BorderLayout.NORTH);
//
//        // Search panel
//        final JPanel searchPanel = new JPanel(new FlowLayout());
//        addressField = new JTextField(20);
//        radiusField = new JTextField(20);
//        final JButton searchButton = new JButton("Search");
//
//        searchPanel.add(new JLabel("Address:"));
//        searchPanel.add(addressField);
//        searchPanel.add(new JLabel("Radius:"));
//        searchPanel.add(radiusField);
//        searchPanel.add(searchButton);
//
//        this.add(searchPanel, BorderLayout.CENTER);
//
//        // Filter panel
//        final JPanel filterPanel = new JPanel(new FlowLayout());
//
//        cuisineList = new JList<>(new DefaultListModel<>());
//        cuisineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        final JScrollPane cuisineScrollPane = new JScrollPane(cuisineList);
//
//        ratingComboBox = new JComboBox<>();
//        ratingComboBox.setEditable(true);
//        ratingComboBox.addItem("");
//
//        vegStatComboBox = new JComboBox<>();
//        vegStatComboBox.setEditable(true);
//        vegStatComboBox.addItem("");
//
//        hourComboBox = new JComboBox<>();
//        hourComboBox.setEditable(true);
//        hourComboBox.addItem("");
//
//        final JButton filterButton = new JButton("Filter");
//
//        filterPanel.add(new JLabel("Cuisine:"));
//        filterPanel.add(cuisineScrollPane);
//        filterPanel.add(new JLabel("Rating:"));
//        filterPanel.add(ratingComboBox);
//        filterPanel.add(new JLabel("Vegetarian Status:"));
//        filterPanel.add(vegStatComboBox);
//        filterPanel.add(new JLabel("Opening Hour:"));
//        filterPanel.add(hourComboBox);
//        filterPanel.add(filterButton);
//
//        this.add(filterPanel, BorderLayout.SOUTH);
//
//        // Search button logic
//        searchButton.addActionListener(evt -> {
//            final String address = addressField.getText();
//            final String radius = radiusField.getText();
//
//            if (address.isEmpty() || radius.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please enter a valid address and radius");
//            }
//            final int radiusQuery;
//            try {
//                radiusQuery = Integer.parseInt(radius);
//                if (radiusQuery <= 0) {
//                    throw new NumberFormatException("Radius must be > 0");
//                }
//            }
//            catch (NumberFormatException error) {
//                JOptionPane.showMessageDialog(this, "Please enter a valid radius");
//            }
////            searchInputData = new SearchLocationsNearbyInputData(address, radiusQuery);
////            searchingRestaurants = searching.execute(address, radiusQuery);
//            updateCuisineOptions();
//            updateOtherFilterOptions();
//        });
//
//        // Filter button logic
//        filterButton.addActionListener(evt -> {
//            if (searchInputData == null) {
//                JOptionPane.showMessageDialog(this, "Please fill out address and radius first");
//            }
//
//            final List<String> selectedCuisines = cuisineList.getSelectedValuesList();
//            final String selectedRating = ratingComboBox.getSelectedItem().toString();
//            final String selectedVegStat = vegStatComboBox.getSelectedItem().toString();
//            final String selectedHour = hourComboBox.getSelectedItem().toString();
//
//            final FilterInputData filterInputData = new FilterInputData(
//                    searchInputData, selectedCuisines, selectedVegStat, selectedHour, selectedRating);
//
//            filtering.execute(filterInputData);
//        });
//    }
//
//    public void updateCuisineOptions() {
//        final FilterInputData f = new FilterInputData(searchInputData, null, null, null, null);
//        final List<String> cuisines = filterDataAccessObject.getCuisineOptions(f);
//
//        final DefaultListModel<String> model = new DefaultListModel<>();
//        model.addElement("");
//        for (String c : cuisines) {
//            if (c != null && !c.isEmpty()) {
//                model.addElement(c);
//            }
//        }
//        cuisineList.setModel(model);
//    }
//
//    public void updateOtherFilterOptions() {
//        final FilterInputData f = new FilterInputData(searchInputData, null, null, null, null);
//        final List<String> ratingOptions = filterDataAccessObject.getRatingOptions(f);
//        ratingComboBox.removeAllItems();
//        ratingComboBox.addItem("");
//        for (String rating : ratingOptions) {
//            if (rating != null && !rating.isEmpty()) {
//                ratingComboBox.addItem(rating);
//            }
//        }
//        final List<String> vegStatOptions = filterDataAccessObject.getVegStatOptions(f);
//        vegStatComboBox.removeAllItems();
//        vegStatComboBox.addItem("");
//        for (String veg : vegStatOptions) {
//            if (veg != null && !veg.isEmpty()) {
//                vegStatComboBox.addItem(veg);
//            }
//        }
//        final List<String> hourOptions = filterDataAccessObject.getOpeningHourOptions(f);
//        hourComboBox.removeAllItems();
//        hourComboBox.addItem("");
//        for (String hour : hourOptions) {
//            if (hour != null && !hour.isEmpty()) {
//                hourComboBox.addItem(hour);
//            }
//        }
//    }
//}
