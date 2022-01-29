package sample.PlayerUI.Components.Menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import sample.Helpers.FileChooserHelper;
import sample.PlayerUI.Interfaces.IPlaylist;
import java.io.File;
import java.util.List;

public class FileMenu extends Menu {

    private MenuItem openFilesItem;
    private IPlaylist playlist;

    public FileMenu(){
        super("File");
        initialize();
    }

    private void initialize() {
        createOpenFilesItem();
    }

    private void createOpenFilesItem() {
        openFilesItem = new MenuItem("Open files");

        openFilesItem.setOnAction(event -> {
            List<File> selectedFiles = FileChooserHelper.getSelectedMediaFiles();
            this.playlist.setFiles(selectedFiles);
        });

        super.getItems().add(openFilesItem);
    }

    public void bindPlaylist(IPlaylist playlist) {
        this.playlist = playlist;
    }
}
