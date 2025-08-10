package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import interface_adapter.CurrentViewState;

public class RightPanel extends JSplitPane {
    public static final int HALF_2 = 2;
    public static final int QUARTER_4 = 4;

    private final JPanel infoPanel = new JPanel();

    private final JButton filterButton;
    private final JPanel filterPanelTop;
    private final JPanel filterPanelBottom;
    private final JPanel filterPanel;

    private JList<String> cuisineList;
    private JComboBox<String> ratingComboBox;
    private JComboBox<String> vegStatComboBox;
    private JComboBox<String> hourComboBox;

    public RightPanel(SearchPanel searchPanel) {
        setSize(new Dimension(this.getWidth() / HALF_2, this.getHeight() / HALF_2));
        final JScrollPane scrollPane = new JScrollPane(infoPanel);
        // Right side: place info panel
        // Filter things
        filterButton = new JButton("Filter");
        filterPanelTop = new JPanel(new FlowLayout());
        filterPanelBottom = new JPanel(new FlowLayout());
        filterPanel = new JPanel(new FlowLayout());
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        setFilterPanelContents();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        scrollPane.setPreferredSize(new Dimension(
                searchPanel.getWidth() / HALF_2,
                searchPanel.getHeight() / QUARTER_4));

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setTopComponent(filterPanel);
        setBottomComponent(scrollPane);
        setOneTouchExpandable(true);
        filterButton.addActionListener(evt -> {
            final List<String> selectedCuisines = cuisineList.getSelectedValuesList();
            final String selectedRating = Objects.requireNonNull(ratingComboBox.getSelectedItem()).toString();
            final String selectedVegStat = Objects.requireNonNull(vegStatComboBox.getSelectedItem()).toString();
            final String selectedHour = Objects.requireNonNull(hourComboBox.getSelectedItem()).toString();
            searchPanel.handleFilter(selectedCuisines, selectedRating, selectedVegStat, selectedHour);
        });
    }

    /**
     * A method that sets up the filter panel's contents.
     */
    public void setFilterPanelContents() {
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

        filterPanelTop.add(new JLabel("Cuisine:"));
        filterPanelTop.add(cuisineScrollPane);
        filterPanelTop.add(new JLabel("Rating:"));
        filterPanelTop.add(ratingComboBox);
        filterPanelBottom.add(new JLabel("Vegetarian Status:"));
        filterPanelBottom.add(vegStatComboBox);
        filterPanelBottom.add(new JLabel("Opening Hour:"));
        filterPanelBottom.add(hourComboBox);

        filterPanel.add(filterPanelTop);
        filterPanel.add(filterPanelBottom);
        filterPanel.add(filterButton);
    }

    public JList<String> getCuisineList() {
        return this.cuisineList;
    }

    public JComboBox<String> getRatingComboBox() {
        return this.ratingComboBox;
    }

    public JComboBox<String> getVegStatComboBox() {
        return this.vegStatComboBox;
    }

    public JComboBox<String> getHourComboBox() {
        return this.hourComboBox;
    }

    public JPanel getInfoPanel() {
        return this.infoPanel;
    }

    /**
     * A method that updates the filter options.
     * @param currentViewState the current view state
     */
    public void updateUi(CurrentViewState currentViewState) {
        getCuisineList().removeAll();
        getCuisineList().setModel(currentViewState.getCuisineOptions());

        getRatingComboBox().removeAllItems();
        getRatingComboBox().setModel(currentViewState.getRatingOptions());

        getVegStatComboBox().removeAllItems();
        getVegStatComboBox().setModel(currentViewState.getVegStatOptions());

        getHourComboBox().removeAllItems();
        getHourComboBox().setModel(currentViewState.getHourOptions());
    }

    /**
     * A method that adds to the info panel display.
     * @param nameLabel the name of the place
     * @param cuisineLabel the type of cuisine
     * @param websiteLabel the website url
     * @param hoursLabel the hours of operation
     */
    public void addToInfoPanel(JLabel nameLabel,
                                JLabel cuisineLabel,
                                JLabel websiteLabel,
                                JLabel hoursLabel) {
        getInfoPanel().add(nameLabel);
        getInfoPanel().add(cuisineLabel);
        getInfoPanel().add(websiteLabel);
        getInfoPanel().add(hoursLabel);
        getInfoPanel().add(new JLabel("------"));
        getInfoPanel().revalidate();
        getInfoPanel().repaint();
    }
}
