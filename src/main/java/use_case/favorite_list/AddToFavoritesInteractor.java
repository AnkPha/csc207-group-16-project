package use_case.favorite_list;

import data_access.favorite_list.FavoritesDataAccessInterface;

public class AddToFavoritesInteractor implements AddToFavoritesInputBoundary{
    private final FavoritesDataAccessInterface dataAccess;

    public AddToFavoritesInteractor(FavoritesDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(AddToFavoritesInputData inputData) {
        dataAccess.addToFavorites(inputData.getUsername(), inputData.getRestaurantId());
    }
}
