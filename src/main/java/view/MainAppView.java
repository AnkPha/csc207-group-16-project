package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesViewModel;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.main_menu.MainAppViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchViewModel;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class MainAppView extends JPanel {

    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;

    private FavoritesController favoritesController;
    private final MainAppViewModel viewModel;
    private SearchLocationsNearbyController searchController;
    private final JTabbedPane tabbedPane;
    private final ProfilePanel profilePanel;
    private final FavoritesPanel favoritesPanel;
    private final SearchPanel searchPanel;
    private final FriendsPanel friendsPanel;
    private final SearchViewModel searchViewModel;
    private final FavoritesViewModel favoritesViewModel;

    private final FilterViewModel filterViewModel;
    private FilterController filterController;

    public MainAppView(MainAppViewModel viewModel,
                       SearchViewModel searchViewModel,
                       FavoritesViewModel favoritesViewModel,
                       FilterViewModel filterViewModel,
                       SearchUserController searchUserController,
                       SearchUserViewModel searchUserViewModel,
                       FilterController filterController1) {
        this.viewModel = viewModel;
        this.searchViewModel = searchViewModel;
        this.filterController = filterController1;
        this.filterViewModel = filterViewModel;
        this.setLayout(new BorderLayout());
        this.favoritesViewModel = favoritesViewModel;

        tabbedPane = new JTabbedPane();

        searchPanel = new SearchPanel(searchViewModel, filterViewModel);
        tabbedPane.addTab("Search", searchPanel);

        favoritesPanel = new FavoritesPanel(favoritesViewModel);
        tabbedPane.addTab("Favorites", favoritesPanel);

        profilePanel = new ProfilePanel(viewModel);
        tabbedPane.addTab("Profile", profilePanel);
        friendsPanel = new FriendsPanel();
        friendsPanel.setSearchUserController(searchUserController);
        friendsPanel.setSearchUserViewModel(searchUserViewModel);
        tabbedPane.addTab("Friends", friendsPanel);

        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public String getViewName() {
        return "main app";
    }

    /**
     * A method that sets the ChangePassWord Controller.
     * @param controller the controller
     */
    public void setChangePasswordController(ChangePasswordController controller) {
        this.changePasswordController = controller;
        this.profilePanel.setChangePasswordController(controller);
    }

    /**
     * A method that sets the Logout Controller.
     * @param controller the controller
     */
    public void setLogoutController(LogoutController controller) {
        this.logoutController = controller;
        this.profilePanel.setLogoutController(controller);
    }

    /**
     * A method that sets the Search Controller.
     * @param controller the controller
     */
    public void setSearchController(SearchLocationsNearbyController controller) {
        this.searchController = controller;
        this.searchPanel.setSearchLocationsController(controller);
    }

    /**
     * A method that sets the favorite controller.
     * @param controller the controller
     */
    public void setFavoritesController(FavoritesController controller) {
        this.favoritesController = controller;
        this.favoritesPanel.setFavoritesController(controller);
    }

    /**
     * A method that sets the Filter Controller.
     * @param controller the controller.
     */
    public void setFilterController(FilterController controller) {
        this.filterController = controller;
        this.searchPanel.setFilteringController(controller);
    }
}
