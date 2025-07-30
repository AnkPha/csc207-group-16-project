package view;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.logout.LogoutController;
import interface_adapter.main_menu.MainAppViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class MainAppView extends JPanel {

    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private MainAppViewModel viewModel;

    private final JTabbedPane tabbedPane;
    private final ProfilePanel profilePanel;

    public MainAppView(MainAppViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Search", new SearchPanel());

        profilePanel = new ProfilePanel(viewModel);
        tabbedPane.addTab("Profile", profilePanel);

        tabbedPane.addTab("Friends", new FriendsPanel());

        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public String getViewName() {
        return "main app";
    }

    public void setChangePasswordController(ChangePasswordController controller) {
        this.changePasswordController = controller;
        this.profilePanel.setChangePasswordController(controller);
    }

    public void setLogoutController(LogoutController controller) {
        this.logoutController = controller;
        this.profilePanel.setLogoutController(controller);
    }
}