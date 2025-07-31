package interface_adapter.favorites_list;

import use_case.favorite_list.AddToFavoritesInputBoundary;
import use_case.favorite_list.AddToFavoritesInputData;
import use_case.favorite_list.RemoveFromFavoritesInputBoundary;
import use_case.favorite_list.RemoveFromFavoritesInputData;

public class FavoritesController {
    private final AddToFavoritesInputBoundary addToFavoritesUseCase;
    private final RemoveFromFavoritesInputBoundary removeFromFavoritesUseCase;

    public FavoritesController(AddToFavoritesInputBoundary addToFavoritesUseCase,
                               RemoveFromFavoritesInputBoundary removeFromFavoritesUseCase) {
        this.addToFavoritesUseCase = addToFavoritesUseCase;
        this.removeFromFavoritesUseCase = removeFromFavoritesUseCase;
    }

    public void addToFavorites(String userId, String restaurantName) {
        final AddToFavoritesInputData inputData = new AddToFavoritesInputData(userId, restaurantName);
        addToFavoritesUseCase.execute(inputData);
    }

    public void removeFromFavorites(String userId, String restaurantName) {
        final RemoveFromFavoritesInputData inputData = new RemoveFromFavoritesInputData(userId, restaurantName);
        removeFromFavoritesUseCase.execute(inputData);
    }
}
