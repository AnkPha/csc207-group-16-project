package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.search_nearby_locations.SearchViewModel;
import entity.Restaurant;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.DefaultWaypoint;


import java.awt.*;
import java.util.Set;
import java.util.HashSet;



/**
 * The View for when the user is logged into the program.
 */
public class SearchPanel extends JPanel implements PropertyChangeListener {
    private final SearchViewModel searchViewModel;
    private final JLabel searchErrorField = new JLabel();
    private SearchLocationsNearbyController searchLocationsController;

    private final JTextField searchInputField = new JTextField(15);

    private final JTextField radiusInputField = new JTextField(15);

    private final JButton searchButton;

    private final JXMapViewer mapViewer = new JXMapViewer();

    private final JLabel notificationLabel = new JLabel("");

    private int zoom;

    private JPanel infoPanel = new JPanel();


    public SearchPanel(SearchViewModel searchViewModel) {
        //Starting with the left Panel
        searchViewModel.addPropertyChangeListener(this);
        mapViewer.setPreferredSize(new Dimension(800, 600));
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(new GeoPosition(43.6532, -79.3832)); // Default center: Toronto

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


        searchButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(searchButton)) {
                        SearchState currentState = searchViewModel.getState();

                        String address = searchInputField.getText();
                        String radiusText = radiusInputField.getText();

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
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Radius must be a valid number.");
                        }
                    }
                }
        );


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
        JPanel rightPanel = new JPanel();
        JButton filterButton = new JButton("Filter");
        JLabel infoPanelTitle = new JLabel("Restaurant Info");
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(infoPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightPanel.add(filterButton);
        rightPanel.add(infoPanelTitle);
        rightPanel.add(scrollPane);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

// Split pane for left (map + search fields) and right (info + filter button)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        infoPanel.removeAll();
        System.out.println("RAN #1");
        if (evt.getPropertyName().equals("state")) {
            System.out.println("RAN #2");
            SearchState newState = (SearchState) evt.getNewValue();
            if (newState.getResturants().isEmpty()) {
                System.out.println("NO WAYPOINYS CUZ NO RESTAURANTS");
                JOptionPane.showMessageDialog(this, "No Restaurants found");
//                notificationLabel.setText("No restaurants found in the specified radius.");
            } else {
                notificationLabel.setText(""); // Clear message
                mapViewer.setOverlayPainter(null);
                Set<Waypoint> waypoints = new HashSet<>();
                System.out.println("SIZE OF RESTURANTS " + newState.getResturants().size() );
                for (Restaurant r : newState.getResturants()) {
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
                    GeoPosition center = new GeoPosition(newState.getAddressCoords()[0], newState.getAddressCoords()[1]);
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
}
