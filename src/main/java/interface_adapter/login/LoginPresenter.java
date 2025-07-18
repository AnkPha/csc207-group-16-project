package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.main_menu.MainAppState;
import interface_adapter.main_menu.MainAppViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MainAppViewModel mainAppViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          MainAppViewModel mainAppViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainAppViewModel = mainAppViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the main app view.

        final MainAppState mainAppState = mainAppViewModel.getState();
        mainAppState.setUsername(response.getUsername());
        this.mainAppViewModel.setState(mainAppState);
        this.mainAppViewModel.firePropertyChanged();

        this.viewManagerModel.setState(mainAppViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }
}
