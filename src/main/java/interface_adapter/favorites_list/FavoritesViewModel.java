package interface_adapter.favorites_list;

import interface_adapter.ViewModel;

public class FavoritesViewModel extends ViewModel<FavoritesState> {
    public FavoritesViewModel() {
        super("favorites");
        setState(new FavoritesState());
    }
}
