package sample.PlayerUI;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import sample.PlayerUI.Components.HelperMenu;
import sample.PlayerUI.Components.BottomBar;
import sample.PlayerUI.Components.MediaPart;
import sample.PlayerUI.Components.Playlist;

public class PlayerUI {
    private BorderPane root;

    private HelperMenu menuBar;
    private MediaPart mediaPart;
    private BottomBar bottomBar;

    private Playlist playlist;

    public PlayerUI() {
        initialize();
    }

    private void initialize() {
        initRoot();
        initMenuBar();
        initMediaSide();
        initPlayList();
        initBottomBar();
    }

    private void initRoot() {
        this.root = new BorderPane();
    }

    private void initMenuBar() {
        this.menuBar = new HelperMenu();
        this.root.setTop(menuBar);
    }

    private void initMediaSide() {
        this.mediaPart = new MediaPart(root);
//        this.root.setCenter(mediaPart);
    }

    private void initPlayList() {
        this.playlist = new Playlist(root);
        this.root.setCenter(playlist);
        this.menuBar.setPlaylist(playlist);
    }

    private void initBottomBar() {
        this.bottomBar = new BottomBar(mediaPart, playlist);
        this.root.setBottom(bottomBar);
    }
    
    public Parent getRoot(){
        return this.root;
    }

    private void switchPlaylistMedia(Node node){
        this.root.setCenter(node);
    }
}
