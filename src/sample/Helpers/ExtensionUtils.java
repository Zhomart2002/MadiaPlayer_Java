package sample.Helpers;

import javafx.stage.FileChooser;

import java.util.*;

public class ExtensionUtils {

    @SafeVarargs
    public static FileChooser.ExtensionFilter concatenateFilters(String description, List<String>... fileExtensions) {
        List<String> extensions = new ArrayList<>();

        for(List<String> extension: fileExtensions)
            extensions.addAll(extension);

        return getExtensionFilter(description, extensions);
    }

    public static FileChooser.ExtensionFilter getExtensionFilter(String description, List<String> extensions) {
        return new FileChooser.ExtensionFilter(description, extensions);
    }

    public static FileChooser.ExtensionFilter getAllFileFilter() {
        return getExtensionFilter("All files", Collections.singletonList("*"));
    }
}
