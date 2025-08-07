package view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesState;
import interface_adapter.favorites_list.FavoritesViewModel;

public class FavoritesPanel extends JPanel {

    private final FavoritesViewModel favoritesViewModel;
    private FavoritesController favoritesController;

    private final JTextField restaurantNameField = new JTextField(15);
    private final JButton addButton = new JButton("Add to Favorites");
    private final JButton removeButton = new JButton("Remove Selected");

    private final DefaultListModel<String> favoritesListModel = new DefaultListModel<>();
    private final JList<String> favoritesList = new JList<>(favoritesListModel);


    FavoritesPanel(FavoritesViewModel favoritesViewModel) {
        this.favoritesViewModel = favoritesViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Favorite Restaurants"));

        this.add(new JLabel("Restaurant Name: "));
        this.add(restaurantNameField);
        this.add(addButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add scrollable list of favorites
        favoritesList.setVisibleRowCount(5);
        this.add(new JLabel("Your Favorites:"));
        this.add(new JScrollPane(favoritesList));
        this.add(removeButton);

        addButton.addActionListener(evt -> {
            if (favoritesController != null) {
                String restaurantName = restaurantNameField.getText().trim();
                if (!restaurantName.isEmpty()) {
                    favoritesController.addToFavorites("currentUser", restaurantName);
                    restaurantNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a restaurant name.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Favorites controller not set.");
            }
        });

        removeButton.addActionListener(evt -> {
            if (favoritesController != null) {
                String selectedRestaurant = favoritesList.getSelectedValue();
                if (selectedRestaurant != null) {
                    favoritesController.removeFromFavorites("currentUser", selectedRestaurant);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a restaurant to remove.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Favorites controller not set.");
            }
        });

        // Listen to view model changes
        this.favoritesViewModel.addPropertyChangeListener(evt -> {
            final FavoritesState state = (FavoritesState) evt.getNewValue();
            updateFavoritesList(state.getFavoriteList());
            if (!state.getErrorMessage().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage());
            }
        });
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
}