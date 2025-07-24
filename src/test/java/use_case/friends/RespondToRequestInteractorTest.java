package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RespondToRequestInteractorTest {
    private InMemoryFriendDataAccessObject dao;

    @BeforeEach
    void setup() {
        dao = new InMemoryFriendDataAccessObject(new CommonUserFactory());
        dao.createUser("alice");
        dao.createUser("bob");
        dao.sendFriendRequest("bob", "alice");  // bob sends a request to alice
    }

    @Test
    void testAcceptFriendRequest() {
        RespondToRequestOutputBoundary presenter = output ->
                assertEquals("Friend request accepted.", output.getMessage());

        RespondToRequestInputData input = new RespondToRequestInputData("alice", "bob", true);
        RespondToRequestInteractor interactor = new RespondToRequestInteractor(dao, presenter);
        interactor.execute(input);

        assertTrue(dao.getFriendsList("alice").contains("bob"));
        assertTrue(dao.getFriendsList("bob").contains("alice"));
        assertFalse(dao.getPendingRequests("alice").contains("bob"));
    }

    @Test
    void testRejectFriendRequest() {
        RespondToRequestOutputBoundary presenter = output ->
                assertEquals("Friend request rejected.", output.getMessage());

        RespondToRequestInputData input = new RespondToRequestInputData("alice", "bob", false);
        RespondToRequestInteractor interactor = new RespondToRequestInteractor(dao, presenter);
        interactor.execute(input);

        assertFalse(dao.getFriendsList("alice").contains("bob"));
        assertFalse(dao.getFriendsList("bob").contains("alice"));
        assertFalse(dao.getPendingRequests("alice").contains("bob"));
    }
}
