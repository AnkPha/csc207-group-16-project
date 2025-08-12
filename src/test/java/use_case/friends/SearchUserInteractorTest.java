package use_case.friends;

import data_access.InMemoryFriendDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import Search.Restaurant;
import entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SearchUserInteractorTest {

    private InMemoryUserDataAccessObject userDao;
    private InMemoryFriendDataAccessObject dao;

    @BeforeEach
    void setUp() {
        userDao = new InMemoryUserDataAccessObject();
        dao = new InMemoryFriendDataAccessObject(userDao, new CommonUserFactory());
        CommonUser alice = new CommonUser("Alice", "pw");
        CommonUser alex = new CommonUser("Alex", "pw");
        CommonUser bob = new CommonUser("Bob", "pw");

        userDao.save(alice);
        userDao.save(alex);
        userDao.save(bob);

        dao.addUser(alice);
        dao.addUser(alex);
        dao.addUser(bob);
    }

    @Test
    void testSearchReturnsMatchingUsers() {
        SearchUserOutputBoundary presenter = outputData -> {
            List<String> results = outputData.results();
            assertTrue(results.contains("Alice"));
            assertTrue(results.contains("Alex"));
            assertFalse(results.contains("Bob"));
        };

        SearchUserInputData input = new SearchUserInputData("Al");
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
        SearchUserOutputBoundary presenter = outputData -> {
            List<String> expected = new ArrayList<>(Arrays.asList("Alice", "Alex","Bob"));
            List<String> actual = new ArrayList<>(outputData.results());

            Collections.sort(expected);
            Collections.sort(actual);

            assertEquals(expected, actual);
        };

        SearchUserInputData input = new SearchUserInputData("");
        SearchUserInteractor interactor = new SearchUserInteractor(dao, presenter);
        interactor.execute(input);
    }

    @Test
    void testSearchReturnsUserWithReviews() {
        // Arrange: Prepare the user and review data
        CommonUser alice = (CommonUser) userDao.get("Alice");

        double[] coords = {43.65107, -79.347015};
        Restaurant testaurant = new Restaurant(
                "Testaurant",
                "123 Main St",
                "Fusion",
                "Vegan Options",
                "10AM - 10PM",
                "https://testaurant.com",
                coords
        );

        alice.addReview(testaurant, 5, "Delicious food and cozy atmosphere!");

        // Act & Assert
        SearchUserOutputBoundary presenter = outputData -> {
            Map<String, List<Review>> reviewsMap = outputData.getUserReviews();

            assertTrue(reviewsMap.containsKey("Alice"));
            List<Review> aliceReviews = reviewsMap.get("Alice");

            assertNotNull(aliceReviews);
            assertEquals(1, aliceReviews.size());

            Review retrieved = aliceReviews.get(0);
            assertEquals("Delicious food and cozy atmosphere!", retrieved.getReviewText());
            assertEquals(5, retrieved.getRating());
            assertEquals("Testaurant", retrieved.getRestaurant().getName());
            assertEquals("Alice", retrieved.getUser().getName());
        };

        SearchUserInputData input = new SearchUserInputData("Alice");
        SearchUserInteractor interactor = new SearchUserInteractor(dao, presenter);
        interactor.execute(input);
    }
}
