package interface_adapter.main_menu;

import interface_adapter.ViewModel;

public class MainAppViewModel extends ViewModel<MainAppState> {

    public MainAppViewModel() {
        super("main app");
        this.setState(new MainAppState());
    }
}
