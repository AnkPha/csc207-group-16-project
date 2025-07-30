package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

    private final String SearchName = "logged in";
    private final SearchViewModel searchViewModel;
    private final JLabel searchErrorField = new JLabel();
    private SearchLocationsNearbyController searchLocationsController;
//    private LogoutController logoutController;

//    private final JLabel username;

//    private final JButton logOut;

    private final JTextField searchInputField = new JTextField(15);

    private final JTextField radiusInputField = new JTextField(15);

    private final JButton searchButton;

    private final JXMapViewer mapViewer = new JXMapViewer();

    private final JLabel notificationLabel = new JLabel("");

    public SearchPanel(SearchViewModel searchViewModel) {

        searchViewModel.addPropertyChangeListener(this);
        mapViewer.setPreferredSize(new Dimension(800, 600));
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(new GeoPosition(43.6532, -79.3832)); // Default center: Toronto
        this.add(mapViewer);

        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Label and input field for searching
        final LabelTextPanel searchInfo = new LabelTextPanel(
                new JLabel("Enter Address"), searchInputField);

        final LabelTextPanel radiusInfo = new LabelTextPanel(
                new JLabel("Enter Radius"), radiusInputField);

//        final JLabel usernameInfo = new JLabel("Currently logged in: ");
//        username = new JLabel();

        final JPanel buttons = new JPanel();
//        logOut = new JButton("Log Out");
//        buttons.add(logOut);

        searchButton = new JButton("Address here");
        buttons.add(searchButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        searchButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
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

                            searchLocationsController.execute(address, radius);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Radius must be a valid number.");
                        }
                    }
                }
        );

        this.add(title);
//        this.add(usernameInfo);
//        this.add(username);

        this.add(searchInfo);
        this.add(searchErrorField);

        this.add(radiusInfo);

        this.add(buttons);
        this.add(notificationLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("RAN #1");
        if (evt.getPropertyName().equals("state")) {
            System.out.println("RAN #2");
            SearchState newState = (SearchState) evt.getNewValue();//            resultLabel.setText("Searching for: " + newState.getAddress());
            // or update map, results list, etc.
            if (newState.getResturants().isEmpty()) {
                System.out.println("NO WAYPOINYS CUZ NO RESTAURANTS");
                notificationLabel.setText("No restaurants found in the specified radius.");
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
                    }
                }

                WaypointPainter<Waypoint> painter = new WaypointPainter<>();
                painter.setWaypoints(waypoints);
                mapViewer.setOverlayPainter(painter);

                if (!waypoints.isEmpty()) {
                    // Optionally center the map on the first restaurant
                    GeoPosition center = waypoints.iterator().next().getPosition();
                    mapViewer.setAddressLocation(center);
                    mapViewer.setZoom(5);
                    System.out.println("THERE EXISTS A WAYPOINT");
                }

                mapViewer.repaint();
            }


        }
    }

    public String getSearchName() {
        return SearchName;
    }

    public void setSearchLocationsController(SearchLocationsNearbyController searchLocationsController) {
        this.searchLocationsController = searchLocationsController;
    }

    public void setLogoutController(LogoutController logoutController) {
        // TODO: save the logout controller in the instance variable.
    }
}
