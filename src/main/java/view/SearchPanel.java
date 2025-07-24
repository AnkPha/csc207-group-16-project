package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class SearchPanel extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;

    SearchPanel() {
        this.setLayout(new BorderLayout());

        // Title
        final JLabel title = new JLabel("Search Restaurants by Map and Filters");
        this.add(title, BorderLayout.NORTH);

        // Search bar section
        final JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        this.add(searchPanel, BorderLayout.CENTER);

        // Add logic later for searchButton click, currently just a placeholder
        searchButton.addActionListener(evt -> {
            final String query = searchField.getText();
            System.out.println("Searching for: " + query);
        });
    }
}
