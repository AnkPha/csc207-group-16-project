package Search;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import view.LabelTextPanel;

public class LeftPanel extends JPanel {
    public static final int ZOOM_7 = 7;
    public static final int HALF_2 = 2;
    public static final double DEFAULT_LAT = 43.6532;
    public static final double DEFAULT_LON = -79.3832;
    private JButton searchButton;
    private final JTextField searchInputField = new JTextField(15);
    private JPanel searchControls;
    private final JLabel searchErrorField = new JLabel();
    private final JXMapViewer mapViewer = new JXMapViewer();

    private final JTextField radiusInputField = new JTextField(15);

    private final SearchPanel searchPanel;

    LeftPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
        setSize(new Dimension(this.getWidth() / HALF_2,
                this.getHeight() / HALF_2));
        mapViewer.setPreferredSize(new Dimension(this.getWidth() / HALF_2,
                this.getHeight() / HALF_2));
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
        mapViewer.setZoom(ZOOM_7);

        // Default center: Toronto
        mapViewer.setAddressLocation(new GeoPosition(DEFAULT_LAT, DEFAULT_LON));
        final JLabel title = new JLabel("Search Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label and input field for searching
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
        // leftPanel = new JPanel();
        // leftPanel.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        // Search fields and labels
        searchControls = new JPanel();
        searchControls.setLayout(new BoxLayout(searchControls, BoxLayout.Y_AXIS));
        searchControls.add(searchInfo);
        searchControls.add(searchErrorField);
        searchControls.add(radiusInfo);
        searchControls.add(buttons);

        add(mapViewer, BorderLayout.CENTER);
        add(searchControls, BorderLayout.SOUTH);
        searchButton.addActionListener(evt -> {
            final String address = searchInputField.getText();
            final String radiusText = radiusInputField.getText();
            searchPanel.handleSearch(address, radiusText);
        });
    }

    /**
     * Returns the map viewer.
     * @return the map viewer
     */
    public JXMapViewer getMapViewer() {
        return this.mapViewer;
    }
}
