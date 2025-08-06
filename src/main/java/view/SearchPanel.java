package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.CurrentViewState;
import interface_adapter.filter.FilterState;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchState;
import interface_adapter.search_nearby_locations.SearchViewModel;
import entity.Restaurant;
import interface_adapter.filter.FilterController;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.DefaultWaypoint;


import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * The View for when the user is logged into the program.
 */
public class SearchPanel extends JPanel implements PropertyChangeListener {
    private final SearchViewModel searchViewModel;
    private final FilterViewModel filterViewModel;

    private final JLabel searchErrorField = new JLabel();
    private SearchLocationsNearbyController searchLocationsController;
    private FilterController filteringController;

    private final JTextField searchInputField = new JTextField(15);

    private final JTextField radiusInputField = new JTextField(15);

    private final JButton searchButton;

    private final JXMapViewer mapViewer = new JXMapViewer();

    private final JLabel notificationLabel = new JLabel("");

    private int zoom;

    private JPanel infoPanel = new JPanel();

    private SearchState currentState;

    private FilterState currentFilterState;
    private final JList<String> cuisineList;
    private final JComboBox<String> ratingComboBox;
    private final JComboBox<String> vegStatComboBox;
    private final JComboBox<String> hourComboBox;

    private String address;
    private String radiusText;
    private CurrentViewState currentViewState = new CurrentViewState();



    public SearchPanel(SearchViewModel searchViewModel, FilterViewModel filterViewModel) {
        //Starting with the left Panel

        searchViewModel.addPropertyChangeListener(this);
        filterViewModel.addPropertyChangeListener(this);

        currentViewState.setFilterState(filterViewModel.getState());
        currentViewState.setSearchState(searchViewModel.getState());

        mapViewer.setPreferredSize(new Dimension(800, 600));
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(new GeoPosition(43.6532, -79.3832)); // Default center: Toronto

        this.filterViewModel = filterViewModel;
        this.filterViewModel.addPropertyChangeListener(this);
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Label and input field for searching
        final LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel("Enter Address"), searchInputField);
        final LabelTextPanel radiusInfo = new LabelTextPanel(
                new JLabel("Enter Radius(In integer meters)"), radiusInputField);
        final JPanel buttons = new JPanel();

        searchButton = new JButton("Click To Search");
        buttons.add(searchButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));





        this.add(title);

        // Left panel with search fields and the map
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // Search fields and labels
        JPanel searchControls = new JPanel();
        searchControls.setLayout(new BoxLayout(searchControls, BoxLayout.Y_AXIS));
        searchControls.add(searchInfo);
        searchControls.add(searchErrorField);
        searchControls.add(radiusInfo);
        searchControls.add(buttons);
//        searchControls.add(notificationLabel);

        leftPanel.add(mapViewer, BorderLayout.CENTER);
        leftPanel.add(searchControls, BorderLayout.SOUTH);

        // Right side: place info panel


        //Filter things
        JButton filterButton = new JButton("Filter");
        final JPanel filterPanel = new JPanel(new FlowLayout());

        cuisineList = new JList<>(new DefaultListModel<>());
        cuisineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane cuisineScrollPane = new JScrollPane(cuisineList);

        ratingComboBox = new JComboBox<>();
        ratingComboBox.setEditable(true);
        ratingComboBox.addItem("");

        vegStatComboBox = new JComboBox<>();
        vegStatComboBox.setEditable(true);
        vegStatComboBox.addItem("");

        hourComboBox = new JComboBox<>();
        hourComboBox.setEditable(true);
        hourComboBox.addItem("");



        filterPanel.add(new JLabel("Cuisine:"));
        filterPanel.add(cuisineScrollPane);
        filterPanel.add(new JLabel("Rating:"));
        filterPanel.add(ratingComboBox);
        filterPanel.add(new JLabel("Vegetarian Status:"));
        filterPanel.add(vegStatComboBox);
        filterPanel.add(new JLabel("Opening Hour:"));
        filterPanel.add(hourComboBox);
        filterPanel.add(filterButton);


        JPanel rightPanel = new JPanel();
        JLabel infoPanelTitle = new JLabel("Restaurant Info");
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(infoPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightPanel.add(filterButton);
        rightPanel.add(infoPanelTitle);
        rightPanel.add(scrollPane);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));


        rightPanel.add(filterPanel);
        rightPanel.add(filterButton);

        // Split pane for left (map + search fields) and right (info + filter button)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);

        filterButton.addActionListener(evt -> {
            currentState = searchViewModel.getState();
            if (currentState.getAddress() == null && currentState.getRadius() == null) {
                JOptionPane.showMessageDialog(this, "Please fill out address and radius first");
            }
            final List<String> selectedCuisines = cuisineList.getSelectedValuesList();
            final String selectedRating = ratingComboBox.getSelectedItem().toString();
            final String selectedVegStat = vegStatComboBox.getSelectedItem().toString();
            final String selectedHour = hourComboBox.getSelectedItem().toString();

            currentState.setFiltered(true);
            filteringController.execute(currentState.getAddress(), Integer.parseInt(currentState.getRadius()), selectedCuisines, selectedVegStat, selectedHour, selectedRating);
            currentViewState.setFilterState(filterViewModel.getState());
        });

        searchButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(searchButton)) {
                        currentState = searchViewModel.getState();
                        currentState.setFiltered(false);
                        address = searchInputField.getText();
                        radiusText = radiusInputField.getText();
                        currentViewState.setSearchState(currentState);
                        if (address.isEmpty() || radiusText.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Address or Radius cannot be empty.");
                            return;
                        }

                        try {
                            int radius = Integer.parseInt(radiusText);

                            currentState.setAddress(address);
                            currentState.setRadius(radiusText);
                            if (radius <= 100) {
                                zoom = 0; // Very close - building level
                            } else if (radius <= 300) {
                                zoom = 2;
                            } else if (radius <= 1000) {
                                zoom = 3;
                            } else if (radius <= 3000) {
                                zoom = 4;
                            } else if (radius <= 10000) {
                                zoom = 5;
                            } else if (radius <= 25000) {
                                zoom = 6;
                            } else if (radius <= 100000) {
                                zoom = 7;
                            } else if (radius <= 500000) {
                                zoom = 8;
                            } else {
                                zoom = 9;
                            }
                            searchLocationsController.execute(address, radius);
                            currentState = searchViewModel.getState();
                            currentState.setRadius(radiusText);
                            currentState.setAddress(address);
                            currentViewState.setSearchState(currentState);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Radius must be a valid number.");
                        }
                    }
                }
        );



    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        infoPanel.removeAll();
        currentViewState.setFilterState(filterViewModel.getState());
        currentViewState.setSearchState(searchViewModel.getState());

        cuisineList.removeAll();
        cuisineList.setModel(currentViewState.getCuisineOptions());

        ratingComboBox.removeAllItems();
        ratingComboBox.setModel(currentViewState.getRatingOptions());

        vegStatComboBox.removeAllItems();
        vegStatComboBox.setModel(currentViewState.getVegStatOptions());

        hourComboBox.removeAllItems();
        hourComboBox.setModel(currentViewState.getHourOptions());

        System.out.println("RAN #1");
        if (evt.getPropertyName().equals("state")) {
            System.out.println("RAN #2: SEARCH");
            if (currentViewState.getActiveRestaurants().isEmpty()) {
                System.out.println("NO WAYPOINYS CUZ NO RESTAURANTS " + "FILTERED? " + currentViewState.isFiltered());
                JOptionPane.showMessageDialog(this, "No Restaurants found");
//                notificationLabel.setText("No restaurants found in the specified radius.");
            } else {
                notificationLabel.setText(""); // Clear message
                mapViewer.setOverlayPainter(null);
                Set<Waypoint> waypoints = new HashSet<>();
                System.out.println("SIZE OF RESTURANTS " + currentViewState.getActiveRestaurants().size() + "FILTERED? " + currentViewState.isFiltered());
                for (Restaurant r : currentViewState.getActiveRestaurants()) {
                    System.out.println("Restaurant: " + r.getName() + " @ " + r.getLat() + ", " + r.getLon());
                    if (r.getLat() != 0.0 && r.getLon() != 0.0) {
                        GeoPosition pos = new GeoPosition(r.getLat(), r.getLon());
                        waypoints.add(new DefaultWaypoint(pos));
                        JLabel nameLabel = new JLabel("Name: " + r.getName());
                        JLabel cuisineLabel = new JLabel("Cuisine: " + r.getCuisine());
                        JLabel websiteLabel = new JLabel("Website: " + r.getWebsite());
                        JLabel hoursLabel = new JLabel("Opening Hours: " + r.getOpeningHours());

                        infoPanel.add(nameLabel);
                        infoPanel.add(cuisineLabel);
                        infoPanel.add(websiteLabel);
                        infoPanel.add(hoursLabel);
                        infoPanel.add(new JLabel("------"));
                        infoPanel.revalidate();
                        infoPanel.repaint();
                    }
                }

                WaypointPainter<Waypoint> painter = new WaypointPainter<>();
                painter.setWaypoints(waypoints);
                mapViewer.setOverlayPainter(painter);
                if (!waypoints.isEmpty()) {
                    GeoPosition center = new GeoPosition(currentViewState.getSearchState().getAddressCoords()[0], currentViewState.getSearchState().getAddressCoords()[1]);
                    mapViewer.setAddressLocation(center);
                    mapViewer.setZoom(zoom);
                    System.out.println("THERE EXISTS A WAYPOINT");
                }

                mapViewer.repaint();
            }

        }
    }

    public void setSearchLocationsController(SearchLocationsNearbyController searchLocationsController) {
        this.searchLocationsController = searchLocationsController;
    }

    public void setFilteringController(FilterController filteringController) {
        this.filteringController = filteringController;
    }

}
