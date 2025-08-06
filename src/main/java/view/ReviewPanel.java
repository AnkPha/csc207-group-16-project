package view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Restaurant;
import entity.User;
import interface_adapter.review.ReviewController;
import interface_adapter.review.ReviewState;
import interface_adapter.review.ReviewViewModel;

class ReviewPanel extends JPanel implements PropertyChangeListener {
    private final ReviewViewModel viewModel;
    private ReviewController addReviewController;

    private final JComboBox<Integer> ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JButton submitReviewButton = new JButton("Submit Review");

    private Restaurant currentRestaurant;
    private User currentUser;

    ReviewPanel(ReviewViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Rate this Restaurant"));

        this.add(new JLabel("Rating (1-5 stars):"));
        this.add(ratingComboBox);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(submitReviewButton);

        ratingComboBox.addActionListener(evt -> {
            // Rating is selected from combo box, no need to track in state
            // unless you want to store it for some reason
        });

        submitReviewButton.addActionListener(evt -> {
            if (addReviewController != null && currentRestaurant != null && currentUser != null) {
                final int rating = (Integer) ratingComboBox.getSelectedItem();
                addReviewController.execute(rating, currentRestaurant, currentUser);
            }
            else if (currentRestaurant == null) {
                JOptionPane.showMessageDialog(this, "No restaurant selected.");
            }
            else if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "No user logged in.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Review controller not set.");
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final ReviewState state = (ReviewState) evt.getNewValue();

            if (state.isSuccess()) {
                JOptionPane.showMessageDialog(this, state.getSuccessMessage());
                // Reset the rating selection after successful submission
                ratingComboBox.setSelectedIndex(0);
            }
            else if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, "Error: " + state.getErrorMessage());
            }
        }
    }

    public void setAddReviewController(ReviewController addReviewController) {
        this.addReviewController = addReviewController;
    }

    public void setCurrentRestaurant(Restaurant restaurant) {
        this.currentRestaurant = restaurant;
        // Update UI to show which restaurant is being reviewed
        if (restaurant != null) {
            // You could add a label to display the restaurant name
            // For now, just enable/disable the submit button
            submitReviewButton.setEnabled(true);
        }
        else {
            submitReviewButton.setEnabled(false);
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
