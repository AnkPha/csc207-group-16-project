package use_case.search;

import Search.InMemoryReviewDataAccessObject;
import Search.search_nearby_locations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.review.AddReviewAccessInterface;
import Search.SearchLocationNearbyDataAccessObject;

import static org.junit.jupiter.api.Assertions.*;

class SearchInteractorTest {
    private SearchLocationNearbyDataAccessObject mockDao;
    private SearchLocationsNearbyInteractor mockInteractor;
    private SearchLocationsNearbyOutputBoundary mockPresenter;
    private AddReviewAccessInterface reviewDao;
    private SearchViewModel mockViewModel;
    private SearchLocationsNearbyInputData mockInputOne;
    private SearchLocationsNearbyInputData mockInputTwo;
    private SearchLocationsNearbyInputData mockInputThree;
    private SearchLocationsNearbyInputData mockInputFour;
    private SearchLocationsNearbyInputData mockInputFive;

    @BeforeEach
    void setup() {
        mockViewModel = new SearchViewModel();
        reviewDao = new InMemoryReviewDataAccessObject();
        mockDao = new SearchLocationNearbyDataAccessObject(reviewDao);

        mockInputOne = new SearchLocationsNearbyInputData("1 Dundas St E, Toronto, Canada", 500);
        mockInputTwo = new SearchLocationsNearbyInputData("ghsjfb j rhenb", 500);

        mockInputThree = new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 100);

        mockInputFour = new SearchLocationsNearbyInputData("", 500);

        mockInputFive = new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 0);
    }

    @Test
    void testSuccessfulSearch() {

        mockPresenter = new SearchLocationsNearbyPresenter(mockViewModel) {
            @Override
            public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
                // Perform assertions here
                assertEquals(2, outputData.getStatus());
                assertTrue(outputData.getNearbyRestaurants().size() > 0);
                assertTrue(0.0 != outputData.getAddressCoords()[0]);
                assertTrue(0.0 != outputData.getAddressCoords()[1]);
            }
        };
        mockInteractor = new SearchLocationsNearbyInteractor(mockDao, mockPresenter);
        mockInteractor.execute(mockInputOne);
    }
    @Test
    void testAddressDoesNotExistSearch() {

        mockPresenter = new SearchLocationsNearbyPresenter(mockViewModel) {
            @Override
            public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
                // Perform assertions here
                assertEquals(1, outputData.getStatus());
                assertEquals(0, outputData.getNearbyRestaurants().size());
                assertEquals(0.0, outputData.getAddressCoords()[0]);
                assertEquals(0.0, outputData.getAddressCoords()[1]);
            }
        };
        mockInteractor = new SearchLocationsNearbyInteractor(mockDao, mockPresenter);
        mockInteractor.execute(mockInputTwo);
    }

    @Test
    void testGoodAddressBadRadiusSearch() {

        mockPresenter = new SearchLocationsNearbyPresenter(mockViewModel) {
            @Override
            public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
                // Perform assertions here
                assertEquals(2, outputData.getStatus());
                assertEquals(0, outputData.getNearbyRestaurants().size());
                assertTrue(0.0 != outputData.getAddressCoords()[0]);
                assertTrue(0.0 != outputData.getAddressCoords()[1]);
            }
        };
        mockInteractor = new SearchLocationsNearbyInteractor(mockDao, mockPresenter);
        mockInteractor.execute(mockInputThree);
    }

    @Test
    void testEmptyAddressTest() {

        mockPresenter = new SearchLocationsNearbyPresenter(mockViewModel) {
            @Override
            public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
                // Perform assertions here
                assertEquals(1, outputData.getStatus());
                assertEquals(0, outputData.getNearbyRestaurants().size());
                assertEquals(0.0, outputData.getAddressCoords()[0]);
                assertEquals(0.0, outputData.getAddressCoords()[1]);
            }
        };
        mockInteractor = new SearchLocationsNearbyInteractor(mockDao, mockPresenter);
        mockInteractor.execute(mockInputFour);
    }

    @Test
    void testZeroRadius() {

        mockPresenter = new SearchLocationsNearbyPresenter(mockViewModel) {
            @Override
            public void prepareSuccessView(SearchLocationsNearbyOutputData outputData) {
                // Perform assertions here
                assertEquals(2, outputData.getStatus());
                assertEquals(0, outputData.getNearbyRestaurants().size());
                assertTrue(0.0 != outputData.getAddressCoords()[0]);
                assertTrue(0.0 != outputData.getAddressCoords()[1]);
            }
        };
        mockInteractor = new SearchLocationsNearbyInteractor(mockDao, mockPresenter);
        mockInteractor.execute(mockInputFive);
    }
}
