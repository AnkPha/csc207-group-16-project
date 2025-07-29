// view/FriendsPanel.java
package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FriendsPanel extends JPanel {
    private final JTextField searchField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");

    private final DefaultListModel<String> friendListModel = new DefaultListModel<>();
    private final JList<String> friendsList = new JList<>(friendListModel);

    private final DefaultListModel<String> requestListModel = new DefaultListModel<>();
    private final JList<String> requestList = new JList<>(requestListModel);

    public FriendsPanel() {
        this.setLayout(new BorderLayout());

        // North: Search
        final JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Find Friends:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        this.add(topPanel, BorderLayout.NORTH);

        // Center: Friend list
        final JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(new JScrollPane(friendsList));
        centerPanel.add(new JScrollPane(requestList));
        this.add(centerPanel, BorderLayout.CENTER);

        // Labels
        friendsList.setBorder(BorderFactory.createTitledBorder("Your Friends"));
        requestList.setBorder(BorderFactory.createTitledBorder("Friend Requests"));
    }

    public void updateFriendList(List<String> friends) {
        friendListModel.clear();
        for (String friend : friends) {
            friendListModel.addElement(friend);
        }
    }

    public void updateFriendRequests(List<String> requests) {
        requestListModel.clear();
        for (String request : requests) {
            requestListModel.addElement(request);
        }
    }

    // Add controller setters and event hooks here...
}
