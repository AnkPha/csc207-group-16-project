package interface_adapter.review;

public class ReviewState {

    private boolean success = false;
    private String errorMessage = null;
    private String successMessage = null;

    public ReviewState() {
        this.success = true;
        this.errorMessage = null;
        this.successMessage = null;
        }

        public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getSuccessMessage() { return successMessage; }
    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

}
