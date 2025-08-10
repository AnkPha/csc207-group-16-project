package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.logout.LogoutController;
import interface_adapter.main_menu.MainAppState;
import interface_adapter.main_menu.MainAppViewModel;

class ProfilePanel extends JPanel {
    private static final int TEN = 10;
    private final MainAppViewModel viewModel;
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;

    private final JTextField passwordField = new JTextField(15);
    private final JButton changePasswordButton = new JButton("Change Password");
    private final JButton logoutButton = new JButton("Logout");

    ProfilePanel(MainAppViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("User Profile"));

        final JLabel usernameInfo = new JLabel("Currently logged in: " + viewModel.getState().getUsername());
        this.add(usernameInfo);

        this.add(new JLabel("New Password: "));
        this.add(passwordField);
        this.add(changePasswordButton);
        this.add(Box.createRigidArea(new Dimension(0, TEN)));
        this.add(logoutButton);

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            private void updatePasswordInState() {
                final MainAppState currentState = viewModel.getState();
                currentState.setPassword(passwordField.getText());
                viewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordInState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordInState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordInState();
            }
        });

        changePasswordButton.addActionListener(evt -> {
            if (changePasswordController != null) {
                final String username = viewModel.getState().getUsername();
                final String newPassword = viewModel.getState().getPassword();
                changePasswordController.execute(username, newPassword);
            }
            else {
                JOptionPane.showMessageDialog(this, "Password change controller not set.");
            }
        });

        logoutButton.addActionListener(evt -> {
            if (logoutController != null) {
                final String username = viewModel.getState().getUsername();
                logoutController.execute(username);
            }
            else {
                JOptionPane.showMessageDialog(this, "Logout controller not set.");
            }
        });
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
