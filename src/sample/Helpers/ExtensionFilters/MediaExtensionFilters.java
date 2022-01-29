package sample.Helpers.ExtensionFilters;

import javafx.stage.FileChooser;
import sample.Helpers.ExtensionUtils;
import sample.Helpers.FileExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sample.Helpers.FileExtension.*;

public class MediaExtensionFilters {

    private static final Map<FileExtension, List<String>> filtersMap = new HashMap<FileExtension, List<String>>(){
        {
            put(AUDIO_FILES, Arrays.asList(
                    "*.mp3",
                    "*.aac"
            ));

            put(VIDEO_FILES, Arrays.asList(
                    "*.mp4",
                    "*.mov"
            ));
        }
    };
    private static List<String> getExtensions(FileExtension fileExtension){
        return filtersMap.get(fileExtension);
    }

    public static FileChooser.ExtensionFilter getMediaFilter() {
        return ExtensionUtils.concatenateFilters(
                "Media files",
                getExtensions(AUDIO_FILES),
                getExtensions(VIDEO_FILES));
    }

    public static FileChooser.ExtensionFilter getAudioFilter() {
        return ExtensionUtils.getExtensionFilter("Audio files", getExtensions(AUDIO_FILES));
    }

    public static FileChooser.ExtensionFilter getVideoFilter() {
        return ExtensionUtils.getExtensionFilter("Video files", getExtensions(VIDEO_FILES));
    }

    public static void setFilter(FileChooser fileChooser){
        fileChooser.getExtensionFilters().setAll(
                getAudioFilter(),
                getVideoFilter(),
                getMediaFilter(),
                ExtensionUtils.getAllFileFilter()
        );
    }
}
