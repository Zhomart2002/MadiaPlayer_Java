package sample.PlayerUI.Interfaces;


import javafx.scene.media.MediaPlayer;

public interface IMedia extends Switchable{
    void setMedia(String mediaFile);
    boolean togglePlayer();
    MediaPlayer getPlayer();
}
