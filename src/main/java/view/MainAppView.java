package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import data_access.InMemoryUserDataAccessObject;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesViewModel;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.main_menu.MainAppViewModel;
import interface_adapter.review.ReviewController;
import interface_adapter.review.ReviewViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchViewModel;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserViewModel;
import interface_adapter.send_friend_request.SendFriendRequestController;
import interface_adapter.send_friend_request.SendFriendRequestViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class MainAppView extends JPanel {

    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private ReviewController reviewController;
    private FavoritesController favoritesController;
    private SearchLocationsNearbyController searchController;
    private FilterController filterController;

    private final MainAppViewModel viewModel;
    private final ReviewViewModel reviewViewModel;
    private final FilterViewModel filterViewModel;
    private final SearchViewModel searchViewModel;
    private final FavoritesViewModel favoritesViewModel;

    private final FriendsPanel friendsPanel;
    private final JTabbedPane tabbedPane;
    private final ProfilePanel profilePanel;
    private final FavoritesPanel favoritesPanel;
    private final SearchPanel searchPanel;
    private final ReviewPanel reviewPanel;
  
    private SendFriendRequestController sendFriendRequestController;
    private SendFriendRequestViewModel sendFriendRequestViewModel;
    private SearchUserController searchUserController;
    private SearchUserViewModel searchUserViewModel;
    private FilterController filterController;
    private InMemoryUserDataAccessObject userDataAccessObject;

    public MainAppView(MainAppViewModel viewModel,
                       SearchViewModel searchViewModel,
                       FavoritesViewModel favoritesViewModel,
                       FilterViewModel filterViewModel,
                       SearchUserController searchUserController,
                       SearchUserViewModel searchUserViewModel,
                       FilterController filterController1,
                       ReviewController reviewController,
                       ReviewViewModel reviewViewModel) {
        this.viewModel = viewModel;
        this.searchViewModel = searchViewModel;
        this.filterController = filterController1;
        this.filterViewModel = filterViewModel;
        this.setLayout(new BorderLayout());
        this.favoritesViewModel = favoritesViewModel;
        this.reviewViewModel = reviewViewModel;
        this.reviewController = reviewController;

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
        reviewPanel = new ReviewPanel(reviewViewModel);
        tabbedPane.addTab("Reviews", reviewPanel);

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

    /**
     * Sets the controller for sending friend requests and passes it to the friends panel.
     *
     * @param controller the SendFriendRequestController
     */
    public void setSendFriendRequestController(SendFriendRequestController controller) {
        this.sendFriendRequestController = controller;
        if (friendsPanel != null) {
            friendsPanel.setSendFriendRequestController(controller);
        }
    }

    private void sendFriendRequest(String recipientUsername) {
        if (sendFriendRequestController != null) {
            final String currentUser = getCurrentUsername();
            sendFriendRequestController.execute(currentUser, recipientUsername);
        }
    }

    /**
     * This method delegates the provided view model to the internal friends panel.
     *
     * @param newViewModel the {@code SendFriendRequestViewModel} to be passed to the friends panel
     */
    public void setSendFriendRequestViewModel(SendFriendRequestViewModel newViewModel) {
        friendsPanel.setSendFriendRequestViewModel(newViewModel);
    }

    /**
     * Sets the current username in the friends panel so it knows who is sending requests.
     *
     * @param username the current logged-in user's username
     */
    public void setCurrentUsername(String username) {
        if (friendsPanel != null) {
            friendsPanel.setCurrentUsername(username);
        }
    }

    private String getCurrentUsername() {
        return userDataAccessObject.getCurrentUsername();
    }

    private void initializeFriendsPanel() {
        if (friendsPanel == null) {
            friendsPanel = new FriendsPanel();
            // Set controllers
            friendsPanel.setSearchUserController(searchUserController);
            friendsPanel.setSearchUserViewModel(searchUserViewModel);

            // Set current username if available
            final String currentUser = getCurrentUsername();
            if (currentUser != null) {
                friendsPanel.setCurrentUsername(currentUser);
            }
        }
    }

    private JButton createSendRequestButton(String username) {
        final JButton sendRequestBtn = new JButton("Send Friend Request");
        sendRequestBtn.addActionListener(evt -> sendFriendRequest(username));
        return sendRequestBtn;
      
     /**
     * A method that sets the Review Controller.
     * @param controller the controller.
     */
    public void setReviewController(ReviewController controller) {
        this.reviewController = controller;
        this.reviewPanel.setAddReviewController(controller);

    }
}
