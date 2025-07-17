package interface_adapter.filter;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

/**
 * The View Model for the Login View.
 */
public class FilterViewModel extends ViewModel<LoginState> {

    public FilterViewModel() {
        super("Filter");
        setState(new LoginState());
    }

}
