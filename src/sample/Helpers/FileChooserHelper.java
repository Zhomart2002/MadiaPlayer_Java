package sample.Helpers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Helpers.ExtensionFilters.MediaExtensionFilters;

import java.util.*;
import java.io.File;

public class FileChooserHelper {

    private static final FileChooser fileChooser = new FileChooser();

    public static List<File> getSelectedMediaFiles(){
        MediaExtensionFilters.setFilter(fileChooser);
        return getSelectedFiles();
    }

    private static List<File> getSelectedFiles(){
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        if (files == null)
            return new ArrayList<>();
        return files;
    }
}
