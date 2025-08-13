package use_case.review;

import data_access.InMemoryReviewDataAccessObject;
import entity.CommonUser;
import entity.Restaurant;
import entity.Review;
import entity.TestReviewFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddReviewInteractorTest {
    private InMemoryReviewDataAccessObject reviewDao;
    private AddReviewInteractor interactor;
    private CommonUser testUser;
    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        reviewDao = new InMemoryReviewDataAccessObject();
        testUser = new CommonUser("testuser", "password");

        double[] coords = {43.6532, -79.3832};
        testRestaurant = new Restaurant(
                "Test Restaurant",
                "123 Test Street",
                "Italian",
                "Vegetarian Options",
                "11AM-11PM",
                "https://testrestaurant.com",
                coords
        );
    }

    @Test
    void testExecuteSuccessfulReviewAddition() {
        TestSuccessPresenter presenter = new TestSuccessPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(4, testRestaurant, testUser);
        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.outputData);

        assertTrue(presenter.outputData.isSuccess());
        assertNotNull(presenter.outputData.getReview());
        assertNull(presenter.outputData.getErrorMessage());

        Review outputReview = presenter.outputData.getReview();
        assertEquals(testUser, outputReview.getUser());
        assertEquals(testRestaurant, outputReview.getRestaurant());
        assertEquals(4, outputReview.getRating());

        assertTrue(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
        assertEquals(1, reviewDao.getRatingsForRestaurant(testRestaurant).size());
        assertEquals(4.0, reviewDao.getAverageRatingForRestaurant(testRestaurant), 0.1);
    }

    @Test
    void testExecuteWithInvalidRating() {
        TestFailPresenter presenter = new TestFailPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(0, testRestaurant, testUser);
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Invalid review data:"));
        assertFalse(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
    }

    @Test
    void testExecuteWithInvalidHighRating() {
        TestFailPresenter presenter = new TestFailPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(6, testRestaurant, testUser);
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Invalid review data:"));
        assertFalse(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
    }

    @Test
    void testExecuteWithNegativeRating() {
        TestFailPresenter presenter = new TestFailPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(-1, testRestaurant, testUser);
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Invalid review data:"));
        assertFalse(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
    }

    @Test
    void testExecuteMultipleValidRatings() {
        TestReviewFactory reviewFactory = new TestReviewFactory();
        for (int rating = 1; rating <= 5; rating++) {
            CommonUser user = new CommonUser("user" + rating, "password");

            TestSuccessPresenter presenter = new TestSuccessPresenter();
            interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

            AddReviewInputData input = new AddReviewInputData(rating, testRestaurant, user);
            interactor.execute(input);

            assertTrue(presenter.successCalled);
            assertFalse(presenter.failCalled);

            assertTrue(presenter.outputData.isSuccess());
            assertNotNull(presenter.outputData.getReview());
            assertNull(presenter.outputData.getErrorMessage());
            assertEquals(rating, presenter.outputData.getReview().getRating());

            assertTrue(reviewDao.existsReviewByUserAndRestaurant(user, testRestaurant));
        }

        assertEquals(5, reviewDao.getRatingsForRestaurant(testRestaurant).size());
        assertEquals(3.0, reviewDao.getAverageRatingForRestaurant(testRestaurant), 0.1);
    }

    @Test
    void testExecuteWithNullUser() {
        TestFailPresenter presenter = new TestFailPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(4, testRestaurant, null);
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Invalid review data:"));
    }

    @Test
    void testExecuteWithNullRestaurant() {
        TestFailPresenter presenter = new TestFailPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(4, null, testUser);
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Invalid review data:"));
    }

    @Test
    void testExecuteWithMinRating() {
        TestSuccessPresenter presenter = new TestSuccessPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(1, testRestaurant, testUser);
        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertTrue(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
    }

    @Test
    void testExecuteWithMaxRating() {
        TestSuccessPresenter presenter = new TestSuccessPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(5, testRestaurant, testUser);
        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertTrue(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
    }

    // Test helper classes
    private static class TestSuccessPresenter implements AddReviewOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        AddReviewOutputData outputData = null;

        @Override
        public void prepareSuccessView(AddReviewOutputData outputData) {
            this.successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failCalled = true;
        }
    }

    private static class TestFailPresenter implements AddReviewOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        String errorMessage = null;

        @Override
        public void prepareSuccessView(AddReviewOutputData outputData) {
            this.successCalled = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failCalled = true;
            this.errorMessage = errorMessage;
        }
    }

    @Test
    void testExecuteWithInvalidRatingOutputData() {
        TestFailPresenter presenter = new TestFailPresenter();
        TestReviewFactory reviewFactory = new TestReviewFactory();
        interactor = new AddReviewInteractor(reviewDao, presenter, reviewFactory);

        AddReviewInputData input = new AddReviewInputData(0, testRestaurant, testUser);
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Invalid review data:"));

        assertFalse(reviewDao.existsReviewByUserAndRestaurant(testUser, testRestaurant));
    }
}
