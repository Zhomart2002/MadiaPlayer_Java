import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class MainPane extends BorderPane {
    BottomBar bottomBar;

    // for media
    private Media media;
    private MediaPlayer player;
    private final MediaView mediaView = new MediaView();

    // for list of medias
    private TableView<File> tableView;

    // for fileChooser
    private File clickedFile;

    public MainPane(){
    // Bottom side
        bottomBar = new BottomBar();
        setBottom(bottomBar);
        bottomBar.getOpenFile().setOnAction(e -> {
            tableView.setItems(bottomBar.getSelectedFiles());
            if (tableView.itemsProperty().isNotNull().get())
                playMedia(bottomBar.getNext());
            tableView.getSelectionModel().select(0);

        });
    // Right side
        creteTableView();
        VBox vbox = new VBox(3);
        vbox.setStyle("-fx-background-color: white");
        vbox.setMaxHeight(Double.MAX_VALUE);
        vbox.setPadding(new Insets(0, 2, 0, 2));
        vbox.getChildren().addAll(tableView, createAutoSize(), createMute(), createRepeat());
        setRight(vbox);

    // Center media side
        setCenter(mediaView);
        mediaView.fitWidthProperty().bind(widthProperty().subtract(((VBox)getRight()).widthProperty()));
        mediaView.fitHeightProperty().bind(heightProperty().subtract(((HBox)getBottom()).heightProperty()));

        bottomBar.getNextMedia().setOnAction(event -> {
            File file;
            if ((file = bottomBar.getNext()) !=  null){
                playMedia(file);
                tableView.getSelectionModel().select(bottomBar.currentIndex);
            }
        });
        bottomBar.getPrevMedia().setOnAction(event -> {
            File file;
            if ((file = bottomBar.getPrev()) != null){
                playMedia(file);
                tableView.getSelectionModel().select(bottomBar.currentIndex);
            }
        });

    }

    public void creteTableView(){
        tableView = new TableView<>();
        tableView.setPlaceholder(new Label("No media selected"));
        tableView.setPrefHeight(600);
        TableColumn<File, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(nameColumn);
        tableView.setOnMousePressed(event -> {
            if (event.getClickCount() == 2 && (clickedFile = tableView.getSelectionModel().getSelectedItem()) != null){
                playMedia(clickedFile);
                bottomBar.currentIndex = tableView.getSelectionModel().getSelectedIndex();
            }
        });
    }
    public CheckBox createAutoSize(){
        CheckBox mute = new CheckBox("set video size");
        mute.setOnAction(event -> {
            if (player != null){
                if (mute.isSelected()) {
                    Main.getStage().setWidth(media.getWidth());
                    Main.getStage().setHeight(media.getHeight());
                    Main.getStage().setResizable(false);
                }
                else{
                    Main.getStage().setResizable(true);
                }

            }
        });
        return mute;
    }
    public CheckBox createMute(){
        CheckBox mute = new CheckBox("Mute");
        mute.setOnAction(event -> {
            if (player != null){
                player.setMute(mute.isSelected());
            }
        });
        return mute;
    }
    public CheckBox createRepeat(){
        CheckBox mute = new CheckBox("Repeat");
        mute.setOnAction(event -> {
            if (player != null){
                bottomBar.setRepeat(mute.isSelected());
            }
        });
        return mute;
    }

    public void playMedia(File file){
        cleanMemory();
        media =  new Media(file.toURI().toString());
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        bottomBar.setMediaProperties(player);
        player.play();
    }
    public void cleanMemory(){
        if (player != null)
            player.dispose();
        media = null;
        player = null;
        System.gc(); // Garbage collector, келесі медиаларды қосқанда памятьдагы алдынгы медиаларды өшіруге
    }

}
