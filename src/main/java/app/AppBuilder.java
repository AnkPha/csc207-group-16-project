package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.FilterDataAccessObject;
import data_access.InMemoryFriendDataAccessObject;
import data_access.InMemoryReviewDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.SearchLocationNearbyDataAccessObject;
import data_access.favorite_list.FavoritesDataAccessInterface;
import data_access.favorite_list.FavoritesDataAccessObject;
import entity.CommonReviewFactory;
import entity.CommonUserFactory;
import entity.ReviewFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesPresenter;
import interface_adapter.favorites_list.FavoritesViewModel;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterPresenter;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.main_menu.MainAppState;
import interface_adapter.main_menu.MainAppViewModel;
import interface_adapter.review.ReviewController;
import interface_adapter.review.ReviewPresenter;
import interface_adapter.review.ReviewViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyPresenter;
import interface_adapter.search_nearby_locations.SearchViewModel;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserPresenter;
import interface_adapter.search_user.SearchUserViewModel;
import interface_adapter.send_friend_request.SendFriendRequestController;
import interface_adapter.send_friend_request.SendFriendRequestPresenter;
import interface_adapter.send_friend_request.SendFriendRequestViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.favorite_list.AddToFavoritesInteractor;
import use_case.favorite_list.RemoveFromFavoritesInteractor;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.friends.SearchUserInteractor;
import use_case.friends.SendFriendRequestInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.review.AddReviewAccessInterface;
import use_case.review.AddReviewInputBoundary;
import use_case.review.AddReviewInteractor;
import use_case.review.AddReviewOutputBoundary;
import use_case.search_nearby_locations.SearchLocationsNearbyDataAccessInterface;
import use_case.search_nearby_locations.SearchLocationsNearbyInputBoundary;
import use_case.search_nearby_locations.SearchLocationsNearbyInteractor;
import use_case.search_nearby_locations.SearchLocationsNearbyOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.LoginView;
import view.MainAppView;
import view.SignupView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final AddReviewAccessInterface reviewDataAccessInterface = new InMemoryReviewDataAccessObject();
    private final SearchLocationsNearbyDataAccessInterface searchDataAccessObject =
            new SearchLocationNearbyDataAccessObject(reviewDataAccessInterface);
    private final FilterDataAccessInterface filterDataAccessObject =
            new FilterDataAccessObject(reviewDataAccessInterface);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoginView loginView;
    private MainAppViewModel mainAppViewModel;
    private MainAppView mainAppView;
    private AddToFavoritesInteractor addToFavoritesInteractor;
    private RemoveFromFavoritesInteractor removeFromFavoritesInteractor;
    private SearchViewModel searchViewModel;
    private FavoritesViewModel favoritesViewModel;
    private InMemoryFriendDataAccessObject friendDataAccessObject;
    private FilterViewModel filterViewModel;
    private FilterController filterController;
    private SearchUserController searchUserController;
    private SearchUserViewModel searchUserViewModel;
    private SendFriendRequestController sendFriendRequestController;
    private SendFriendRequestViewModel sendFriendRequestViewModel;
    private ReviewViewModel reviewViewModel;
    private ReviewController reviewController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                mainAppViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(mainAppViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        mainAppView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                mainAppViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        mainAppView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("TasteMap");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

    /**
     * A method that adds the search use case.
     * @return the AppBuilder with search use case set up
     */
    public AppBuilder addSearchUseCase() {
        final SearchLocationsNearbyOutputBoundary searchOutputBoundary =
                new SearchLocationsNearbyPresenter(searchViewModel);

        final SearchLocationsNearbyInputBoundary searchInteractor =
                new SearchLocationsNearbyInteractor(searchDataAccessObject, searchOutputBoundary);

        final SearchLocationsNearbyController searchController =
                new SearchLocationsNearbyController(searchInteractor);

        mainAppView.setSearchController(searchController);
        return this;
    }

    /**
     * A method that sets up the search view model.
     * @return An app builder with the search view model set
     */
    public AppBuilder addSearchViewModel() {
        this.searchViewModel = new SearchViewModel();
        return this;
    }

    /**
     * A method that adds friends use case.
     * @return An app builder with the friends use case added
     */
    public AppBuilder addFriendsUseCase() {
        userDataAccessObject.populateSampleUsers();

        this.friendDataAccessObject = new InMemoryFriendDataAccessObject(userDataAccessObject, userFactory);

        this.searchUserViewModel = new SearchUserViewModel();

        final SearchUserPresenter searchUserPresenter = new SearchUserPresenter(searchUserViewModel);
        final SearchUserInteractor searchUserInteractor = new SearchUserInteractor(
                friendDataAccessObject,
                searchUserPresenter);
        this.searchUserController = new SearchUserController(searchUserInteractor);

        return this;
    }

    /**
     * Adds filter view model to the application.
     * @return this builder
     */
    public AppBuilder addFilterViewModel() {
        this.filterViewModel = new FilterViewModel();
        return this;
    }

    /**
     * Adds the favorite view model to this application.
     * @return this builder
     */

    public AppBuilder addFavoritesViewModel() {
        this.favoritesViewModel = new FavoritesViewModel();
        return this;
    }

    /**
     * Adds the favorite use case to this application.
     * @return this builder
     */
    public AppBuilder addFavoritesUseCase() {
        final FavoritesDataAccessInterface favoritesDataAccess = new FavoritesDataAccessObject();

        final FavoritesPresenter favoritesPresenter = new FavoritesPresenter(favoritesViewModel);

        addToFavoritesInteractor = new AddToFavoritesInteractor(favoritesDataAccess, favoritesPresenter);
        removeFromFavoritesInteractor = new RemoveFromFavoritesInteractor(favoritesDataAccess, favoritesPresenter);

        final FavoritesController favoritesController =
                new FavoritesController(addToFavoritesInteractor, removeFromFavoritesInteractor);

        mainAppView.setFavoritesController(favoritesController);

        return this;
    }

    /**
     * Adds the filter use case to this application.
     * @return this builder
     */
    public AppBuilder addFilterUseCase() {
        final FilterOutputBoundary filterOutputBoundary =
                new FilterPresenter(filterViewModel);

        final FilterInputBoundary filterInteractor =
                new FilterInteractor(filterDataAccessObject, filterOutputBoundary);

        filterController = new FilterController(filterInteractor);

        mainAppView.setFilterController(filterController);
        return this;

    }

    /**
     * Adds the review view model to this application.
     * @return this builder
     */
    public AppBuilder addReviewsViewModel() {
        reviewViewModel = new ReviewViewModel();
        return this;
    }

    /**
     * Adds the send friend request view model to this application.
     * @return this builder
     */
    public AppBuilder addSendFriendRequestViewModel() {
        this.sendFriendRequestViewModel = new SendFriendRequestViewModel();
        return this;
    }

    /**
     * Adds the send friend request use case to this application.
     * @return this builder
     */
    public AppBuilder addSendFriendRequestUseCase() {
        final SendFriendRequestPresenter sendFriendRequestPresenter =
                new SendFriendRequestPresenter(sendFriendRequestViewModel);

        final SendFriendRequestInteractor sendFriendRequestInteractor =
                new SendFriendRequestInteractor(friendDataAccessObject, sendFriendRequestPresenter);

        this.sendFriendRequestController = new SendFriendRequestController(sendFriendRequestInteractor);
        return this;
    }

    /**
     * Adds the add review use case to application.
     * @return this builder
     */
    public AppBuilder addReviewsUseCase() {
        final AddReviewOutputBoundary addReviewOutputBoundary = new ReviewPresenter(reviewViewModel);
        final ReviewFactory reviewFactory = new CommonReviewFactory();

        final AddReviewInputBoundary addReviewInteractor = new AddReviewInteractor(
                reviewDataAccessInterface,
                addReviewOutputBoundary,
                reviewFactory
        );

        reviewController = new ReviewController(addReviewInteractor);

        if (mainAppView != null) {
            mainAppView.setReviewController(reviewController);
        }
        return this;
    }

    /**
     * Adds the MainApp View to the application.
     * @return this builder
     */
    public AppBuilder addMainAppView() {
        mainAppViewModel = new MainAppViewModel();
        favoritesViewModel = new FavoritesViewModel();

        mainAppView = new MainAppView(mainAppViewModel,
                searchViewModel,
                favoritesViewModel,
                filterViewModel,
                searchUserController,
                searchUserViewModel,
                filterController,
                reviewController,
                reviewViewModel);

        mainAppViewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                final MainAppState state = mainAppViewModel.getState();
                if (state.getUsername() != null) {
                    mainAppView.setCurrentUsername(state.getUsername());

                    mainAppView.setCurrentUser(state.getUsername(), state.getPassword());
                }
            }
        });

        cardPanel.add(mainAppView, mainAppView.getViewName());
        if (sendFriendRequestViewModel != null) {
            mainAppView.setSendFriendRequestController(sendFriendRequestController);
        }
        return this;
    }

    /**
     * Add this method to set the current username after login.
     * @param username the username of the currently logged-in user
     * @return the current instance of {@code AppBuilder}, allowing for method chaining
     */
    public AppBuilder setCurrentUsername(String username) {
        if (mainAppView != null) {
            mainAppView.setCurrentUsername(username);
        }
        return this;
    }
}
