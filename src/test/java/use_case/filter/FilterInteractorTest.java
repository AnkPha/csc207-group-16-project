package use_case.filter;

import data_access.FilterDataAccessObject;
import Search.InMemoryReviewDataAccessObject;
import Search.Restaurant;
import entity.Review;
import entity.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.review.AddReviewInteractor;
import use_case.review.AddReviewInputData;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterInteractorTest {
    private FilterDataAccessObject dao;

    @BeforeEach
    void setUp() {
        InMemoryReviewDataAccessObject inMemoryReviewDao = new InMemoryReviewDataAccessObject();
        dao = new FilterDataAccessObject(inMemoryReviewDao);

        AddReviewInteractor addReviewInteractor = getAddReviewInteractor(inMemoryReviewDao);

        final double[] redlobsterCords = new double[] {-79.3836362, 43.6561655};
        final double[] sunsetgrillCords = new double[] {-79.3790842, 43.6515163};

        Restaurant redLobster = new Restaurant("Red Lobster", "seafood", "website",
                "Mo-Su 10:00-22:00", "yes", "No Ratings", redlobsterCords);
        Restaurant sunsetGrill = new Restaurant("Sunset Grill", "breakfast", "website",
                "Mo-Su 7:00-15:00", "yes", "No Ratings", sunsetgrillCords);

        User testUser = new User() {
            @Override
            public String getName() {
                return "";
            }
            @Override
            public String getPassword() {
                return "";
            }
        };

        addReviewInteractor.execute(new AddReviewInputData(5, redLobster, testUser));
        addReviewInteractor.execute(new AddReviewInputData(2, sunsetGrill, testUser));
    }

    @NotNull
    private static AddReviewInteractor getAddReviewInteractor(InMemoryReviewDataAccessObject inMemoryReviewDao) {
        final int[] reviewIdCounter = {1};

        return new AddReviewInteractor(
                inMemoryReviewDao,
                new DummyReviewPresenter(),
                (user, restaurant, rating) -> {
                    int reviewId = reviewIdCounter[0]++;
                    return new Review(
                            restaurant,
                            reviewId,
                            rating,
                            LocalDateTime.now(),
                            user,
                            "test test"
                    );
                }
        );
    }


    @Test
    void filterByCuisineOnly() {
        List<String> selectedCuisines = List.of("japanese");

        FilterInputData inputOne = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisines, "None", "None", "None");

        FilterOutputBoundary outputOne = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                assertEquals(10, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getCuisine().equals("sushi;japanese")));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputOne);
        interactor.execute(inputOne);
    }

    @Test
    void filterByRatingOnly() {
        List<String> selectedCuisine = List.of("None");

        FilterInputData inputTwo = new FilterInputData("100 Queen St W, Toronto, ON, Canada",
                500, selectedCuisine, "None", "None", "2.0");

        FilterOutputBoundary outputTwo = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                assertEquals(2, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getName().equals("Red Lobster")));
                assertTrue(outputData.getFilteredRestaurants().stream().anyMatch(restaurant ->
                        outputData.getFilteredRestaurants().get(1).getName().equals("Sunset Grill")));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure to Filter Restaurants" + errorMessage);
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
                assertEquals(5, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant -> outputData.getFilteredRestaurants().get(0).getVegStat()
                                .equals("yes")));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure to Filter Restaurants" + errorMessage);
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
                assertEquals(104, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant ->
                                outputData.getFilteredRestaurants().get(0).getOpeningHours()
                                        .equals("Mo-Su 11:00-23:00")));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure to Filter Restaurants" + errorMessage);
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
                assertEquals(2, outputData.getFilteredRestaurants().size());
                assertTrue(outputData.getFilteredRestaurants().stream()
                        .anyMatch(restaurant -> outputData.getFilteredRestaurants().get(0).getOpeningHours().
                                equals("Mo-Fr 11:30-21:30; Sa 12:00-21:00")));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure to Filter Restaurants" + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputFour);
        interactor.execute(inputFour);
    }

    /**
     * The availability option depends upon the time of when the search is made.
     * As this restaurant is bound by opening hours {Mo-Fr 11:30-21:30; Sa 12:00-21:00}, this test
     * will fail when ran during these times.
     * So, this test case is likely to fail outside the relative time.
     */
    @Test
    void noMatchingRestaurants() {
        List<String> selectedCuisines = List.of("thai");

        FilterInputData input = new FilterInputData("100 Queen St W, Toronto, ON, Canada", 500,
                selectedCuisines, "yes", "Open Now", "None");

        FilterOutputBoundary outputNone = new FilterOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterOutputData outputData) {
                assertEquals(0, outputData.getFilteredRestaurants().size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure to Filter Restaurants: " + errorMessage);
            }
        };
        FilterInputBoundary interactor = new FilterInteractor(dao, outputNone);
        interactor.execute(input);
    }

    // Dummy presenters to satisfy constructor requirements without behavior

    private static class DummyReviewPresenter implements use_case.review.AddReviewOutputBoundary {
        @Override
        public void prepareSuccessView(use_case.review.AddReviewOutputData outputData) { }
        @Override
        public void prepareFailView(String errorMessage) { }
    }
}
