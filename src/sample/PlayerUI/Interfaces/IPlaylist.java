package sample.PlayerUI.Interfaces;

import sample.PlayerUI.Components.EventListener;

import java.io.File;
import java.util.List;

public interface IPlaylist extends Switchable{
    void nextMedia();
    void prevMedia();
    void remove(File file);
    void setFiles(List<File> files);
    void setListener(EventListener.FileListener listener);
}
