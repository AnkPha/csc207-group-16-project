package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
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
    private JButton searchButton;

    private final JXMapViewer mapViewer = new JXMapViewer();

    private final JPanel infoPanel = new JPanel();

    private SearchState currentState;
    private final JList<String> cuisineList;
    private final JComboBox<String> ratingComboBox;
    private final JComboBox<String> vegStatComboBox;
    private final JComboBox<String> hourComboBox;

    private String address;
    private String radiusText;
    private final CurrentViewState currentViewState = new CurrentViewState();

    private int zoom;

    public SearchPanel(SearchViewModel searchViewModel, FilterViewModel filterViewModel) {
        this.filterViewModel = filterViewModel;
        this.searchViewModel = searchViewModel;

        searchViewModel.addPropertyChangeListener(this);
        filterViewModel.addPropertyChangeListener(this);

        currentViewState.setFilterState(filterViewModel.getState());
        currentViewState.setSearchState(searchViewModel.getState());

        // Map View Initialization
        final JPanel leftPanel = initMapView();

        // Filter View Initialization
        JButton filterButton = new JButton("Filter");
        JPanel filterPanel = new JPanel(new FlowLayout());

        cuisineList = new JList<>(new DefaultListModel<>());
        cuisineList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane cuisineScrollPane = new JScrollPane(cuisineList);
        cuisineScrollPane.setPreferredSize(new Dimension(120, 80));

        ratingComboBox = new JComboBox<>();
        ratingComboBox.setEditable(false);
        ratingComboBox.addItem("None");

        vegStatComboBox = new JComboBox<>();
        vegStatComboBox.setEditable(false);
        vegStatComboBox.addItem("None");

        hourComboBox = new JComboBox<>();
        hourComboBox.setEditable(false);
        hourComboBox.addItem("None");
        hourComboBox.addItem("Open Now");
        hourComboBox.addItem("Closed Now");

        filterPanel.add(new JLabel("Cuisine:"));
        filterPanel.add(cuisineScrollPane);
        filterPanel.add(new JLabel("Rating:"));
        filterPanel.add(ratingComboBox);
        filterPanel.add(new JLabel("Vegetarian Status:"));
        filterPanel.add(vegStatComboBox);
        filterPanel.add(new JLabel("Opening Hour:"));
        filterPanel.add(hourComboBox);
        filterPanel.add(filterButton);

        JScrollPane filterScrollPane = new JScrollPane(filterPanel);
        filterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        filterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel rightPanel = new JPanel();
        JLabel infoPanelTitle = new JLabel("Restaurant Info");
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(infoPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(infoPanelTitle);
        rightPanel.add(scrollPane);
        rightPanel.add(filterScrollPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);

        operationalizeFilterButton(filterButton);
        operationalizeSearchButton(searchButton);
    }

    private JPanel initMapView() {
        mapViewer.setPreferredSize(new Dimension(800, 600));
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(new GeoPosition(43.6532, -79.3832)); // Toronto

        final JLabel title = new JLabel("Search Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel searchInfo = new LabelTextPanel(new JLabel("Enter Address"), searchInputField);
        final LabelTextPanel radiusInfo = new LabelTextPanel(new JLabel("Enter Radius(In integer meters)"), radiusInputField);

        final JPanel buttons = new JPanel();
        searchButton = new JButton("Click To Search");
        buttons.add(searchButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);

        final JPanel leftPanel = new JPanel(new BorderLayout());

        final JPanel searchControls = new JPanel();
        searchControls.setLayout(new BoxLayout(searchControls, BoxLayout.Y_AXIS));
        searchControls.add(searchInfo);
        searchControls.add(searchErrorField);
        searchControls.add(radiusInfo);
        searchControls.add(buttons);

        leftPanel.add(mapViewer, BorderLayout.CENTER);
        leftPanel.add(searchControls, BorderLayout.SOUTH);

        return leftPanel;
    }

    private void operationalizeFilterButton(JButton filterButton) {
        filterButton.addActionListener(evt -> {
            System.out.println("Filter button clicked");
            currentState = searchViewModel.getState();

            if (currentState.getAddress() == null || currentState.getRadius() == null) {
                JOptionPane.showMessageDialog(this, "Please fill out address and radius first");
                return;
            }

            List<String> selectedCuisines = cuisineList.getSelectedValuesList();
            selectedCuisines.removeIf(s -> s.equalsIgnoreCase("None"));
            System.out.println("Selected cuisines: " + selectedCuisines);

            String selectedRating = "";
            if (ratingComboBox.getSelectedItem() != null) {
                selectedRating = ratingComboBox.getSelectedItem().toString();
                if (selectedRating.equalsIgnoreCase("None")) {
                    selectedRating = "";
                }
            }
            System.out.println("Selected rating: " + selectedRating);

            String selectedVegStat = "";
            if (vegStatComboBox.getSelectedItem() != null) {
                selectedVegStat = vegStatComboBox.getSelectedItem().toString();
                if (selectedVegStat.equalsIgnoreCase("None")) {
                    selectedVegStat = "";
                }
            }
            System.out.println("Selected vegStat: " + selectedVegStat);

            String selectedAvailability = "";
            if (hourComboBox.getSelectedItem() != null) {
                selectedAvailability = hourComboBox.getSelectedItem().toString();
                if (selectedAvailability.equalsIgnoreCase("None")) {
                    selectedAvailability = "";
                }
            }
            System.out.println("Selected availability: " + selectedAvailability);

            currentState.setFiltered(true);
            filteringController.execute(
                    currentState.getAddress(),
                    Integer.parseInt(currentState.getRadius()),
                    selectedCuisines,
                    selectedVegStat,
                    selectedAvailability,
                    selectedRating);
            currentViewState.setFilterState(filterViewModel.getState());
        });
    }

    private void operationalizeSearchButton(JButton searchButton) {
        searchButton.addActionListener(evt -> {
            if (evt.getSource().equals(searchButton)) {
                System.out.println("Search button clicked");
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
                        zoom = 0; // building level
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

                    System.out.println("Executing search for address: " + address + " with radius: " + radius);
                    searchLocationsController.execute(address, radius);
                    currentState = searchViewModel.getState();
                    currentState.setRadius(radiusText);
                    currentState.setAddress(address);
                    currentViewState.setSearchState(currentState);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Radius must be a valid number.");
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property changed event: " + evt.getPropertyName());
        infoPanel.removeAll();
        currentViewState.setFilterState(filterViewModel.getState());
        currentViewState.setSearchState(searchViewModel.getState());

        cuisineList.setModel(currentViewState.getCuisineOptions());
        ratingComboBox.setModel(currentViewState.getRatingOptions());
        vegStatComboBox.setModel(currentViewState.getVegStatOptions());
        hourComboBox.setModel(currentViewState.getHourOptions());

        if ("state".equals(evt.getPropertyName())) {
            if (currentViewState.getActiveRestaurants().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Restaurants found");
            } else {
                mapViewer.setOverlayPainter(null);
                Set<Waypoint> waypoints = new HashSet<>();

                for (Restaurant r : currentViewState.getActiveRestaurants()) {
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
                    }
                }

                WaypointPainter<Waypoint> painter = new WaypointPainter<>();
                painter.setWaypoints(waypoints);
                mapViewer.setOverlayPainter(painter);

                if (!waypoints.isEmpty()) {
                    GeoPosition center = new GeoPosition(
                            currentViewState.getSearchState().getAddressCoords()[0],
                            currentViewState.getSearchState().getAddressCoords()[1]);
                    mapViewer.setAddressLocation(center);
                    mapViewer.setZoom(zoom);
                }
                mapViewer.repaint();
            }
        }
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    public void setSearchLocationsController(SearchLocationsNearbyController searchLocationsController) {
        this.searchLocationsController = searchLocationsController;
    }

    public void setFilteringController(FilterController filteringController) {
        this.filteringController = filteringController;
    }
}
