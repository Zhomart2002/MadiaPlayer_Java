package sample.PlayerUI.Components;

import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.PlayerUI.Interfaces.IMedia;

public class MediaPart extends MediaView implements IMedia {
    private MediaPlayer mediaPlayer;
    private Media media;

    private final BorderPane root;

    public MediaPart(BorderPane root) {
        this.root = root;
        initialize();
    }

    private void initialize() {
    }

    @Override
    public void setMedia(String mediaFilePath) {
        disposePlayer();
        media = new Media(mediaFilePath);
        mediaPlayer = new MediaPlayer(media);
        super.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }

    private void disposePlayer() {
        if (mediaPlayer != null) mediaPlayer.dispose();
    }

    @Override
    public boolean togglePlayer() {
        MediaPlayer.Status status = mediaPlayer.getStatus();

        switch (status){
            case PAUSED:
            case READY:
            case STOPPED:
                mediaPlayer.play(); return true;
            case PLAYING:
                mediaPlayer.pause(); return false;
            default:
                System.out.println(status);
        }
        return true;
    }

    @Override
    public MediaPlayer getPlayer() {
        return this.mediaPlayer;
    }

    @Override
    public boolean isShown() {
        return super.getParent() != null;
    }

    @Override
    public void show() {
        this.root.setCenter(this);
    }
}
