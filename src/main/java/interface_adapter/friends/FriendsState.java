package interface_adapter.friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsState {
    private String searchQuery = "";
    private List<String> friendList = new ArrayList<>();
    private List<String> friendRequests = new ArrayList<>();

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
