package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entity.Review;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserState;
import interface_adapter.search_user.SearchUserViewModel;
import interface_adapter.send_friend_request.SendFriendRequestController;
import interface_adapter.send_friend_request.SendFriendRequestState;
import interface_adapter.send_friend_request.SendFriendRequestViewModel;

public class FriendsPanel extends JPanel implements PropertyChangeListener {
    private static final String ERROR_TEXT = "Error";
    private static final int FIVE = 5;
    private static final int TEN = 10;
    private static final int SEVENTY = 70;
    private static final int HUNDRED_THIRTY = 130;
    private static final int HUNDRED_EIGHTY = 180;
    private static final float FONT = 14f;

    private final JTextField searchField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");

    private final DefaultListModel<String> friendListModel = new DefaultListModel<>();
    private final JList<String> friendsList = new JList<>(friendListModel);

    private final DefaultListModel<String> requestListModel = new DefaultListModel<>();
    private final JList<String> requestList = new JList<>(requestListModel);

    private final JPanel searchResultPanel = new JPanel();
    private final JScrollPane searchResultScrollPane;

    private SearchUserController searchUserController;
    private SearchUserViewModel searchUserViewModel;
    private SendFriendRequestController sendFriendRequestController;
    private SendFriendRequestViewModel sendFriendRequestViewModel;

    private String currentUsername;

    public FriendsPanel() {
        this.setLayout(new BorderLayout());

        final JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Find Friends:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        this.add(topPanel, BorderLayout.NORTH);

        final JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(new JScrollPane(friendsList));
        centerPanel.add(new JScrollPane(requestList));
        this.add(centerPanel, BorderLayout.CENTER);

        searchResultPanel.setLayout(new BoxLayout(searchResultPanel, BoxLayout.Y_AXIS));
        searchResultPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
        searchResultScrollPane = new JScrollPane(searchResultPanel);
        searchResultScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(searchResultScrollPane, BorderLayout.SOUTH);

        friendsList.setBorder(BorderFactory.createTitledBorder("Your Friends"));
        requestList.setBorder(BorderFactory.createTitledBorder("Friend Requests"));

        searchButton.addActionListener(evt -> {
            if (searchUserController != null) {
                final String query = searchField.getText();
                searchUserController.execute(query);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()) && searchUserViewModel != null) {
            final SearchUserState state = searchUserViewModel.getState();
            displaySearchResults(state.getUsernames(), state.getUserReviews());
        }

        if ("friendRequestResult".equals(evt.getPropertyName()) && sendFriendRequestViewModel != null) {
            final SendFriendRequestState state = sendFriendRequestViewModel.getState();
            if (state.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                        state.getMessage(),
                        "Request Sent",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Failed to send friend request: " + state.getMessage(),
                        ERROR_TEXT,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Sets the SearchUserViewModel for this component and manages its property change listener.
     * If an existing view model is already set, this method removes this object as a listener from it
     * before assigning the new view model. It then registers this object as a listener to the newly
     * assigned view model, if it is not then null.
     *
     * @param searchUserViewModel the new {@code SearchUserViewModel} to associate with this component
     */
    public void setSearchUserViewModel(SearchUserViewModel searchUserViewModel) {
        if (this.searchUserViewModel != null) {
            this.searchUserViewModel.removePropertyChangeListener(this);
        }

        this.searchUserViewModel = searchUserViewModel;
        if (this.searchUserViewModel != null) {
            this.searchUserViewModel.addPropertyChangeListener(this);
        }
    }

    /**
     * Updates the displayed list of friends in the UI.
     *
     * @param friends a list of usernames representing the user's current friends
     */
    public void updateFriendList(List<String> friends) {
        friendListModel.clear();
        for (String friend : friends) {
            friendListModel.addElement(friend);
        }
    }

    /**
     * Updates the displayed list of incoming friend requests in the UI.
     *
     * @param requests a list of usernames representing users who have sent friend requests
     */
    public void updateFriendRequests(List<String> requests) {
        requestListModel.clear();
        for (String request : requests) {
            requestListModel.addElement(request);
        }
    }

    /**
     * Displays the result of a user search, including each user's name and their reviews.
     *
     * @param usernames a list of usernames that matched the search query
     * @param userReviews a map from usernames to a list of their reviews; users with no reviews
     *                    will either have an empty list or a null value
     */
    public void displaySearchResults(List<String> usernames, Map<String, List<Review>> userReviews) {
        SwingUtilities.invokeLater(() -> updateSearchResultsUi(usernames, userReviews));
    }

    /**
     * Updates the search results UI with the provided user data.
     *
     * @param usernames a list of usernames that matched the search query
     * @param userReviews a map from usernames to a list of their reviews
     */
    private void updateSearchResultsUi(List<String> usernames, Map<String, List<Review>> userReviews) {
        searchResultPanel.removeAll();

        if (usernames.isEmpty()) {
            addNoResultsLabel();
        }
        else {
            addUserPanels(usernames, userReviews);
        }

        refreshPanels();
    }

    /**
     * Adds a "No users found" label to the search results panel.
     */
    private void addNoResultsLabel() {
        final JLabel noResultsLabel = new JLabel("No users found");
        noResultsLabel.setForeground(Color.GRAY);
        searchResultPanel.add(noResultsLabel);
    }

    /**
     * Adds user panels to the search results for each username.
     *
     * @param usernames the list of usernames to display
     * @param userReviews the map of user reviews
     */
    private void addUserPanels(List<String> usernames, Map<String, List<Review>> userReviews) {
        for (String username : usernames) {
            final JPanel userPanel = createUserPanel(username, userReviews.get(username));
            searchResultPanel.add(userPanel);
        }
    }

    /**
     * Refreshes the search results panel and parent container.
     */
    private void refreshPanels() {
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    /**
     * Creates a panel for displaying a single user with their reviews and action buttons.
     *
     * @param username the username to display
     * @param reviews the user's reviews, or null if none
     * @return a JPanel containing the user information and buttons
     */
    private JPanel createUserPanel(String username, List<Review> reviews) {
        final JPanel userPanel = createBaseUserPanel();

        final JLabel usernameLabel = createUsernameLabel(username);
        userPanel.add(usernameLabel, BorderLayout.NORTH);

        final JPanel reviewsPanel = createReviewsPanel(reviews);
        userPanel.add(reviewsPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = createButtonPanel(username);
        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        return userPanel;
    }

    /**
     * Creates the base user panel with styling and layout.
     *
     * @return a styled JPanel with BorderLayout
     */
    private JPanel createBaseUserPanel() {
        final JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout(FIVE, FIVE));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(TEN, TEN, TEN, TEN)
        ));
        userPanel.setBackground(Color.WHITE);
        return userPanel;
    }

    /**
     * Creates a styled username label.
     *
     * @param username the username to display
     * @return a styled JLabel
     */
    private JLabel createUsernameLabel(String username) {
        final JLabel usernameLabel = new JLabel("User: " + username);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD, FONT));
        return usernameLabel;
    }

    /**
     * Creates a panel containing the user's reviews.
     *
     * @param reviews the user's reviews, or null if none
     * @return a JPanel containing review information
     */
    private JPanel createReviewsPanel(List<Review> reviews) {
        final JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setBackground(Color.WHITE);

        if (reviews != null && !reviews.isEmpty()) {
            addReviewsToPanel(reviewsPanel, reviews);
        }
        else {
            addNoReviewsLabel(reviewsPanel);
        }

        return reviewsPanel;
    }

    /**
     * Adds individual review panels to the reviews container.
     *
     * @param reviewsPanel the container panel
     * @param reviews the list of reviews to add
     */
    private void addReviewsToPanel(JPanel reviewsPanel, List<Review> reviews) {
        for (Review review : reviews) {
            addSingleReview(reviewsPanel, review);
        }
    }

    /**
     * Adds a single review to the reviews panel.
     *
     * @param reviewsPanel the container panel
     * @param review the review to add
     */
    private void addSingleReview(JPanel reviewsPanel, Review review) {
        final JPanel reviewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        reviewPanel.setBackground(Color.WHITE);

        final JLabel restaurantLabel = new JLabel("  - " + review.getRestaurant().getName()
                + " | " + review.getRating() + "/5");
        restaurantLabel.setFont(restaurantLabel.getFont().deriveFont(Font.BOLD));

        reviewPanel.add(restaurantLabel);
        reviewsPanel.add(reviewPanel);

        final String reviewText = review.getReviewText();
        if (reviewText != null && !reviewText.isEmpty()) {
            addReviewText(reviewsPanel, reviewText);
        }
    }

    /**
     * Adds review text to the reviews panel.
     *
     * @param reviewsPanel the container panel
     * @param reviewText the review text to add
     */
    private void addReviewText(JPanel reviewsPanel, String reviewText) {
        final JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textPanel.setBackground(Color.WHITE);
        final JLabel reviewTextLabel = new JLabel(" - " + reviewText);
        reviewTextLabel.setForeground(Color.DARK_GRAY);
        textPanel.add(reviewTextLabel);
        reviewsPanel.add(textPanel);
    }

    /**
     * Adds a "No reviews" label to the reviews panel.
     *
     * @param reviewsPanel the container panel
     */
    private void addNoReviewsLabel(JPanel reviewsPanel) {
        final JLabel noReviewsLabel = new JLabel("  (No reviews)");
        noReviewsLabel.setForeground(Color.GRAY);
        reviewsPanel.add(noReviewsLabel);
    }

    /**
     * Creates a panel with action buttons for the user.
     *
     * @param username the username for the buttons
     * @return a JPanel containing action buttons
     */
    private JPanel createButtonPanel(String username) {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        final JButton sendRequestButton = createSendRequestButton(username);
        buttonPanel.add(sendRequestButton);

        final JButton viewProfileButton = createViewProfileButton(username);
        buttonPanel.add(viewProfileButton);

        return buttonPanel;
    }

    /**
     * Creates a styled "Send Friend Request" button.
     *
     * @param username the username for the button action
     * @return a styled JButton
     */
    private JButton createSendRequestButton(String username) {
        final JButton sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.setBackground(new Color(SEVENTY, HUNDRED_THIRTY, HUNDRED_EIGHTY));
        sendRequestButton.setForeground(Color.WHITE);
        sendRequestButton.setFont(sendRequestButton.getFont().deriveFont(Font.BOLD));
        sendRequestButton.addActionListener(evt -> sendFriendRequest(username));
        return sendRequestButton;
    }

    /**
     * Creates a "View Profile" button.
     *
     * @param username the username for the button action
     * @return a JButton
     */
    private JButton createViewProfileButton(String username) {
        final JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(evt -> handleViewProfile(username));
        return viewProfileButton;
    }

    /**
     * Handles the view profile action for a specific user.
     *
     * @param username the username whose profile to view
     */
    private void handleViewProfile(String username) {
        SearchUserState state = null;
        if (searchUserViewModel != null) {
            state = searchUserViewModel.getState();
        }

        List<Review> reviews = null;
        if (state != null) {
            reviews = state.getUserReviews().get(username);
        }

        viewUserProfile(username, reviews);
    }

    /**
     * Shows an error message dialog.
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, ERROR_TEXT, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Validates preconditions for sending a friend request.
     *
     * @param recipientUsername the username to send the request to
     * @return an error message if validation fails, null if validation passes
     */
    private String validateFriendRequestInput(String recipientUsername) {
        String errorMessage = null;
        if (currentUsername.equals(recipientUsername)) {
            errorMessage = "You cannot send a friend request to yourself!";
        }
        return errorMessage;
    }

    /**
     * Sends a friend request to the specified user.
     *
     * @param recipientUsername the username to send the friend request to
     */
    private void sendFriendRequest(String recipientUsername) {
        final String validationError = validateFriendRequestInput(recipientUsername);
        if (validationError != null) {
            if (currentUsername != null && currentUsername.equals(recipientUsername)) {
                JOptionPane.showMessageDialog(this,
                        validationError,
                        ERROR_TEXT,
                        JOptionPane.WARNING_MESSAGE);
            }
            else {
                showError(validationError);
            }
        }
        else {
            executeFriendRequest(recipientUsername);
        }
    }

    /**
     * Executes the friend request after validation passes.
     *
     * @param recipientUsername the username to send the friend request to
     */
    private void executeFriendRequest(String recipientUsername) {
        try {
            sendFriendRequestController.execute(currentUsername, recipientUsername);
            JOptionPane.showMessageDialog(this,
                    "Friend request sent to " + recipientUsername + "!",
                    "Request Sent",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IllegalArgumentException | IllegalStateException ex) {
            ex.printStackTrace();
            showError("Failed to send friend request: " + ex.getMessage());
        }
    }

    /**
     * Displays a user's profile information in a dialog.
     *
     * @param username the username whose profile to display
     * @param reviews the user's reviews
     */
    private void viewUserProfile(String username, List<Review> reviews) {
        final StringBuilder profileInfo = new StringBuilder();
        profileInfo.append("Username: ").append(username).append("\n\n");

        if (reviews != null && !reviews.isEmpty()) {
            addReviewsToProfile(profileInfo, reviews);
        }
        else {
            profileInfo.append("No reviews yet.");
        }

        JOptionPane.showMessageDialog(this,
                profileInfo.toString(),
                "Profile: " + username,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Adds review information to the profile string builder.
     *
     * @param profileInfo the StringBuilder to append to
     * @param reviews the list of reviews to add
     */
    private void addReviewsToProfile(StringBuilder profileInfo, List<Review> reviews) {
        profileInfo.append("Reviews (").append(reviews.size()).append("):\n\n");
        for (Review review : reviews) {
            profileInfo.append("- ").append(review.getRestaurant().getName()).append(" (")
                    .append(review.getRating()).append("/5)\n");

            final String reviewText = review.getReviewText();
            if (reviewText != null && !reviewText.isEmpty()) {
                profileInfo.append("  \"").append(reviewText).append("\"\n");
            }
            profileInfo.append("\n");
        }
    }

    public void setSearchUserController(SearchUserController controller) {
        this.searchUserController = controller;
    }

    /**
     * Sets the controller for sending friend requests.
     *
     * @param controller the SendFriendRequestController
     */
    public void setSendFriendRequestController(SendFriendRequestController controller) {
        this.sendFriendRequestController = controller;
    }

    /**
     * Sets this component and manages the associated property change listener.
     * If a previous view model exists, its listener is removed before
     * attaching this object as a listener to the new view model.
     *
     * @param viewModel the new {@code SendFriendRequestViewModel} to associate with this component
     */
    public void setSendFriendRequestViewModel(SendFriendRequestViewModel viewModel) {
        if (this.sendFriendRequestViewModel != null) {
            this.sendFriendRequestViewModel.removePropertyChangeListener(this);
        }

        this.sendFriendRequestViewModel = viewModel;
        if (this.sendFriendRequestViewModel != null) {
            this.sendFriendRequestViewModel.addPropertyChangeListener(this);
        }
    }

    /**
     * Sets the current logged-in username.
     *
     * @param username the current user's username
     */
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    /**
     * Gets the current logged-in username.
     *
     * @return the current user's username
     */
    public String getCurrentUsername() {
        return this.currentUsername;
    }
}
