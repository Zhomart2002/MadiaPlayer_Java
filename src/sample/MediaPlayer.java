package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.UserInterface.IUserInterface;
import sample.UserInterface.UserInterface;

public class MediaPlayer extends Application {

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        IUserInterface ui = new UserInterface(stage);
    }
}
