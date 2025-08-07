package use_case.change_password;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordInteractorTest {

    @Test
    void testExecuteChangesPasswordAndPreparesSuccessView() {
        final String testUsername = "John";
        final String newPassword = "secure123";

        UserFactory userFactory = (password, name) -> new User() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getPassword() {
                return password;
            }
        };

        final boolean[] daoCalled = {false};
        final User[] userPassedToDAO = new User[1];
        ChangePasswordUserDataAccessInterface fakeDAO = user -> {
            daoCalled[0] = true;
            userPassedToDAO[0] = user;
        };

        final boolean[] presenterCalled = {false};
        final ChangePasswordOutputData[] outputCaptured = new ChangePasswordOutputData[1];
        ChangePasswordOutputBoundary fakePresenter = new ChangePasswordOutputBoundary() {
            @Override
            public void prepareSuccessView(ChangePasswordOutputData outputData) {
                presenterCalled[0] = true;
                outputCaptured[0] = outputData;
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call fail view on valid input.");
            }
        };

        ChangePasswordInteractor interactor = new ChangePasswordInteractor(fakeDAO, fakePresenter, userFactory);
        ChangePasswordInputData inputData = new ChangePasswordInputData(testUsername, newPassword);

        interactor.execute(inputData);

        assertTrue(daoCalled[0], "DAO should be called");
        assertNotNull(userPassedToDAO[0], "User passed to DAO should not be null");
        assertEquals(testUsername, userPassedToDAO[0].getName(), "Username should match");
        assertEquals(newPassword, userPassedToDAO[0].getPassword(), "Password should match");
        assertTrue(presenterCalled[0], "Presenter should be called");
        assertNotNull(outputCaptured[0], "Output data should not be null");
        assertEquals(testUsername, outputCaptured[0].getUsername(), "Presenter username should match");
        assertFalse(outputCaptured[0].isUseCaseFailed(), "Use case should not have failed");
    }
}
