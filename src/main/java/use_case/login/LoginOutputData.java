package use_case.login;

import entity.User;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final User user;

    public LoginOutputData(String username, boolean useCaseFailed, User user) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.user = user;
    }

    public LoginOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.user = null;
    }

    public String getUsername() {
        return username;
    }

    public User getUser() {
        return user;
    }
}
