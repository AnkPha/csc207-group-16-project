package interface_adapter.friends;

import interface_adapter.ViewModel;

public class FriendsViewModel extends ViewModel<FriendsState> {
    public FriendsViewModel() {
        super("friends");
        setState(new FriendsState());
    }
}
