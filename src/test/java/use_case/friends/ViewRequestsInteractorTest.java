package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewRequestsInteractorTest {

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
    void testViewRequestsReturnsSingleRequest() {
        dao.sendFriendRequest("Alice", "Bob");

        ViewRequestsOutputBoundary presenter = outputData ->
                assertEquals(List.of("Alice"), outputData.getPendingUsernames());

        ViewRequestsInteractor interactor = new ViewRequestsInteractor(dao, presenter);
        interactor.execute(new ViewRequestsInputData("Bob"));
    }

    @Test
    void testViewRequestsReturnsNoRequests() {
        ViewRequestsOutputBoundary presenter = outputData ->
                assertTrue(outputData.getPendingUsernames().isEmpty());

        ViewRequestsInteractor interactor = new ViewRequestsInteractor(dao, presenter);
        interactor.execute(new ViewRequestsInputData("Alice"));
    }

    @Test
    void testViewRequestsMultipleRequests() {
        dao.createUser("Carol");
        dao.createUser("Dave");

        dao.sendFriendRequest("Carol", "Bob");
        dao.sendFriendRequest("Dave", "Bob");

        ViewRequestsOutputBoundary presenter = outputData -> {
            List<String> requests = outputData.getPendingUsernames();
            assertTrue(requests.contains("Carol"));
            assertTrue(requests.contains("Dave"));
            assertEquals(2, requests.size());
        };

        ViewRequestsInteractor interactor = new ViewRequestsInteractor(dao, presenter);
        interactor.execute(new ViewRequestsInputData("Bob"));
    }
}
