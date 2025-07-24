package use_case.friends;

import java.util.List;

public class ViewRequestsOutputData {
    private final List<String> pendingUsernames;

    public ViewRequestsOutputData(List<String> pendingUsernames) {
        this.pendingUsernames = pendingUsernames;
    }

    public List<String> getPendingUsernames() {
        return pendingUsernames;
    }
}
