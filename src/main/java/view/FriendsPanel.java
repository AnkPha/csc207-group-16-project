package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.Review;
import interface_adapter.search_user.SearchUserController;

public class FriendsPanel extends JPanel {
    private final JTextField searchField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");

    private final DefaultListModel<String> friendListModel = new DefaultListModel<>();
    private final JList<String> friendsList = new JList<>(friendListModel);

    private final DefaultListModel<String> requestListModel = new DefaultListModel<>();
    private final JList<String> requestList = new JList<>(requestListModel);

    private final JTextArea searchResultArea = new JTextArea(10, 40);

    private SearchUserController searchUserController;

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

        searchResultArea.setEditable(false);
        searchResultArea.setBorder(BorderFactory.createTitledBorder("Search Results"));
        this.add(new JScrollPane(searchResultArea), BorderLayout.SOUTH);

        friendsList.setBorder(BorderFactory.createTitledBorder("Your Friends"));
        requestList.setBorder(BorderFactory.createTitledBorder("Friend Requests"));

        searchButton.addActionListener(evt -> {
            if (searchUserController != null) {
                final String query = searchField.getText();
                searchUserController.execute(query);
            }
        });
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
        final StringBuilder sb = new StringBuilder();
        for (String user : usernames) {
            sb.append("User: ").append(user).append("\n");
            final List<Review> reviews = userReviews.get(user);
            if (reviews != null && !reviews.isEmpty()) {
                for (Review review : reviews) {
                    sb.append("  - ").append(review.getRestaurant().getName())
                            .append(" | ").append(review.getRating()).append("/5\n");
                }
            }
            else {
                sb.append("  (No reviews)\n");
            }
            sb.append("\n");
        }
        searchResultArea.setText(sb.toString());
    }

    public void setSearchUserController(SearchUserController controller) {
        this.searchUserController = controller;
    }
}
