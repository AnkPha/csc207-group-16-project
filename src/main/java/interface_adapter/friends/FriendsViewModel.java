package interface_adapter.friends;

import Search.ViewModel;

public class FriendsViewModel extends ViewModel<FriendsState> {
    public FriendsViewModel() {
        super("friends");
        setState(new FriendsState());
    }
}
