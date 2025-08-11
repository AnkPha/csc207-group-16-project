package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entity.Restaurant;
import entity.User;
import interface_adapter.review.ReviewController;
import interface_adapter.review.ReviewState;
import interface_adapter.review.ReviewViewModel;

class ReviewPanel extends JPanel implements PropertyChangeListener {
    private static final String FONT = "Arial";
    private static final String ERROR = "Error";
    private static final String UNKNOWN = "Unknown";
    private final ReviewViewModel viewModel;
    private ReviewController addReviewController;
    private User currentUser;

    private final JTextField restaurantNameField = new JTextField(24);
    private final JComboBox<Integer> ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JTextArea reviewTextArea = new JTextArea(5, 20);
    private final JButton submitReviewButton = new JButton("Submit Review");

    private final int border = 15;
    private final int panelRigidAreaHeight = 5;
    private final int height = 10;
    private final int buttonRigidAreaHeight = 15;

    private final int color = 245;
    private final int panelColor = 200;
    private final int buttonColorR = 70;
    private final int buttonColorG = 130;
    private final int buttonColorB = 180;
    private final int scrollColor = 200;

    private final int titleFontSize = 18;
    private final int buttonFont = 14;
    private final int textFontSize = 14;

    private final int textAreaWidth = 300;
    private final int restaurantNameHeight = 30;
    private final int textAreaHeight = 120;
    private final int buttonSizeWidth = 200;
    private final int buttonSizeHeight = 40;
    private final int spacingHeight = 10;
    private final int buttonSpacing = 15;

    ReviewPanel(ReviewViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(border, border, border, border));
        this.setBackground(new Color(color, color, color));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        configureTextArea();

        final JLabel titleLabel = new JLabel("Write a Review");
        titleLabel.setFont(new Font(FONT, Font.BOLD, titleFontSize));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, border, 0));
        this.add(titleLabel);

        addCompactFormField("Restaurant Name:", restaurantNameField);

        addRatingSelector();

        addFormField("Your Review:", reviewTextArea, true);

        configureSubmitButton();
    }

    private void configureTextArea() {
        restaurantNameField.setForeground(Color.BLACK);
        restaurantNameField.setBackground(Color.WHITE);
        restaurantNameField.setFont(new Font(FONT, Font.PLAIN, textFontSize));
        restaurantNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, restaurantNameHeight));
        reviewTextArea.setForeground(Color.BLACK);
        reviewTextArea.setBackground(Color.WHITE);
        reviewTextArea.setFont(new Font(FONT, Font.PLAIN, textFontSize));
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
    }

    private void addRatingSelector() {
        final JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ratingPanel.setBackground(new Color(panelColor, panelColor, panelColor));
        ratingPanel.add(new JLabel("Rating:"));
        ratingComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value.toString() + " stars");
                return this;
            }
        });
        ratingPanel.add(ratingComboBox);
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ratingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, ratingPanel.getPreferredSize().height));
        this.add(ratingPanel);
        this.add(Box.createRigidArea(new Dimension(0, spacingHeight)));
    }

    private void configureSubmitButton() {
        submitReviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitReviewButton.setBackground(new Color(buttonColorR, buttonColorG, buttonColorB));
        submitReviewButton.setForeground(Color.BLACK);
        submitReviewButton.setFocusPainted(false);
        submitReviewButton.setFont(new Font(FONT, Font.BOLD, buttonFont));
        submitReviewButton.setMaximumSize(new Dimension(buttonSizeWidth, buttonSizeHeight));
        submitReviewButton.addActionListener(evt -> handleReviewSubmission());

        this.add(Box.createRigidArea(new Dimension(0, buttonSpacing)));
        this.add(submitReviewButton);
    }

    private void addCompactFormField(String labelText, JTextField field) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(color, color, color));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel label = new JLabel(labelText);
        label.setFont(new Font(FONT, Font.PLAIN, buttonFont));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, panelRigidAreaHeight)));

        field.setPreferredSize(new Dimension(textAreaWidth, restaurantNameHeight));
        field.setMinimumSize(new Dimension(textAreaWidth, restaurantNameHeight));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, restaurantNameHeight));
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(field);
        this.add(panel);
        this.add(Box.createRigidArea(new Dimension(0, height)));
    }

    private void addFormField(String labelText, JTextArea area, boolean isMultiLine) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(color, color, color));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel label = new JLabel(labelText);
        label.setFont(new Font(FONT, Font.PLAIN, buttonFont));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, panelRigidAreaHeight)));

        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setPreferredSize(new Dimension(textAreaWidth, textAreaHeight));
        area.setMinimumSize(new Dimension(textAreaWidth, textAreaHeight));
        area.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        final JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(textAreaWidth, textAreaHeight));
        scrollPane.setMinimumSize(new Dimension(textAreaWidth, textAreaHeight));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(scrollPane);
        this.add(panel);
        this.add(Box.createRigidArea(new Dimension(0, height)));
    }

    private void handleReviewSubmission() {
        String errorMessage = null;

        if (addReviewController != null && currentUser != null) {
            final String restaurantName = restaurantNameField.getText().trim();
            final int ratingValue = (Integer) ratingComboBox.getSelectedItem();
            final String reviewText = reviewTextArea.getText().trim();

            if (restaurantName.isEmpty()) {
                errorMessage = "Please enter a restaurant name.";
            }
            else if (reviewText.isEmpty()) {
                errorMessage = "Please enter a review.";
            }
            else {
                final double[] coords = {0.0, 0.0};
                final Restaurant restaurant = new Restaurant(
                        restaurantName,
                        UNKNOWN,
                        UNKNOWN,
                        UNKNOWN,
                        UNKNOWN,
                        UNKNOWN,
                        coords
                );

                addReviewController.execute(ratingValue, reviewText, restaurant, currentUser);
            }
        }
        else if (currentUser == null) {
            errorMessage = "Please log in to submit a review.";
        }
        else {
            errorMessage = "System error. Please try again later.";
        }

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, ERROR, JOptionPane.ERROR_MESSAGE);
        }
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

    private static final class StarRatingRenderer extends DefaultListCellRenderer {
        private final int maxRating = 5;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Integer) {
                final int rating = (Integer) value;
                setText(rating + " " + "\u2605".repeat(rating) + "\u2606".repeat(maxRating - rating));
            }
            return this;
        }
    }
}
