package sample.PlayerUI.Components;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import sample.PlayerUI.Interfaces.IMedia;
import sample.PlayerUI.Interfaces.IPlaylist;

import java.io.File;

public class BottomBar extends HBox implements EventListener.FileListener {

    private final IMedia mediaPart;
    private final IPlaylist playlist;

    private Button playPause;
    private Button nextMedia;
    private Button prevMedia;
    private Button switcherPlaylistMediaView;

    private Slider timeSlider;
    private Slider volumeSlider;
//    private Label volume;

    public BottomBar(IMedia player, IPlaylist playlist) {
        this.mediaPart = player;
        this.playlist = playlist;

        this.playlist.setListener(this);

        initialize();
    }

    private void initialize(){
        initMediaButtons();
        initSwitcher();
        initSliders();
    }

    private void initSliders() {
        timeSlider = new Slider();
        timeSlider.setPrefWidth(450);
        timeSlider.setMaxWidth(Double.MAX_VALUE);

//        volume = new Label("volume");
        volumeSlider = new Slider();
        volumeSlider.setValue(75);

        getChildren().addAll(timeSlider, volumeSlider);
    }

    private void initSwitcher() {
        switcherPlaylistMediaView = new Button("=");
        switcherPlaylistMediaView.setOnMouseClicked(event -> {
            if (!playlist.isShown()){
                playlist.show();
            }else{
                mediaPart.show();
            }
        });
        getChildren().add(switcherPlaylistMediaView);
    }

    /*
    * handle playlist events
    *
    */

    private void initMediaButtons() {
        prevMedia = new Button("prev");
        playPause = new Button("||");
        nextMedia = new Button("next");

        playPause.setOnAction(event -> {
            boolean isPlaying = this.mediaPart.togglePlayer();
            if (isPlaying) playPause.setText("||");
            else playPause.setText(">");
        });

        prevMedia.setOnAction(event -> {
            playlist.prevMedia();
        });

        nextMedia.setOnAction(event -> {
            playlist.nextMedia();
        });

        getChildren().addAll(prevMedia, playPause, nextMedia);
    }

    @Override
    public void handle(File file) {
        setMedia(file);
    }

    private void setMedia(File file) {
        mediaPart.setMedia(file.toURI().toString());
        resetListeners(mediaPart.getPlayer());
    }

    private void resetListeners(MediaPlayer player) {

        timeSlider.valueProperty().addListener(val -> {
            if (timeSlider.isValueChanging() || timeSlider.isPressed()) {
                player.seek(player.getMedia().getDuration().multiply(timeSlider.getValue() / 100.0));
            }

        });
        volumeSlider.valueProperty().addListener(val -> {
            if (volumeSlider.isValueChanging() || volumeSlider.isPressed()) {
                player.setVolume(volumeSlider.getValue() / 100.0);
            }
        });

    }
}
