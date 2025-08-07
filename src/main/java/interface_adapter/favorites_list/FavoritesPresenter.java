package interface_adapter.favorites_list;

import java.util.ArrayList;
import java.util.List;

import use_case.favorite_list.AddToFavoritesOutputBoundary;
import use_case.favorite_list.AddToFavoritesOutputData;
import use_case.review.AddReviewOutputBoundary;
import use_case.favorite_list.RemoveFromFavoritesOutputBoundary;
import use_case.favorite_list.RemoveFromFavoritesOutputData;

public class FavoritesPresenter implements AddToFavoritesOutputBoundary {
    private final FavoritesViewModel favoritesViewModel;

    public FavoritesPresenter(FavoritesViewModel favoritesViewModel) {
        this.favoritesViewModel = favoritesViewModel;
    }

    @Override
    public void prepareSuccessView(AddToFavoritesOutputData outputData) {
        final FavoritesState currentState = favoritesViewModel.getState();

        final List<String> updatedFavorites = new ArrayList<>(currentState.getFavoriteList());
        final String newFavoriteItem = outputData.getRestaurantId();

        if (!updatedFavorites.contains(newFavoriteItem)) {
            updatedFavorites.add(newFavoriteItem);
        }

        currentState.setFavoriteList(updatedFavorites);
        currentState.setErrorMessage("");

        favoritesViewModel.setState(currentState);
        favoritesViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(RemoveFromFavoritesOutputData outputData) {
        final FavoritesState currentState = favoritesViewModel.getState();

        final List<String> updatedFavorites = new ArrayList<>(currentState.getFavoriteList());
        final String restaurantToRemove = outputData.getRestaurantName();

        updatedFavorites.remove(restaurantToRemove);

        currentState.setFavoriteList(updatedFavorites);
        currentState.setErrorMessage("");

        favoritesViewModel.setState(currentState);
        favoritesViewModel.firePropertyChanged();
    }

    @Override
    public void prepareErrorView(AddToFavoritesOutputData outputData) {
        final FavoritesState currentState = favoritesViewModel.getState();
        currentState.setErrorMessage("Failed to add restaurant to favorites");

        favoritesViewModel.setState(currentState);
        favoritesViewModel.firePropertyChanged();
    }

    @Override
    public void prepareErrorView(RemoveFromFavoritesOutputData outputData) {
        final FavoritesState currentState = favoritesViewModel.getState();
        currentState.setErrorMessage("Failed to remove restaurant from favorites");

        favoritesViewModel.setState(currentState);
        favoritesViewModel.firePropertyChanged();
    }
}
