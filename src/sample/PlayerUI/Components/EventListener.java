package sample.PlayerUI.Components;

import java.io.File;

public interface EventListener {
    @FunctionalInterface
    interface FileListener{
        void handle(File file);
    }
}
