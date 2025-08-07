package interface_adapter.favorites_list;

import use_case.favorite_list.AddToFavoritesInputBoundary;
import use_case.favorite_list.AddToFavoritesInputData;
import use_case.favorite_list.RemoveFromFavoritesInputBoundary;
import use_case.favorite_list.RemoveFromFavoritesInputData;

public class FavoritesController {
    private final AddToFavoritesInputBoundary addToFavoritesInteractor;
    private final RemoveFromFavoritesInputBoundary removeFromFavoritesInteractor;

    public FavoritesController(AddToFavoritesInputBoundary addToFavoritesInteractor,
                               RemoveFromFavoritesInputBoundary removeFromFavoritesInteractor) {
        this.addToFavoritesInteractor = addToFavoritesInteractor;
        this.removeFromFavoritesInteractor = removeFromFavoritesInteractor;
    }

    public void addToFavorites(String username, String restaurantId) {
        AddToFavoritesInputData inputData = new AddToFavoritesInputData(username, restaurantId);
        addToFavoritesInteractor.execute(inputData);
    }

    public void removeFromFavorites(String username, String restaurantId) {
        RemoveFromFavoritesInputData inputData = new RemoveFromFavoritesInputData(username, restaurantId);
        removeFromFavoritesInteractor.execute(inputData);
    }
}


