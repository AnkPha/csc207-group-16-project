package view;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.main_menu.MainAppViewModel;
import interface_adapter.review.ReviewController;
import interface_adapter.review.ReviewViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class MainAppView extends JPanel {

    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private FavoritesController favoritesController;
    private ReviewController reviewController;
    private final MainAppViewModel viewModel;
    private SearchLocationsNearbyController searchController;
    private final JTabbedPane tabbedPane;
    private final ProfilePanel profilePanel;
    private final FavoritesPanel favoritesPanel;
    private final SearchPanel searchPanel;
    private final ReviewPanel reviewPanel;
    private final SearchViewModel searchViewModel;
    private final FavoritesViewModel favoritesViewModel;
    private final ReviewViewModel reviewViewModel;

    public MainAppView(MainAppViewModel viewModel, SearchViewModel searchViewModel,
                       FavoritesViewModel favoritesViewModel, ReviewViewModel reviewViewModel) {
        this.viewModel = viewModel;
        this.searchViewModel = searchViewModel;
        this.reviewViewModel = reviewViewModel;
        this.setLayout(new BorderLayout());
        this.favoritesViewModel = favoritesViewModel;

        tabbedPane = new JTabbedPane();
        //CHECK OUT
        searchPanel = new SearchPanel(searchViewModel);
        tabbedPane.addTab("Search", searchPanel);

        favoritesPanel = new FavoritesPanel(favoritesViewModel);
        tabbedPane.addTab("Favorites", favoritesPanel);

        profilePanel = new ProfilePanel(viewModel);
        tabbedPane.addTab("Profile", profilePanel);

        reviewPanel = new ReviewPanel(reviewViewModel);
        tabbedPane.addTab("Review", reviewPanel);

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

    public void setSearchController(SearchLocationsNearbyController controller) {
        this.searchController = controller;
        this.searchPanel.setSearchLocationsController(controller);
    }

    public void setFavoritesController(FavoritesController controller) {
        this.favoritesController = controller;
        this.favoritesPanel.setFavoritesController(controller);
    }

    public void setReviewController(ReviewController controller) {
        this.reviewController = controller;
        this.reviewPanel.setAddReviewController(controller);
    }
 }