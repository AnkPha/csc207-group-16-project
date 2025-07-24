package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SendFriendRequestInteractorTest {
    private InMemoryFriendDataAccessObject dao;

    @BeforeEach
    void setup() {
        dao = new InMemoryFriendDataAccessObject(new CommonUserFactory());
        dao.createUser("alice");
        dao.createUser("bob");
    }

    @Test
    void testSendFriendRequestSuccess() {
        SendFriendRequestOutputBoundary presenter = output -> {
            assertTrue(output.getSuccess());
            assertEquals("Request sent.", output.getMessage());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("alice", "bob"));
        assertTrue(dao.getPendingRequests("bob").contains("alice"));
    }

    @Test
    void testSendFriendRequestFailsForNonexistentUser() {
        SendFriendRequestOutputBoundary presenter = output -> {
            assertFalse(output.getSuccess());
            assertEquals("Request failed (maybe already friends or pending).", output.getMessage());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("alice", "charlie"));  // charlie doesn't exist
    }

    @Test
    void testSendFriendRequestFailsForDuplicateRequest() {
        dao.sendFriendRequest("alice", "bob");

        SendFriendRequestOutputBoundary presenter = output -> {
            assertFalse(output.getSuccess());
            assertEquals("Request failed (maybe already friends or pending).", output.getMessage());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("alice", "bob"));  // already sent
    }

    @Test
    void testSendRequestToSelfFails() {
        SendFriendRequestOutputBoundary presenter = output -> {
            assertFalse(output.getSuccess());
        };

        SendFriendRequestInteractor interactor = new SendFriendRequestInteractor(dao, presenter);
        interactor.execute(new SendFriendRequestInputData("alice", "alice"));
    }
}
