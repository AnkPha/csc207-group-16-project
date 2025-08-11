package view;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import entity.Restaurant;
import interface_adapter.CurrentViewState;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchState;
import interface_adapter.search_nearby_locations.SearchViewModel;

/**
 * The View for when the user is searching and filtering places.
 */
public class SearchPanel extends JPanel implements PropertyChangeListener {

    public static final double RESIZE_HALF = 0.5;
    public static final int RADIUS_100 = 100;
    public static final int RADIUS_300 = 300;
    public static final int RADIUS_1000 = 1000;
    public static final int RADIUS_3000 = 3000;
    public static final int RADIUS_10000 = 10000;
    public static final int RADIUS_25000 = 25000;
    public static final int RADIUS_100000 = 100000;
    public static final int RADIUS_500000 = 500000;

    public static final int ZOOM_0 = 0;
    public static final int ZOOM_2 = 2;
    public static final int ZOOM_3 = 3;
    public static final int ZOOM_4 = 4;
    public static final int ZOOM_5 = 5;
    public static final int ZOOM_6 = 6;
    public static final int ZOOM_7 = 7;
    public static final int ZOOM_8 = 8;
    public static final int ZOOM_9 = 9;

    public static final int NO_RESULTS = 1;
    public static final int FAILED_AT_CALL = 0;
    public static final int TIME_OUT = 4;
    private static final int NOT_WHOLE_LENGTH = 3;
    private static final int DECIMAL_VALE_INDEX = 2;
    private static final char VALUE_OF_DECIMAL_WHOLE = '0';
    private static final int DECIMAL_START_SLICE = 0;
    private static final int DECIMAL_END_SLICE = 1;

    private final SearchViewModel searchViewModel;
    private final FilterViewModel filterViewModel;

    private SearchLocationsNearbyController searchLocationsController;
    private FilterController filteringController;

    private int zoom;

    private SearchState currentState;

    private final CurrentViewState currentViewState = new CurrentViewState();

    private RightPanel rightPanel;

    private LeftPanel leftPanel;

    private Set<Waypoint> waypoints;

    public SearchPanel(SearchViewModel searchViewModel, FilterViewModel filterViewModel) {

        searchViewModel.addPropertyChangeListener(this);
        filterViewModel.addPropertyChangeListener(this);

        currentViewState.setFilterState(filterViewModel.getState());
        currentViewState.setSearchState(searchViewModel.getState());

        setUpScreen();
        this.filterViewModel = filterViewModel;
        this.filterViewModel.addPropertyChangeListener(this);
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);
    }

    /**
     * Sets up the main Screen.
     */
    public void setUpScreen() {

        leftPanel = new LeftPanel(this);
        rightPanel = new RightPanel(this);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);

        splitPane.setResizeWeight(RESIZE_HALF);
        splitPane.setDividerLocation(RESIZE_HALF);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent event) {
                splitPane.setDividerLocation(RESIZE_HALF);
                rightPanel.setDividerLocation(RESIZE_HALF);
            }
        });
    }

    /**
     * A method that sets the zoom according to the radius.
     *
     * @param radius the radius in meters
     */
    public void getZoom(int radius) {
        if (radius <= RADIUS_100) {
            zoom = ZOOM_0;
        }
        else if (radius <= RADIUS_300) {
            zoom = ZOOM_2;
        }
        else if (radius <= RADIUS_1000) {
            zoom = ZOOM_3;
        }
        else if (radius <= RADIUS_3000) {
            zoom = ZOOM_4;
        }
        else if (radius <= RADIUS_10000) {
            zoom = ZOOM_5;
        }
        else if (radius <= RADIUS_25000) {
            zoom = ZOOM_6;
        }
        else if (radius <= RADIUS_100000) {
            zoom = ZOOM_7;
        }
        else if (radius <= RADIUS_500000) {
            zoom = ZOOM_8;
        }
        else {
            zoom = ZOOM_9;
        }
    }

    /**
     * A method that functions as the filter logic.
     *
     * @param selectedCuisines the selected cuisines;
     * @param selectedRating   the selected rating
     * @param selectedVegStat  the selected veg stat
     * @param selectedHour     the selected hour
     */
    public void handleFilter(List<String> selectedCuisines,
                             String selectedRating,
                             String selectedVegStat,
                             String selectedHour) {
        currentState = searchViewModel.getState();
        if (currentState.getAddress() == null || currentState.getAddress().trim().isEmpty()
                || currentState.getRadius() == null || currentState.getRadius().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out address and radius first");
        }

        currentState.setFiltered(true);
        filteringController.execute(currentState.getAddress(),
                Integer.parseInt(currentState.getRadius()),
                selectedCuisines,
                selectedVegStat,
                selectedHour,
                selectedRating);
        currentViewState.setFilterState(filterViewModel.getState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        rightPanel.getInfoPanel().removeAll();
        currentViewState.setFilterState(filterViewModel.getState());
        currentViewState.setSearchState(searchViewModel.getState());

        rightPanel.updateUi(currentViewState);

        if (evt.getPropertyName().equals("state")) {
            if (currentViewState.getStatus() == FAILED_AT_CALL) {
                JOptionPane.showMessageDialog(this, "Problem with API call try again later");
            }
            else if (currentViewState.getStatus() == NO_RESULTS) {
                JOptionPane.showMessageDialog(this, "Could Not Find Address");
            }
            else if (currentViewState.getStatus() == TIME_OUT) {
                JOptionPane.showMessageDialog(this, "API Timed Out due to traffic, try again");
            }
            else if (currentViewState.getActiveRestaurants().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Restaurants found");
            }
            else {
                leftPanel.getMapViewer().setOverlayPainter(null);
                waypoints = new HashSet<>();
                updateInfoPanelAndWaypoints();
            }
            final WaypointPainter<Waypoint> painter = new WaypointPainter<>();
            painter.setWaypoints(waypoints);
            leftPanel.getMapViewer().setOverlayPainter(painter);
            if (!waypoints.isEmpty()) {
                final GeoPosition center = new GeoPosition(
                        currentViewState.getSearchState().getAddressCoords()[0],
                        currentViewState.getSearchState().getAddressCoords()[1]);
                leftPanel.getMapViewer().setAddressLocation(center);
                leftPanel.getMapViewer().setZoom(zoom);
            }
            leftPanel.getMapViewer().repaint();
        }
    }

    /**
     * A method that updates the infoPanel visual.
     */
    public void updateInfoPanelAndWaypoints() {
        for (Restaurant r : currentViewState.getActiveRestaurants()) {
            if (r.getLat() != 0.0 && r.getLon() != 0.0) {
                final GeoPosition pos = new GeoPosition(r.getLat(), r.getLon());
                waypoints.add(new DefaultWaypoint(pos));
                final JLabel nameLabel = new JLabel("Name: " + r.getName());
                final JLabel cuisineLabel = new JLabel("Cuisine: " + r.getCuisine());
                final JLabel websiteLabel = new JLabel("Website: " + r.getWebsite());
                final JLabel hoursLabel = new JLabel("Opening Hours: " + r.getOpeningHours());
                String ratingString = r.getRating();
                if (!r.getRating().equals("No Ratings")) {
                    if (ratingString.length() == NOT_WHOLE_LENGTH &&
                            ratingString.charAt(DECIMAL_VALE_INDEX) == VALUE_OF_DECIMAL_WHOLE) {
                        ratingString = r.getRating().substring(DECIMAL_START_SLICE, DECIMAL_END_SLICE);
                    }
                    ratingString += "/5";
                }
                final JLabel ratingLabel = new JLabel("Rating: " + ratingString);
                rightPanel.addToInfoPanel(nameLabel, cuisineLabel, websiteLabel, hoursLabel, ratingLabel);
            }
        }
    }

    /**
     * A method that handles the search once the search button is clicked.
     *
     * @param address    the address
     * @param radiusText the radius in String format
     */
    public void handleSearch(String address, String radiusText) {
        currentState = searchViewModel.getState();
        currentState.setFiltered(false);
        currentViewState.setSearchState(currentState);
        if (address.isEmpty() || radiusText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address or Radius cannot be empty.");
        }

        try {
            final int radius = Integer.parseInt(radiusText);
            currentState.setAddress(address);
            currentState.setRadius(radiusText);
            getZoom(radius);
            searchLocationsController.execute(address, radius);
            currentState = searchViewModel.getState();
            currentState.setRadius(radiusText);
            currentState.setAddress(address);
            currentViewState.setSearchState(currentState);
        }
        catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Radius must be a valid number.");
        }
        rightPanel.getInfoPanel().revalidate();
        rightPanel.getInfoPanel().repaint();
    }

    public void setSearchLocationsController(SearchLocationsNearbyController searchLocationsController) {
        this.searchLocationsController = searchLocationsController;
    }

    public void setFilteringController(FilterController filteringController) {
        this.filteringController = filteringController;
    }
}
