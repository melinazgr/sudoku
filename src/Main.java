import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public Stage window;
    private Scene menu;

    private boolean wordokuGame;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = createMainScene();

        window = primaryStage;
        window.setTitle("Sudoku");

        menu = new Scene(root);
        menu.getStylesheets().add("app.css");

        window.setScene(menu);
        window.show();
    }

    /**
     *
     * @return
     */
    private BorderPane createMainScene() {
        //create the logo text
        Label logo = new Label("Sudoku");
        logo.setId("mainLabel");

        //create language pane
        HBox languagePane = new HBox();
        Label lang = new Label();
        lang.setText("Language  ");

        Button buttonEn = new Button("English");
        Button buttonGr = new Button("Greek");

        languagePane.getChildren().addAll(lang, buttonEn, buttonGr);

        buttonEn.setOnAction(e -> {
            System.out.println("english");
        });
        buttonGr.setOnAction(e -> {
            System.out.println("greek");
        });

        // create main menu pane
        VBox centerPane = new VBox();
        Button buttonSud = new Button("Sudoku");
        CheckBox buttonWordoku = new CheckBox("Wordoku");

        centerPane.getChildren().addAll(buttonSud, buttonWordoku);
        buttonSud.setOnAction(e -> sudokuGameButtonAction());

        buttonWordoku.setOnAction(e -> {
            wordokuGame = buttonWordoku.isSelected();
        });

        // set the alignment of the logo text
        BorderPane.setAlignment(logo, Pos.TOP_CENTER);

        BorderPane root = new BorderPane();

        root.setTop(logo);
        root.setBottom(languagePane);
        root.setCenter(centerPane);

        root.setPrefSize(700, 700);

        return root;
    }

    /**
     * changes scenes in order to go to main sudoku game scene
     */
    private void sudokuGameButtonAction() {
        ModelSudoku model = new ModelSudoku(wordokuGame);
        GameController controller = new GameController(model);
        String s =
                "..3.....9" +
                        "2....43.." +
                        "461......" +
                        "...8..9.6" +
                        "...4..8.." +
                        ".......3." +
                        "..9....15" +
                        ".2.68..9." +
                        "63.5..2.8," +
                        "573268149298154367461793582342871956957436821816925734789342615125687493634519278";
        model.load(s);

        GameSudokuView gameSudoku = new GameSudokuView(model, controller, this);
        window.setScene(gameSudoku.scene);
    }

    public void showMainMenu() {
        window.setScene(this.menu);
    }
}