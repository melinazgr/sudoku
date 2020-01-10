import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main class of the program
 * @author Melina Zikou
 */
public class Main extends Application {

    public Stage window;
    GameMenu menu;

    //initiation of the game
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        window = primaryStage;

        menu = new GameMenu(window);

        window.setTitle("Sudoku");

        menu.showMainMenu();
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
