package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SendFriendRequestInteractorTest {
    private InMemoryUserDataAccessObject userDao;
    private InMemoryFriendDataAccessObject dao;

    @BeforeEach
    void setUp() {
        userDao = new InMemoryUserDataAccessObject();
        dao = new InMemoryFriendDataAccessObject(userDao, new CommonUserFactory());
        CommonUser alice = new CommonUser("Alice", "pw");
        CommonUser bob = new CommonUser("Bob", "pw");

        userDao.save(alice);
        userDao.save(bob);

        dao.addUser(alice);
        dao.addUser(bob);
    }

    @Test
    void testSendFriendRequestSuccess() {
        SendFriendRequestOutputBoundary presenter = output -> {
            assertTrue(output.getSuccess());
            assertEquals("Request sent.", output.getMessage());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("Alice", "Bob"));
        assertTrue(dao.getPendingRequests("Bob").contains("Alice"));
    }

    @Test
    void testSendFriendRequestFailsForNonexistentUser() {
        SendFriendRequestOutputBoundary presenter = output -> {
            assertFalse(output.getSuccess());
            assertEquals("Request failed (maybe already friends or pending).", output.getMessage());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("Alice", "Charlie"));  // charlie doesn't exist
    }

    @Test
    void testSendFriendRequestFailsForDuplicateRequest() {
        dao.sendFriendRequest("Alice", "Bob");

        SendFriendRequestOutputBoundary presenter = output -> {
            assertFalse(output.getSuccess());
            assertEquals("Request failed (maybe already friends or pending).", output.getMessage());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("Alice", "Bob"));
    }

    @Test
    void testSendRequestToSelfFails() {
        SendFriendRequestOutputBoundary presenter = output -> {
            assertFalse(output.getSuccess());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("Alice", "Alice"));
    }
}
