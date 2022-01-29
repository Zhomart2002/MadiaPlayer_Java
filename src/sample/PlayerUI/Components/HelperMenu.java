package sample.PlayerUI.Components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import sample.PlayerUI.Components.Menus.FileMenu;
import sample.PlayerUI.Interfaces.IPlaylist;

public class HelperMenu extends MenuBar {

    private FileMenu fileMenu;

    public HelperMenu(){
        initialize();
    }

    private void initialize() {
        createFileMenu();
    }

    private void createFileMenu() {
        this.fileMenu = new FileMenu();
        addMenu(fileMenu);
    }
    public void setPlaylist(IPlaylist playlist){
        this.fileMenu.bindPlaylist(playlist);
    }
    private void addMenu(Menu menu){
        super.getMenus().add(menu);
    }
}
