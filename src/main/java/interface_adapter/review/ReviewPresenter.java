package interface_adapter.review;

import use_case.review.AddReviewOutputBoundary;
import use_case.review.AddReviewOutputData;

public class ReviewPresenter implements AddReviewOutputBoundary {

    private ReviewViewModel reviewViewModel;

    public ReviewPresenter(ReviewViewModel reviewViewModel) {
        this.reviewViewModel = reviewViewModel;
    }

    @Override
    public void prepareSuccessView(AddReviewOutputData outputData) {
        final ReviewState state = reviewViewModel.getState();
        state.setSuccess(true);
        state.setSuccessMessage("Review added successfully!");
        state.setErrorMessage(null);
        reviewViewModel.setState(state);
        reviewViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ReviewState state = reviewViewModel.getState();
        state.setSuccess(false);
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage(null);
        reviewViewModel.setState(state);
        reviewViewModel.firePropertyChanged();
    }
}

