package use_case.filter;

import data_access.FilterDataAccessObject;
import entity.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterInteractorTest {
    private FilterDataAccessObject dao;
    private List<Restaurant> restaurants;

    @BeforeEach
    void setUp() {
        dao = new FilterDataAccessObject();
        restaurants = new ArrayList<>();

        restaurants.add(new Restaurant("Red Lobster",
                "20 Dundas Street West",
                "seafood",
                "Not given",
                "Mo-Su 11:00-23:00",
                "Not given",
                43.6561655,
                -79.3836362));

        restaurants.add(new Restaurant("Yueh Tung Restaurant",
                "126 Elizabeth Street",
                "japanese",
                "yes",
                "Tu 11:30-16:00; We,Th 11:30-21:00; Fr 11:30-21:30; Sa 12:00-21:30; Su 12:00-21:00; Mo off",
                "https://www.yuehtungrestaurant.com/",
                43.6552238,
                -79.3852634));

        restaurants.add(new Restaurant("Cactus Club Cafe",
                "Adelaide Street West",
                "international",
                "Not given",
                "Not given",
                "Not given",
                43.6496578,
                -793811783));

        dao = new FilterDataAccessObject() {
            @Override
            public ArrayList<Restaurant> getFilteredRestaurants (FilterInputData filterInputData) {
                return super.getFilteredRestaurants(filterInputData);
            }
        };
    }

    @Test
    void filterByCuisineOnly() {
        SearchLocationsNearbyInputData locationInput =
                new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 500);
        List<String> selectedCuisines = List.of("japanese");

        FilterInputData inputOne = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisines, "None", "None", "None");

        FilterOutputBoundary outputOne = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals(10, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getCuisine().equals("japanese")));
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
        SearchLocationsNearbyInputData locationInput =
                new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 500);
        List<String> selectedCuisine = List.of("None");

        FilterInputData inputTwo = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisine, "None", "None", "None");

        FilterOutputBoundary outputTwo = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                Assertions.assertEquals("104", outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getRating().equals("Not given")));
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
        SearchLocationsNearbyInputData locationInput =
                new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 500);
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
        SearchLocationsNearbyInputData locationInput =
                new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 500);
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
        SearchLocationsNearbyInputData locationInput =
                new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 500);
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
        SearchLocationsNearbyInputData locationInput =
                new SearchLocationsNearbyInputData("100 Queen St W, Toronto, ON, Canada", 500);
        List<String> selectedCuisines = List.of("thai");

        FilterInputData input = new FilterInputData("100 Queen St W, Toronto, ON, Canada", 500,
                selectedCuisines, "yes", "Closed Now", "None");

        FilterOutputBoundary outputNone = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                assertTrue(outputData.getFilteredRestaurants().isEmpty());
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