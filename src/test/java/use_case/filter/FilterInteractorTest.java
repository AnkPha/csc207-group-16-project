package use_case.filter;

import data_access.FilterDataAccessObject;
import data_access.InMemoryReviewDataAccessObject;
import entity.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterInteractorTest {
    private FilterDataAccessObject dao;
    private InMemoryReviewDataAccessObject inMemoryReviewDao = new InMemoryReviewDataAccessObject();


    @BeforeEach
    void setUp() {
        dao = new FilterDataAccessObject(inMemoryReviewDao);

        dao = new FilterDataAccessObject(inMemoryReviewDao) {
            @Override
            public ArrayList<Restaurant> getFilteredRestaurants (FilterInputData filterInputData) {
                return super.getFilteredRestaurants(filterInputData);
            }
        };
    }

    @Test
    void filterByCuisineOnly() {
        List<String> selectedCuisines = List.of("japanese");

        FilterInputData inputOne = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisines, "None", "None", "None");

        FilterOutputBoundary outputOne = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals(10, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getCuisine().equals("sushi;japanese")));
            }
            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputOne);
        interactor.execute(inputOne);
    }

    @Test
    void filterByRatingOnly() {
        List<String> selectedCuisine = List.of("None");

        FilterInputData inputTwo = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisine, "None", "None", "None");

        FilterOutputBoundary outputTwo = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals(104, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getRating().equals("No Ratings")));
            }
            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputTwo);
        interactor.execute(inputTwo);
    }

    @Test
    void filterByVegStatOnly() {
        List<String> selectedCuisine = List.of("None");

        FilterInputData inputThree = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisine, "yes", "None", "None");

        FilterOutputBoundary outputThree = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals(5, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant -> outputData.getFilteredRestaurants().get(0).getVegStat()
                                .equals("yes")));
            }
            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputThree);
        interactor.execute(inputThree);
    }

    @Test
    void filterByAvailabilityOnly() {
        List<String> selectedCuisine = List.of("None");

        FilterInputData inputFour = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisine, "None", "None", "None");

        FilterOutputBoundary outputFour = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals(104, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getOpeningHours()
                                        .equals("Mo-Su 11:00-23:00")));
            }
            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputFour);
        interactor.execute(inputFour);
    }

    @Test
    void filterByAll() {
        List<String> selectedCuisine = List.of("indian");

        FilterInputData inputFour = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisine, "yes", "None", "None");

        FilterOutputBoundary outputFour = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals(2, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant -> outputData.getFilteredRestaurants().get(0).getOpeningHours().
                                equals("Mo-Fr 11:30-21:30; Sa 12:00-21:00")));
            }
            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputFour);
        interactor.execute(inputFour);
    }

    @Test
    void noMatchingRestaurants() {
        List<String> selectedCuisines = List.of("thai");

        FilterInputData input = new FilterInputData("100 Queen St W, Toronto, ON, Canada", 500,
                selectedCuisines, "yes", "Closed Now", "None");

        FilterOutputBoundary outputNone = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                assertEquals(0, outputData.getFilteredRestaurants().size());
            }
            @Override
            public void prepareFailView(String errorMessage) {
                Assertions.fail("Failure to Filter Restaurants: " + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputNone);
        interactor.execute(input);
    }
}