package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import entity.CommonUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchUserInteractorTest {

    private InMemoryFriendDataAccessObject dao;

    @BeforeEach
    void setUp() {
        dao = new InMemoryFriendDataAccessObject(new CommonUserFactory());
        dao.createUser("alice");
        dao.createUser("alex");
        dao.createUser("bob");
    }

    @Test
    void testSearchReturnsMatchingUsers() {
        SearchUserOutputBoundary presenter = outputData -> {
            List<String> results = outputData.results();
            assertTrue(results.contains("alice"));
            assertTrue(results.contains("alex"));
            assertFalse(results.contains("bob"));
        };

        SearchUserInputData input = new SearchUserInputData("al");
        SearchUserInteractor interactor = new SearchUserInteractor(dao, presenter);
        interactor.execute(input);
    }

    @Test
    void testSearchReturnsNoMatches() {
        SearchUserOutputBoundary presenter = outputData ->
                assertTrue(outputData.results().isEmpty());

        SearchUserInputData input = new SearchUserInputData("zzz");
        SearchUserInteractor interactor = new SearchUserInteractor(dao, presenter);
        interactor.execute(input);
    }

    @Test
    void testSearchReturnsAllOnEmptyQuery() {
        SearchUserOutputBoundary presenter = outputData ->
                assertEquals(List.of("alice", "alex", "bob"), outputData.results());

        SearchUserInputData input = new SearchUserInputData("");
        SearchUserInteractor interactor = new SearchUserInteractor(dao, presenter);
        interactor.execute(input);
    }
}
