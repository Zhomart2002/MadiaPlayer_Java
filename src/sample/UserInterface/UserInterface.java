package sample.UserInterface;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.PlayerUI.PlayerUI;

public class UserInterface implements IUserInterface {
    private final Stage stage;

    private PlayerUI pane;

    private final double WINDOW_WIDTH = 850;
    private final double WINDOW_HEIGHT = 800;
    private final String WINDOW_TITLE = "Media player";

    public UserInterface(Stage stage) {
        this.stage = stage;
        initialize();
    }

    private void initialize() {
        pane = new PlayerUI();
        Scene scene = new Scene(pane.getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);
        this.stage.setScene(scene);
        this.stage.setTitle(WINDOW_TITLE);
        this.stage.show();
    }
}
