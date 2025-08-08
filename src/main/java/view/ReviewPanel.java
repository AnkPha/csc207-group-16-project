package view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.Restaurant;
import entity.User;
import interface_adapter.review.ReviewController;
import interface_adapter.review.ReviewState;
import interface_adapter.review.ReviewViewModel;

class ReviewPanel extends JPanel implements PropertyChangeListener {
    private final ReviewViewModel viewModel;
    private ReviewController addReviewController;

    private final JTextField restaurantNameField = new JTextField(20);
    private final JComboBox<Integer> ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JTextArea reviewTextArea = new JTextArea(5, 20);
    private final JButton submitReviewButton = new JButton("Submit Review");

    private User currentUser;

    ReviewPanel(ReviewViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("Restaurant Name:"));
        this.add(restaurantNameField);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(new JLabel("Rating (1-5 stars):"));
        this.add(ratingComboBox);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(new JLabel("Write your review:"));
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(submitReviewButton);

        submitReviewButton.addActionListener(evt -> {
            if (addReviewController != null && currentUser != null) {
                final String restaurantName = restaurantNameField.getText().trim();
                final int ratingValue = (Integer) ratingComboBox.getSelectedItem();
                final String reviewText = reviewTextArea.getText().trim();

                if (restaurantName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a restaurant name.");
                    return;
                }
                if (reviewText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a review.");
                    return;
                }

                double[] coords = new double[]{0.0, 0.0};

                Restaurant restaurant = new Restaurant(
                        restaurantName,
                        "Unknown",
                        "Unknown",
                        "Unknown",
                        "Unknown",
                        "Unknown",
                        coords
                );

                addReviewController.execute(ratingValue, reviewText, restaurant, currentUser);
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
                restaurantNameField.setText("");
                ratingComboBox.setSelectedIndex(0);
                reviewTextArea.setText("");
            }
            else if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, "Error: " + state.getErrorMessage());
            }
        }
    }

    public void setAddReviewController(ReviewController addReviewController) {
        this.addReviewController = addReviewController;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
