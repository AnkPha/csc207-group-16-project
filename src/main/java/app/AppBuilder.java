package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryFriendDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.SearchLocationNearbyDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.favorites_list.FavoritesController;
import interface_adapter.favorites_list.FavoritesViewModel;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterPresenter;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.main_menu.MainAppViewModel;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyPresenter;
import interface_adapter.search_nearby_locations.SearchViewModel;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserPresenter;
import interface_adapter.search_user.SearchUserViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.friends.SearchUserInteractor;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.favorite_list.AddToFavoritesInputBoundary;
import use_case.favorite_list.AddToFavoritesInteractor;
import use_case.favorite_list.RemoveFromFavoritesInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
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
    // private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final SearchLocationsNearbyDataAccessInterface searchDataAccessObject = new SearchLocationNearbyDataAccessObject();

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
    private SearchUserController searchUserController;
    private SearchUserViewModel searchUserViewModel;

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

     * Adds the MainApp View to the application.
     * @return this builder
     */
    public AppBuilder addMainAppView() {
        if (searchViewModel == null) {
            throw new IllegalStateException("searchViewModel must be initialized before creating MainAppView");
        }
        if (filterViewModel == null) {
            throw new IllegalStateException("filterViewModel must be initialized before creating MainAppView");
        }
        mainAppViewModel = new MainAppViewModel();

        FavoritesViewModel favoritesViewModel = new FavoritesViewModel();
        mainAppView = new MainAppView(mainAppViewModel, searchViewModel, favoritesViewModel, filterViewModel);
        cardPanel.add(mainAppView, mainAppView.getViewName());
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

    public AppBuilder addSearchViewModel() {
        this.searchViewModel = new SearchViewModel();
        return this;
    }

    public AppBuilder addFriendsUseCase() {
        userDataAccessObject.populateSampleUsers();

        this.friendDataAccessObject = new InMemoryFriendDataAccessObject(userDataAccessObject, userFactory);

        this.searchUserViewModel = new SearchUserViewModel();

        final SearchUserPresenter searchUserPresenter = new SearchUserPresenter(searchUserViewModel);
        final SearchUserInteractor searchUserInteractor = new SearchUserInteractor(friendDataAccessObject, searchUserPresenter);
        this.searchUserController = new SearchUserController(searchUserInteractor);

        return this;
    }

    public AppBuilder addFavoritesViewModel() {
        this.favoritesViewModel = new FavoritesViewModel();
        return this;
    }

    public AppBuilder addFavoritesUseCase() {

        final FavoritesController favoritesController =
                new FavoritesController(addToFavoritesInteractor, removeFromFavoritesInteractor);

        // This line is crucial - make sure it's there:
        mainAppView.setFavoritesController(favoritesController);

        return this;
    }

    /**
     * Adds the MainApp View to the application.
     * @return this builder
     */
    public AppBuilder addMainAppView() {
        mainAppViewModel = new MainAppViewModel();
        favoritesViewModel = new FavoritesViewModel();

        mainAppView = new MainAppView(mainAppViewModel, searchViewModel, favoritesViewModel, searchUserController, searchUserViewModel);
        cardPanel.add(mainAppView, mainAppView.getViewName());
        return this;
    }
}
