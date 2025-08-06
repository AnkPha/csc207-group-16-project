package use_case.searchlocation;

import data_access.InMemorySearchLocationNearbyDAO;
import entity.CommonRestaurantFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchLocationsNearbyInteractorTest {

    private InMemorySearchLocationNearbyDAO dao;

    @BeforeEach
    void setUp() {
        dao = new InMemorySearchLocationNearbyDAO(new CommonRestaurantFactory());
        dao.populateDummyData(); // Assume this adds sample locations like "McDonald's", "Tim Hortons"
    }

    @Test
    void testSearchNearbyReturnsResults() {
        SearchLocationNearbyOutputBoundary presenter = output -> {
            assertFalse(output.getRestaurants().isEmpty());
            assertTrue(output.getRestaurants().stream().anyMatch(r -> r.getName().contains("Tim")));
        };

        SearchLocationsNearbyInteractor interactor = new SearchLocationsNearbyInteractor(dao, presenter);
        interactor.execute(new SearchLocationNearbyInputData("Toronto", 1000));
    }

    @Test
    void testSearchNearbyReturnsEmpty() {
        SearchLocationNearbyOutputBoundary presenter = output ->
                assertTrue(output.getRestaurants().isEmpty());

        SearchLocationsNearbyInteractor interactor = new SearchLocationsNearbyInteractor(dao, presenter);
        interactor.execute(new SearchLocationNearbyInputData("NowhereLand", 1000));
    }
}
