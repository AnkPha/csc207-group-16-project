package interface_adapter.send_friend_request;

import Search.ViewModel;

public class SendFriendRequestViewModel extends ViewModel<SendFriendRequestState> {
    public SendFriendRequestViewModel() {
        super("send_friend_request");
        setState(new SendFriendRequestState());
    }
}
