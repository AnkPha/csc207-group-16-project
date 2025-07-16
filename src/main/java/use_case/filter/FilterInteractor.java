package use_case.filter;

import entity.User;
import entity.UserFactory;

/**
 * The Change Password Interactor.
 */
public class FilterInteractor implements FilterInputBoundary {
    private final FilterUserDataAccessInterface filterConceptsDataAccessObject;
    private final FilterOutputBoundary userPresenter;
//  private final UserFactory userFactory;
    /// not done

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
        this.userFactory = userFactory;
    }
    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        final User user = userFactory.create(changePasswordInputData.getUsername(),
                                             changePasswordInputData.getPassword());
        userDataAccessObject.changePassword(user);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(user.getName(),
                                                                                  false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
