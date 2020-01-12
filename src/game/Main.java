package game;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * game.Main class of the program
 * Complete class diagram and association shown in
 * <img src= "http://yuml.me/1fadf7c0.png" alt = "CLass diagram"></img>
 * @author Melina Zikou
 */
public class Main extends Application {

    public Stage window;
    private GameMenu menu;
    Language language;

    //initiation of the game
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        window = primaryStage;
        language = new Language();

        menu = new GameMenu(window, language);

        window.setTitle("Sudoku");

        window.setOnCloseRequest(e -> closeProgram());

        menu.showMainMenu();
    }

    private void closeProgram() {
        menu.saveState();
    }


}

//TODO: duidoku
//TODO: language
//TODO: load
//TODO: make files
//TODO: player management
//TODO: player stats
//TODO: save player stats
//TODO: unit tests controller
//TODO: comments
