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
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
        SwingUtilities.invokeLater(() -> {
            searchResultPanel.removeAll();

            if (usernames.isEmpty()) {
                final JLabel noResultsLabel = new JLabel("No users found");
                noResultsLabel.setForeground(Color.GRAY);
                searchResultPanel.add(noResultsLabel);
            }
            else {
                for (String username : usernames) {
                    final JPanel userPanel = createUserPanel(username, userReviews.get(username));
                    searchResultPanel.add(userPanel);
                }
            }
            searchResultPanel.revalidate();
            searchResultPanel.repaint();
            this.revalidate();
            this.repaint();
        });
    }

    /**
     * Creates a panel for displaying a single user with their reviews and action buttons.
     *
     * @param username the username to display
     * @param reviews the user's reviews, or null if none
     * @return a JPanel containing the user information and buttons
     */
    private JPanel createUserPanel(String username, List<Review> reviews) {
        final JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout(5, 5));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        userPanel.setBackground(Color.WHITE);

        final JLabel usernameLabel = new JLabel("User: " + username);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD, 14f));
        userPanel.add(usernameLabel, BorderLayout.NORTH);

        final JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setBackground(Color.WHITE);

        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
                final JPanel reviewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
                reviewPanel.setBackground(Color.WHITE);

                final JLabel restaurantLabel = new JLabel("  - " + review.getRestaurant().getName()
                        + " | " + review.getRating() + "/5");
                restaurantLabel.setFont(restaurantLabel.getFont().deriveFont(Font.BOLD));

                reviewPanel.add(restaurantLabel);
                reviewsPanel.add(reviewPanel);

                final String reviewText = review.getReviewText();
                if (reviewText != null && !reviewText.isEmpty()) {
                    final JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    textPanel.setBackground(Color.WHITE);
                    final JLabel reviewTextLabel = new JLabel(" - " + reviewText);
                    reviewTextLabel.setForeground(Color.DARK_GRAY);
                    textPanel.add(reviewTextLabel);
                    reviewsPanel.add(textPanel);
                }
            }
        }
        else {
            final JLabel noReviewsLabel = new JLabel("  (No reviews)");
            noReviewsLabel.setForeground(Color.GRAY);
            reviewsPanel.add(noReviewsLabel);
        }

        userPanel.add(reviewsPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        final JButton sendRequestButton = new JButton("Send Friend Request");
        sendRequestButton.setBackground(new Color(70, 130, 180));
        sendRequestButton.setForeground(Color.WHITE);
        sendRequestButton.setFont(sendRequestButton.getFont().deriveFont(Font.BOLD));
        sendRequestButton.addActionListener(evt -> sendFriendRequest(username));
        buttonPanel.add(sendRequestButton);

        final JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(evt -> viewUserProfile(username, reviews));
        buttonPanel.add(viewProfileButton);

        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        return userPanel;
    }

    /**
     * Sends a friend request to the specified user.
     *
     * @param recipientUsername the username to send the friend request to
     */
    private void sendFriendRequest(String recipientUsername) {
        if (sendFriendRequestController == null) {
            JOptionPane.showMessageDialog(this,
                    "Friend request functionality is not available.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentUsername == null) {
            JOptionPane.showMessageDialog(this,
                    "Unable to determine current user. Please log in again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentUsername.equals(recipientUsername)) {
            JOptionPane.showMessageDialog(this,
                    "You cannot send a friend request to yourself!",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            sendFriendRequestController.execute(currentUsername, recipientUsername);

            JOptionPane.showMessageDialog(this,
                    "Friend request sent to " + recipientUsername + "!",
                    "Request Sent",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Failed to send friend request: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            profileInfo.append("Reviews (").append(reviews.size()).append("):\n\n");
            for (Review review : reviews) {
                profileInfo.append("• ").append(review.getRestaurant().getName()).append(" (")
                        .append(review.getRating()).append("/5)\n");

                final String reviewText = review.getReviewText();
                if (reviewText != null && !reviewText.isEmpty()) {
                    profileInfo.append("  \"").append(reviewText).append("\"\n");
                }
                profileInfo.append("\n");
            }
        }
        else {
            profileInfo.append("No reviews yet.");
        }

        JOptionPane.showMessageDialog(this,
                profileInfo.toString(),
                "Profile: " + username,
                JOptionPane.INFORMATION_MESSAGE);
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
