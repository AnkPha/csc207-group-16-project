package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesState;
import interface_adapter.favorites_list.FavoritesViewModel;

public class FavoritesPanel extends JPanel {

    private static final int BORDER_PADDING = 15;
    private static final int SECTION_SPACING = 15;
    private static final int FIELD_HEIGHT = 30;
    private static final int LABEL_FONT_SIZE = 14;
    private static final int TITLE_FONT_SIZE = 18;
    private static final int SMALL_SPACING = 5;
    private static final int MEDIUM_SPACING = 10;
    private static final int ROW_COUNT = 5;
    private static final int BUTTON_PADDING_VERTICAL = 8;
    private static final int BUTTON_PADDING_HORIZONTAL = 20;
    private static final int LIST_ITEM_PADDING = 5;

    private static final Color BG_COLOR = new Color(245, 245, 245);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Color ADD_BUTTON_COLOR = new Color(59, 89, 182);
    private static final Color REMOVE_BUTTON_COLOR = new Color(205, 92, 92);

    private static final String FONT_FAMILY = "Arial";
    private static final String ERROR = "ERROR";

    private final FavoritesViewModel favoritesViewModel;
    private FavoritesController favoritesController;

    private final JTextArea restaurantNameArea = new JTextArea(1, 15);
    private final JButton addButton = new JButton("Add to Favorites");
    private final JButton removeButton = new JButton("Remove Selected");
    private final DefaultListModel<String> favoritesListModel = new DefaultListModel<>();
    private final JList<String> favoritesList = new JList<>(favoritesListModel);

    FavoritesPanel(FavoritesViewModel favoritesViewModel) {
        this.favoritesViewModel = favoritesViewModel;

        configureMainPanel();

        addTitleLabel();

        addRestaurantInputSection();

        addFavoritesListSection();

        addRemoveButton();

        setupEventHandlers();
    }

    private void configureMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING, BORDER_PADDING));
        this.setBackground(BG_COLOR);
    }

    private void addTitleLabel() {
        final JLabel titleLabel = new JLabel("Your Favorite Restaurants");
        titleLabel.setFont(new Font(FONT_FAMILY, Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, BORDER_PADDING, 0));
        this.add(titleLabel);
    }

    private void addRestaurantInputSection() {
        final JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.setBackground(BG_COLOR);
        addPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel nameLabel = new JLabel("Restaurant Name:");
        nameLabel.setFont(new Font(FONT_FAMILY, Font.PLAIN, LABEL_FONT_SIZE));
        addPanel.add(nameLabel);
        addPanel.add(Box.createRigidArea(new Dimension(0, SMALL_SPACING)));

        configureRestaurantNameArea();
        final JScrollPane nameScrollPane = new JScrollPane(restaurantNameArea);
        nameScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        nameScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        addPanel.add(nameScrollPane);
        addPanel.add(Box.createRigidArea(new Dimension(0, MEDIUM_SPACING)));

        styleButton(addButton, ADD_BUTTON_COLOR);
        final JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(BG_COLOR);
        styleButton(addButton, ADD_BUTTON_COLOR);
        buttonPanel.add(addButton);
        addPanel.add(buttonPanel);

        this.add(addPanel);
        this.add(Box.createRigidArea(new Dimension(0, SECTION_SPACING)));
    }

    private void configureRestaurantNameArea() {
        restaurantNameArea.setLineWrap(true);
        restaurantNameArea.setWrapStyleWord(true);
        restaurantNameArea.setRows(1);
        restaurantNameArea.setForeground(Color.BLACK);
        restaurantNameArea.setBackground(Color.WHITE);
        restaurantNameArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, LABEL_FONT_SIZE));
        restaurantNameArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
    }

    private void addFavoritesListSection() {
        final JLabel listLabel = new JLabel("Your Favorites:");
        listLabel.setFont(new Font(FONT_FAMILY, Font.BOLD, LABEL_FONT_SIZE));
        listLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(listLabel);
        this.add(Box.createRigidArea(new Dimension(0, SMALL_SPACING)));

        configureFavoritesList();
        final JScrollPane scrollPane = new JScrollPane(favoritesList);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0, MEDIUM_SPACING)));
    }

    private void configureFavoritesList() {
        favoritesList.setVisibleRowCount(ROW_COUNT);
        favoritesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        favoritesList.setCellRenderer(new FavoritesListRenderer());
        favoritesList.setBackground(Color.WHITE);
        favoritesList.setForeground(Color.BLACK);
        favoritesList.setFont(new Font(FONT_FAMILY, Font.PLAIN, LABEL_FONT_SIZE));
    }

    private void addRemoveButton() {
        styleButton(removeButton, REMOVE_BUTTON_COLOR);
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(removeButton);
    }

    private void setupEventHandlers() {
        addButton.addActionListener(evt -> handleAddFavorite());
        removeButton.addActionListener(evt -> handleRemoveFavorite());

        this.favoritesViewModel.addPropertyChangeListener(evt -> {
            final FavoritesState state = (FavoritesState) evt.getNewValue();
            updateFavoritesList(state.getFavoriteList());
            if (!state.getErrorMessage().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font(FONT_FAMILY, Font.BOLD, LABEL_FONT_SIZE));
        button.setBorder(BorderFactory.createEmptyBorder(BUTTON_PADDING_VERTICAL, BUTTON_PADDING_HORIZONTAL,
                BUTTON_PADDING_VERTICAL, BUTTON_PADDING_HORIZONTAL));
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private void handleAddFavorite() {
        if (favoritesController != null) {
            final String restaurantName = restaurantNameArea.getText().trim();
            if (!restaurantName.isEmpty()) {
                favoritesController.addToFavorites("currentUser", restaurantName);
                restaurantNameArea.setText("");
            }
            else {
                JOptionPane.showMessageDialog(this, "Please enter a restaurant name.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "System error. Please try again later.",
                    ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRemoveFavorite() {
        if (favoritesController != null) {
            final String selectedRestaurant = favoritesList.getSelectedValue();
            if (selectedRestaurant != null) {
                favoritesController.removeFromFavorites("currentUser", selectedRestaurant);
            }
            else {
                JOptionPane.showMessageDialog(this, "Please select a restaurant to remove.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "System error. Please try again later.",
                    ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFavoritesList(List<String> favorites) {
        favoritesListModel.clear();
        for (String restaurant : favorites) {
            favoritesListModel.addElement(restaurant);
        }
    }

    public void setFavoritesController(FavoritesController favoritesController) {
        this.favoritesController = favoritesController;
    }

    private static final class FavoritesListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);
            label.setBorder(BorderFactory.createEmptyBorder(LIST_ITEM_PADDING, LIST_ITEM_PADDING,
                    LIST_ITEM_PADDING, LIST_ITEM_PADDING));
            return label;
        }
    }
}
