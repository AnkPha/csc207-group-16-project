package interface_adapter;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInputData;
import use_case.change_password.ChangePasswordOutputData;

class ChangePasswordInterfaceAdapterTests {

    @Test
    void testControllerExecuteCallsInteractorWithCorrectData() {
        TestInteractor testInteractor = new TestInteractor();
        ChangePasswordController controller = new ChangePasswordController(testInteractor);

        controller.execute("testUser", "newPass123");

        Assertions.assertNotNull(testInteractor.capturedInput);
        Assertions.assertEquals("testUser", testInteractor.capturedInput.getUsername());
    }

    private static class TestInteractor implements ChangePasswordInputBoundary {
        ChangePasswordInputData capturedInput;

        @Override
        public void execute(ChangePasswordInputData inputData) {
            this.capturedInput = inputData;
        }
    }

    @Test
    void testPresenterPrepareSuccessViewFiresPropertyChange() {
        MainAppViewModelMock viewModel = new MainAppViewModelMock();
        ChangePasswordPresenter presenter = new ChangePasswordPresenter(viewModel);

        ChangePasswordOutputData outputData = new ChangePasswordOutputData("testUser", false);
        presenter.prepareSuccessView(outputData);

        Assertions.assertTrue(viewModel.propertyChanged);
        Assertions.assertEquals("password", viewModel.changedPropertyName);
    }

    private static class MainAppViewModelMock extends interface_adapter.main_menu.MainAppViewModel {
        boolean propertyChanged = false;
        String changedPropertyName;

        @Override
        public void firePropertyChanged(String propertyName) {
            propertyChanged = true;
            changedPropertyName = propertyName;
        }
    }

    @Test
    void testLoggedInStateSetAndGetUsername() {
        LoggedInState state = new LoggedInState();
        state.setUsername("user123");
        Assertions.assertEquals("user123", state.getUsername());
    }

    @Test
    void testLoggedInStateSetAndGetPassword() {
        LoggedInState state = new LoggedInState();
        state.setPassword("pass123");
        Assertions.assertEquals("pass123", state.getPassword());
    }

    @Test
    void testLoggedInStateSetAndGetPasswordError() {
        LoggedInState state = new LoggedInState();
        state.setPasswordError("Too short");
        Assertions.assertEquals("Too short", state.passwordError);
    }

    @Test
    void testLoggedInStateCopyConstructor() {
        LoggedInState original = new LoggedInState();
        original.setUsername("originalUser");
        original.setPassword("originalPass");
        original.setPasswordError("originalError");

        LoggedInState copy = new LoggedInState(original);
        Assertions.assertEquals("originalUser", copy.getUsername());
        Assertions.assertEquals("originalPass", copy.getPassword());
        Assertions.assertEquals("originalError", copy.passwordError);
    }

    @Test
    void testLoggedInViewModelInitialStateNotNull() {
        LoggedInViewModel viewModel = new LoggedInViewModel();
        Assertions.assertNotNull(viewModel.getState());
        Assertions.assertTrue(true);
    }

    @Test
    void testLoggedInViewModelSetState() {
        LoggedInViewModel viewModel = new LoggedInViewModel();
        LoggedInState newState = new LoggedInState();
        newState.setUsername("newUser");

        viewModel.setState(newState);

        Assertions.assertEquals("newUser", viewModel.getState().getUsername());
    }
}
