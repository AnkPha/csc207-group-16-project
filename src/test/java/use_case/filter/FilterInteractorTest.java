package use_case.filter;

import data_access.FilterDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.SearchLocationNearbyDataAccessObject;
import entity.CommonUserFactory;
import entity.Restaurant;
import entity.User;
import entity.UserFactory;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import org.junit.jupiter.api.Test;
import use_case.login.*;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;
import use_case.search_nearby_locations.SearchLocationsNearbyInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FilterInteractorTest {

    public static class MockSearchDAO extends SearchLocationNearbyDataAccessObject {
        private final ArrayList<Restaurant> mockRestaurants;

        public MockSearchDAO(ArrayList<Restaurant> mockRestaurants) {
            this.mockRestaurants = mockRestaurants;
        }

        @Override
        public ArrayList<Restaurant> getNearbyRestaurants(String address, int radius) {
            return mockRestaurants;
        }
    }

    @Test
    void successTest() {
        SearchLocationsNearbyInputData s = new SearchLocationsNearbyInputData("100 University Avenue", 500);
        List<String> c = List.of(new String[]{"Korean", "Italian"});
        FilterInputData inputData = new FilterInputData(s , c,"", "Open", "3");
        FilterDataAccessInterface daoFilter = new FilterDataAccessObject();

        // For the success test, we need to search the address and radius.
        SearchLocationsNearbyController s = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successUserLoggedInTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        assertEquals(null, userRepository.getCurrentUsername());

        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For this failure test, we need to add Paul to the data access repository before we log in, and
        // the passwords should not match.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Paul: Account does not exist.", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}