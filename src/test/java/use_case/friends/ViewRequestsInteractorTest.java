package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewRequestsInteractorTest {

    private InMemoryFriendDataAccessObject dao;

    @BeforeEach
    void setUp() {
        dao = new InMemoryFriendDataAccessObject(new CommonUserFactory());
        dao.createUser("alice");
        dao.createUser("bob");
    }

    @Test
    void testViewRequestsReturnsSingleRequest() {
        dao.sendFriendRequest("alice", "bob");

        ViewRequestsOutputBoundary presenter = outputData ->
                assertEquals(List.of("alice"), outputData.getPendingUsernames());

        ViewRequestsInteractor interactor = new ViewRequestsInteractor(dao, presenter);
        interactor.execute(new ViewRequestsInputData("bob"));
    }

    @Test
    void testViewRequestsReturnsNoRequests() {
        ViewRequestsOutputBoundary presenter = outputData ->
                assertTrue(outputData.getPendingUsernames().isEmpty());

        ViewRequestsInteractor interactor = new ViewRequestsInteractor(dao, presenter);
        interactor.execute(new ViewRequestsInputData("alice"));
    }

    @Test
    void testViewRequestsMultipleRequests() {
        dao.createUser("carol");
        dao.createUser("dave");

        dao.sendFriendRequest("carol", "bob");
        dao.sendFriendRequest("dave", "bob");

        ViewRequestsOutputBoundary presenter = outputData -> {
            List<String> requests = outputData.getPendingUsernames();
            assertTrue(requests.contains("carol"));
            assertTrue(requests.contains("dave"));
            assertEquals(2, requests.size());
        };

        ViewRequestsInteractor interactor = new ViewRequestsInteractor(dao, presenter);
        interactor.execute(new ViewRequestsInputData("bob"));
    }
}
