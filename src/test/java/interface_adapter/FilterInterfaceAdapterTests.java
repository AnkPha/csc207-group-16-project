package interface_adapter.filter;

import entity.Restaurant;
import org.junit.jupiter.api.Test;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterInterfaceAdapterTests {

    // FilterState Tests
    @Test
    void filterStateGetSetTest() {
        FilterState state = new FilterState();
        state.getCuisine().add("Italian");
        assertTrue(state.getCuisine().contains("Italian"));

        state.setVegStat("yes");
        assertEquals("yes", state.getVegStat());

        state.setOpeningHours("Mo-Fr 10:00-22:00");
        assertEquals("Mo-Fr 10:00-22:00", state.getOpeningHours());

        state.setAvailability("Open Now");
        assertEquals("Open Now", state.getAvailability());

        state.setRating("4");
        assertEquals("4", state.getRating());

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        state.setRestaurants(restaurants);
        assertSame(restaurants, state.getRestaurants());
    }

    // FilterPresenter Tests

    @Test
    void filterPresenterPrepareSuccessViewTest() {
        FilterViewModel viewModel = new FilterViewModel();
        FilterPresenter presenter = new FilterPresenter(viewModel);

        ArrayList<Restaurant> testList = new ArrayList<>();
        FilterOutputData outputData = new FilterOutputData(testList);

        presenter.prepareSuccessView(outputData);

        assertSame(testList, viewModel.getState().getRestaurants());
    }

    @Test
    void filterPresenterPrepareFailViewTest() {
        final boolean[] errorShown = {false};
        FilterViewModel viewModel = new FilterViewModel() {
            @Override
            public void showError(String errorMessage) {
                errorShown[0] = true;
                assertEquals("Error message", errorMessage);
            }
        };

        FilterPresenter presenter = new FilterPresenter(viewModel);
        presenter.prepareFailView("Error message");

        assertTrue(errorShown[0]);
    }

    // FilterController Test

    @Test
    void filterControllerExecuteTest() {
        FilterInputBoundary fakeUseCase = filterInputData -> {
            assertEquals("Address", filterInputData.getAddress());
            assertEquals(100, filterInputData.getRadius());
            assertEquals(List.of("indian"), filterInputData.getCuisine());
            assertEquals("yes", filterInputData.getVegStat());
            assertEquals("Open Now", filterInputData.getAvailability());
            assertEquals("4", filterInputData.getRating());
        };

        FilterController controller = new FilterController(fakeUseCase);
        controller.execute("Address", 100, List.of("indian"), "yes", "Open Now", "4");
    }

    // FilterViewModel Tests

    @Test
    void filterViewModelSettersTest() {
        FilterViewModel vm = new FilterViewModel();

        vm.setCuisine(List.of("italian"));
        assertTrue(vm.getState().getCuisine().contains("italian"));

        vm.setVegStat("yes");
        assertEquals("yes", vm.getState().getVegStat());

        vm.setOpeningHours("Mo-Fr 10:00-22:00");
        assertEquals("Mo-Fr 10:00-22:00", vm.getState().getOpeningHours());

        vm.setAvailability("Open Now");
        assertEquals("Open Now", vm.getState().getAvailability());

        vm.setRating("5");
        assertEquals("5", vm.getState().getRating());
    }

    @Test
    void filterViewModelSetGetRestaurantsTest() {
        FilterViewModel vm = new FilterViewModel();
        Restaurant r = new Restaurant("Test", "Addr", "cuisine", "yes", "24/7", "url", new double[]{0,0});
        vm.setRestaurants(List.of(r));
        assertEquals(1, vm.getRestaurants().size());
        assertSame(r, vm.getRestaurants().get(0));
    }

    @Test
    void filterViewModelShowErrorTest() {
        FilterViewModel vm = new FilterViewModel();
        vm.showError("Test error");
    }

    @Test
    void filterViewModelSetStateTest() {
        FilterViewModel vm = new FilterViewModel();
        FilterState newState = new FilterState();
        vm.setState(newState);
        assertSame(newState, vm.getState());
    }
}

