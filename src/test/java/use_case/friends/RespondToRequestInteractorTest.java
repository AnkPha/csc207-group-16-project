package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RespondToRequestInteractorTest {
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

        dao.sendFriendRequest("Bob", "Alice");
    }

    @Test
    void testAcceptFriendRequest() {
        RespondToRequestOutputBoundary presenter = output ->
                assertEquals("Friend request accepted.", output.getMessage());

        RespondToRequestInputData input = new RespondToRequestInputData("Alice", "Bob", true);
        RespondToRequestInteractor interactor = new RespondToRequestInteractor(dao, presenter);
        interactor.execute(input);

        assertTrue(dao.getFriendsList("Alice").contains("Bob"));
        assertTrue(dao.getFriendsList("Bob").contains("Alice"));
        assertFalse(dao.getPendingRequests("Alice").contains("Bob"));
    }

    @Test
    void testRejectFriendRequest() {
        RespondToRequestOutputBoundary presenter = output ->
                assertEquals("Friend request rejected.", output.getMessage());

        RespondToRequestInputData input = new RespondToRequestInputData("Alice", "Bob", false);
        RespondToRequestInteractor interactor = new RespondToRequestInteractor(dao, presenter);
        interactor.execute(input);

        assertFalse(dao.getFriendsList("Alice").contains("Bob"));
        assertFalse(dao.getFriendsList("Bob").contains("Alice"));
        assertFalse(dao.getPendingRequests("Alice").contains("Bob"));
    }
}
