package sample.PlayerUI.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.PlayerUI.Interfaces.IPlaylist;

import java.io.File;
import java.util.List;

public class Playlist extends TableView<File> implements IPlaylist {

    private final BorderPane root;

    private ObservableList<File> playlist;
    private EventListener.FileListener listener;

    private int selectedFileIndex = 0;

    private final String NO_MEDIA_MESSAGE = "No media selected";

    public Playlist(BorderPane root){
        this.root = root;
        initialize();
    }

    private void initialize() {
        initRoot();
        createColumns();
    }

    private void createColumns() {
        TableColumn<File, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        super.getColumns().add(nameColumn);
    }

    private void initRoot() {
        super.setPlaceholder(new Label(NO_MEDIA_MESSAGE));

        super.setRowFactory(tableView -> {
            TableRow<File> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && isAvailableIndex(tableRow.getIndex())){
                    setSelectedFileIndex(tableRow.getIndex());
                }
            });
            return tableRow;
        });
    }

    private boolean isAvailableIndex(int selectedFileIndex) {
        return selectedFileIndex < playlist.size() && selectedFileIndex != this.selectedFileIndex;
    }

    @Override
    public void nextMedia() {
        setSelectedFileIndex(selectedFileIndex + 1);
    }

    @Override
    public void prevMedia() {
        setSelectedFileIndex(selectedFileIndex - 1);
    }

    @Override
    public void remove(File file) {
        System.out.println("Removing file: " + file.getName());
    }

    @Override
    public void setFiles(List<File> selectedFiles) {
        this.playlist = FXCollections.observableArrayList(selectedFiles);
        super.setItems(playlist);
        restartPlaylist();
    }

    private void restartPlaylist() {
        setSelectedFileIndex(0);
    }

    private void setSelectedFileIndex(int selectedFileIndex){
        if(isPlaylistEmpty()) return;

        if (selectedFileIndex >= playlist.size()) this.selectedFileIndex = 0;
        else if (selectedFileIndex < 0) this.selectedFileIndex = playlist.size() - 1;
        else this.selectedFileIndex = selectedFileIndex;

        sendSelectedFile();
    }

    private void sendSelectedFile() {
        listener.handle(playlist.get(selectedFileIndex));
    }

    private boolean isPlaylistEmpty(){
        return playlist.size() == 0;
    }

    @Override
    public boolean isShown() {
        return super.getParent() != null;
    }

    @Override
    public void show() {
        this.root.setCenter(this);
    }

    @Override
    public void setListener(EventListener.FileListener listener){
        this.listener = listener;
    }
}
