package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addLoginView()
                                            .addSignupView()
                                            .addSearchViewModel()
                                            .addFilterViewModel()
                                            .addFavoritesViewModel()
                                            .addSendFriendRequestViewModel()
                                            .addFriendsUseCase()
                                            .addSendFriendRequestUseCase()
                                            .addReviewsViewModel()
                                            .addReviewsUseCase()
                                            .addMainAppView()
                                            .addSearchUseCase()
                                            .addFilterUseCase()
                                            .addSignupUseCase()
                                            .addFavoritesUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
        // "1 Dundas St E, Toronto, Canada",
        // 100 Queen St W, Toronto, ON, Canada
        // 100, 500

    }
}
