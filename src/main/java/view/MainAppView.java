package view;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesViewModel;
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

    public MainAppView(MainAppViewModel viewModel, SearchViewModel searchViewModel, FilterViewModel filterViewModel,
                       FavoritesViewModel favoritesViewModel) {

//     public MainAppView(MainAppViewModel viewModel, SearchViewModel searchViewModel,
//                        FavoritesViewModel favoritesViewModel, SearchUserController searchUserController, SearchUserViewModel searchUserViewModel) {
//        searchController = new SearchLocationsNearbyController();
        this.viewModel = viewModel;
        this.searchViewModel = searchViewModel;
        this.filterViewModel = filterViewModel;
        this.setLayout(new BorderLayout());
        this.favoritesViewModel = favoritesViewModel;

        tabbedPane = new JTabbedPane();
        //CHECK OUT
        searchPanel = new SearchPanel(searchViewModel, filterViewModel);
        tabbedPane.addTab("Search", searchPanel);

        favoritesPanel = new FavoritesPanel(favoritesViewModel);
        tabbedPane.addTab("Favorites", favoritesPanel);

        profilePanel = new ProfilePanel(viewModel);
        tabbedPane.addTab("Profile", profilePanel);

        friendsPanel = new FriendsPanel();
//        friendsPanel.setSearchUserController(searchUserController);
        tabbedPane.addTab("Friends", friendsPanel);

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

    public void setSearchController(SearchLocationsNearbyController controller) {
        this.searchController = controller;
        this.searchPanel.setSearchLocationsController(controller);
    }

    public void setFilterController(FilterController controller) {
        this.filterController = controller;
        this.searchPanel.setFilteringController(controller);
    }

    public void setFavoritesController(FavoritesController controller) {
        this.favoritesController = controller;
        this.favoritesPanel.setFavoritesController(controller);
    }
}
