package use_case.favorite_list;

import data_access.favorite_list.FavoritesDataAccessInterface;

public class AddToFavoritesInteractor implements AddToFavoritesInputBoundary {
    private final FavoritesDataAccessInterface dataAccess;
    private final AddToFavoritesOutputBoundary presenter;

    public AddToFavoritesInteractor(FavoritesDataAccessInterface dataAccess,
                                    AddToFavoritesOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddToFavoritesInputData inputData) {
        final AddToFavoritesOutputData outputData = new AddToFavoritesOutputData(
                inputData.getUsername(),
                inputData.getRestaurantId()
        );

        if (dataAccess.isFavorite(inputData.getUsername(), inputData.getRestaurantId())) {
            presenter.prepareErrorView(outputData);
        }
        else {
            dataAccess.addToFavorites(inputData.getUsername(), inputData.getRestaurantId());
            presenter.prepareSuccessView(outputData);
        }
    }
}
