package use_case.favorites;

import data_access.favorite_list.FavoritesDataAccessInterface;
import data_access.favorite_list.FavoritesDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.favorite_list.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoritesInteractorsTest {

    private FavoritesDataAccessObject dao;
    private AddToFavoritesPresenterDummy addPresenter;
    private RemoveFromFavoritesPresenterDummy removePresenter;
    private AddToFavoritesInteractor addInteractor;
    private RemoveFromFavoritesInteractor removeInteractor;

    @BeforeEach
    void setUp() {
        dao = new FavoritesDataAccessObject();
        FavoritesDataAccessInterface daoInterface = dao;
        addPresenter = new AddToFavoritesPresenterDummy();
        removePresenter = new RemoveFromFavoritesPresenterDummy();
        addInteractor = new AddToFavoritesInteractor(daoInterface, addPresenter);
        removeInteractor = new RemoveFromFavoritesInteractor(daoInterface, removePresenter);
    }

    @Test
    void testAddFavoriteSuccess() {
        AddToFavoritesInputData input = new AddToFavoritesInputData("John Doe", "Test Restaurant");
        addInteractor.execute(input);

        assertTrue(addPresenter.successCalled);
        assertFalse(addPresenter.errorCalled);
        assertTrue(dao.isFavorite("John Doe", "Test Restaurant"));
    }

    @Test
    void testAddDuplicateFavoriteTriggersError() {
        dao.addToFavorites("John Doe", "Test Restaurant");
        AddToFavoritesInputData input = new AddToFavoritesInputData("John Doe", "Test Restaurant");
        addInteractor.execute(input);

        assertTrue(addPresenter.errorCalled);
        assertFalse(addPresenter.successCalled);
    }

    @Test
    void testRemoveFavoriteSuccess() {
        dao.addToFavorites("John Doe", "Test Restaurant");
        RemoveFromFavoritesInputData input = new RemoveFromFavoritesInputData("John Doe", "Test Restaurant");
        removeInteractor.execute(input);

        assertTrue(removePresenter.successCalled);
        assertFalse(removePresenter.errorCalled);
        assertFalse(dao.isFavorite("John Doe", "Test Restaurant"));
    }

    @Test
    void testRemoveNonexistentFavoriteTriggersError() {
        RemoveFromFavoritesInputData input = new RemoveFromFavoritesInputData("John Doe", "Nonexistent");
        removeInteractor.execute(input);

        assertTrue(removePresenter.errorCalled);
        assertFalse(removePresenter.successCalled);
    }

    @Test
    void testAddToFavoritesDirectly() {
        dao.addToFavorites("John Doe", "Test Restaurant");

        assertTrue(dao.isFavorite("John Doe", "Test Restaurant"));
        assertEquals(1, dao.getFavorites("John Doe").size());
        assertEquals("Test Restaurant", dao.getFavorites("John Doe").get(0));
    }

    @Test
    void testRemoveFromFavoritesDirectly() {
        dao.addToFavorites("John Doe", "Test Restaurant");
        dao.addToFavorites("John Doe", "Test Restaurant 2");

        dao.removeFromFavorites("John Doe", "Test Restaurant 2");

        assertTrue(dao.isFavorite("John Doe", "Test Restaurant")); // still exists
        assertFalse(dao.isFavorite("John Doe", "Test Restaurant 2")); // removed
        assertEquals(1, dao.getFavorites("John Doe").size());
    }

    @Test
    void testGetFavorites() {
        dao.addToFavorites("John Doe", "Test Restaurant");

        List<String> favorites = dao.getFavorites("John Doe");

        assertTrue(favorites.contains("Test Restaurant"));
        assertFalse(favorites.contains("Test Restaurant 2")); // not added
    }

    @Test
    void testRemoveFromFavoritesForNonexistentUserDoesNotThrow() {
        assertDoesNotThrow(() -> dao.removeFromFavorites("John Bell", "resto999"));
    }

    @Test
    void testMultipleFavoritesAreStored() {
        dao.addToFavorites("John Doe", "Temp Restaurant");
        dao.addToFavorites("John Doe", "Temp Restaurant 2");

        List<String> favorites = dao.getFavorites("John Doe");
        assertTrue(favorites.contains("Temp Restaurant"));
        assertTrue(favorites.contains("Temp Restaurant 2"));
        assertEquals(2, favorites.size());
    }

    @Test
    void testFavoritesAreUserSpecific() {
        dao.addToFavorites("John Doe", "Tempura Restaurant");
        dao.addToFavorites("John Bell", "Ramen Restaurant");

        assertTrue(dao.isFavorite("John Doe", "Tempura Restaurant"));
        assertFalse(dao.isFavorite("John Doe", "Ramen Restaurant"));

        assertTrue(dao.isFavorite("John Bell", "Ramen Restaurant"));
        assertFalse(dao.isFavorite("John Bell", "Tempura Restaurant"));
    }

    static class AddToFavoritesPresenterDummy implements AddToFavoritesOutputBoundary {
        AddToFavoritesOutputData outputData;
        boolean successCalled = false;
        boolean errorCalled = false;

        @Override
        public void prepareSuccessView(AddToFavoritesOutputData outputData) {
            this.outputData = outputData;
            this.successCalled = true;
        }

        @Override
        public void prepareErrorView(AddToFavoritesOutputData outputData) {
            this.outputData = outputData;
            this.errorCalled = true;
        }
    }

    static class RemoveFromFavoritesPresenterDummy implements RemoveFromFavoritesOutputBoundary {
        RemoveFromFavoritesOutputData outputData;
        boolean successCalled = false;
        boolean errorCalled = false;

        @Override
        public void prepareSuccessView(RemoveFromFavoritesOutputData outputData) {
            this.outputData = outputData;
            this.successCalled = true;
        }

        @Override
        public void prepareErrorView(RemoveFromFavoritesOutputData outputData) {
            this.outputData = outputData;
            this.errorCalled = true;
        }
    }
}