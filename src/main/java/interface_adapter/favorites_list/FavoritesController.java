package interface_adapter.favorites_list;

import use_case.favorite_list.AddToFavoritesInputBoundary;
import use_case.favorite_list.AddToFavoritesInputData;

public class FavoritesController {
    private final AddToFavoritesInputBoundary addToFavoritesUseCase;

    public FavoritesController(AddToFavoritesInputBoundary addToFavoritesUseCase) {
        this.addToFavoritesUseCase = addToFavoritesUseCase;
    }

    public void addToFavorites(String userId, String restaurantId) {
        final AddToFavoritesInputData inputData = new AddToFavoritesInputData(userId, restaurantId);
        addToFavoritesUseCase.execute(inputData);
    }
}
