package use_case.filter;

import entity.User;

/**
 * The interface of the DAO for the Filter Use Case.
 */
public interface FilterUserDataAccessInterface {

    /**
     * Updates the system to filter using this user's preference.
     * @param user the user whose password is to be updated
     */
    void updateCuisine(User user);
    void updateAllergen(User user);
    void updateHoursOfOperations(User user);
    void updateRating (User user);
    void updateNutrition (User user);
}