package use_case.friends;

public class RespondToRequestOutputData {
    private final String message;

    public RespondToRequestOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
