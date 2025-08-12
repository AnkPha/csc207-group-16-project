package interface_adapter.main_menu;

import Search.ViewModel;

public class MainAppViewModel extends ViewModel<MainAppState> {

    public MainAppViewModel() {
        super("main app");
        this.setState(new MainAppState());
    }
}
