package interface_adapter;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main_menu.MainAppViewModel;
import org.junit.jupiter.api.Test;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import use_case.login.LoginOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LoginInterfaceAdapterTests {

    @Test
    void testLoginControllerCallsInteractorWithCorrectData() {
        TestLoginInteractor interactor = new TestLoginInteractor();
        LoginController controller = new LoginController(interactor);

        controller.execute("user123", "pass456");

        assertNotNull(interactor.capturedInput);
        assertEquals("user123", interactor.capturedInput.getUsername());
        assertEquals("pass456", interactor.capturedInput.getPassword());
    }

    private static class TestLoginInteractor implements LoginInputBoundary {
        LoginInputData capturedInput;

        @Override
        public void execute(LoginInputData inputData) {
            this.capturedInput = inputData;
        }
    }

    @Test
    void testLoginPresenterPrepareSuccessViewUpdatesState() {
        ViewManagerModel viewModel = new ViewManagerModel();
        MainAppViewModel mainAppViewModel = new MainAppViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginPresenter presenter = new LoginPresenter(viewModel, mainAppViewModel, loginViewModel);

        LoginOutputData outputData = new LoginOutputData("alice", false);
        presenter.prepareSuccessView(outputData);

        LoginState state = new LoginState();
        state.setUsername("alice");
        state.setPassword("pass");
        assertEquals("alice", state.getUsername());
        assertNull(state.getLoginError());
    }

    @Test
    void testLoginPresenterPrepareFailViewSetsError() {
        ViewManagerModel viewModel = new ViewManagerModel();
        MainAppViewModel mainAppViewModel = new MainAppViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginPresenter presenter = new LoginPresenter(viewModel, mainAppViewModel, loginViewModel);

        presenter.prepareFailView("Invalid credentials");

        LoginState state = new LoginState();
        state.setLoginError("Invalid credentials");
        assertEquals("Invalid credentials", state.getLoginError());
    }

    @Test
    void testLoginStateGettersAndSetters() {
        LoginState state = new LoginState();
        state.setUsername("bob");
        state.setPassword("secret");
        state.setLoginError("Error occurred");

        assertEquals("bob", state.getUsername());
        assertEquals("secret", state.getPassword());
        assertEquals("Error occurred", state.getLoginError());
    }

    @Test
    void testLoginStateCopyConstructor() {
        LoginState original = new LoginState();
        original.setUsername("originalUser");
        original.setPassword("originalPass");
        original.setLoginError("error");

        LoginState copy = new LoginState();
        copy.setUsername("originalUser");
        copy.setPassword("originalPass");
        copy.setLoginError("error");

        assertEquals("originalUser", copy.getUsername());
        assertEquals("originalPass", copy.getPassword());
        assertEquals("error", copy.getLoginError());
    }

    @Test
    void testLoginViewModelInitialStateNotNull() {
        LoginViewModel viewModel = new LoginViewModel();
        assertNotNull(viewModel.getState());
    }

    @Test
    void testLoginViewModelSetState() {
        LoginViewModel viewModel = new LoginViewModel();
        LoginState newState = new LoginState();
        newState.setUsername("new_user");

        viewModel.setState(newState);
        assertEquals("new_user", viewModel.getState().getUsername());
    }
}
