import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage window;
    @Override
    public void start(Stage primaryStage){
        window = primaryStage;

        MainPane pane = new MainPane();
        
        Scene scene = new Scene(pane, 900, 900);
        window.setScene(scene);
        window.setMinWidth(850);
        window.setMinHeight(800);
        window.setTitle("MediaPlayer");
        window.show();
    }

    public static Stage getStage(){ // fileChooser ге керек
        return window;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
