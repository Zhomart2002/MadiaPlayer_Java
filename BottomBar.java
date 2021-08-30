import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class BottomBar extends HBox {

    private final Button playPause;
    private final Button nextMedia;
    private final Button prevMedia;
    private final Slider timeSlider;
    private final Label volume;
    private final Slider volumeSlider;
    private final Button openFile;
    private final FileChooser fileChooser = new FileChooser();

    private ObservableList<File> selectedFiles;

    private boolean repeat = false;
    private Duration duration;

    public int listSize = 0;
    public int currentIndex = 0;

    public BottomBar(){
        super(2);
        setAlignment(Pos.CENTER);
        setPrefHeight(30);
        setStyle("-fx-background-color: gray");

        prevMedia = new Button("prev");
        playPause = new Button("||");
        nextMedia = new Button("next");

        timeSlider = new Slider();
        timeSlider.setPrefWidth(450);
        timeSlider.setMaxWidth(Double.MAX_VALUE);

        volume = new Label("volume");
        volumeSlider = new Slider();
        volumeSlider.setValue(75);

        openFile = new Button("Select files");

        getChildren().addAll(prevMedia, playPause, nextMedia,
                timeSlider, volume, volumeSlider, openFile);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("MP3", "*.mp3"));

    }

    public ObservableList<File> getSelectedFiles(){
        List<File> files = fileChooser.showOpenMultipleDialog(Main.getStage());
        if (files != null) {
            selectedFiles = FXCollections.observableArrayList(files);
            listSize = selectedFiles.size();
            currentIndex = -1;
            return selectedFiles;
        }
        return null;
    }

    public void setMediaProperties(MediaPlayer mp){
        mp.setVolume(volumeSlider.getValue() / 100.0);
        mp.setOnEndOfMedia(() -> {
            mp.seek(mp.getStartTime());
            if (!repeat) {
                playPause.setText(">");
                mp.pause();
            }else{
                mp.play();
            }
        });
        mp.currentTimeProperty().addListener(ov -> updateValues(mp));
        mp.setOnReady(() -> {
            duration = mp.getMedia().getDuration();
            updateValues(mp);
        });

        playPause.setOnAction(event -> {
            MediaPlayer.Status status = mp.getStatus();

            if (status == MediaPlayer.Status.PAUSED
                    || status == MediaPlayer.Status.READY
                    || status == MediaPlayer.Status.STOPPED) {
                playPause.setText("||");
                mp.play();
            } else if (status == MediaPlayer.Status.PLAYING){
                playPause.setText(">");
                mp.pause();
            }
        });
        timeSlider.valueProperty().addListener(val -> {
            if (timeSlider.isValueChanging() || timeSlider.isPressed()) {
                mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }

        });
        volumeSlider.valueProperty().addListener(val -> {
            if (volumeSlider.isValueChanging() || volumeSlider.isPressed()) {
                mp.setVolume(volumeSlider.getValue() / 100.0);
            }
        });
    }
    private void updateValues(MediaPlayer mp) {
            Platform.runLater(() -> {
                Duration currentTime = mp.getCurrentTime();
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled()
                        && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                }
            });

    }

    public Button getOpenFile() {
        return openFile;
    }

    public Button getNextMedia() {
        return nextMedia;
    }
    public Button getPrevMedia() {
        return prevMedia;
    }
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
    public File getNext(){
        if (currentIndex < listSize - 1)
            return selectedFiles.get(++currentIndex);

        return null;
    }
    public File getPrev(){
        if (currentIndex > 0)
            return selectedFiles.get(--currentIndex);

        return null;
    }
}
