import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
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
        GridPane centerPane = new GridPane();

        Label selectGame = new Label("Select Game");
        selectGame.setId("select");

        Button buttonSud = new Button("Sudoku");
        buttonSud.getStyleClass().add("gameButtons");

        Button buttonKillSud = new Button("Killer Sudoku");
        buttonKillSud.getStyleClass().add("gameButtons");

        CheckBox buttonWordoku = new CheckBox("Wordoku");

        centerPane.setAlignment(Pos.TOP_CENTER);

        centerPane.setMargin(buttonSud, new Insets(20,0,0,0));
        buttonSud.setAlignment(Pos.CENTER);
        buttonSud.setPrefSize(500, 100);

        selectGame.setAlignment(Pos.TOP_LEFT);


        centerPane.add(selectGame, 0, 0);
        centerPane.add(buttonSud, 0,1);
        centerPane.add(buttonKillSud, 0, 2);
        centerPane.add(buttonWordoku, 0,3);
        buttonSud.setOnAction(e -> sudokuGameButtonAction());
        buttonKillSud.setOnAction(e -> killerSudokuGameButtonAction());

        buttonSud.setLayoutX(70);
        buttonSud.setLayoutY(80);

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

    private void killerSudokuGameButtonAction() {
        ModelKillerSudoku model = new ModelKillerSudoku();
        GameController controller = new GameController(model);
        String s ="681974523245831697739652148857413962924786351316295784598127436473568219162349875," +
                "15:00:00:16:00:09:00:11:18:06:00:16:00:00:03:07:00:00:15:19:00:10:06:00:00:10:00:" +
                "00:07:00:00:00:09:12:00:03:12:00:10:26:00:00:00:13:00:00:10:00:00:00:12:00:00:10:" +
                "10:00:22:00:00:15:08:00:00:00:18:00:00:00:00:19:00:21:00:00:00:07:00:00:00:00:00," +
                "111223314223331214411241234421242431324112421314113324213332114244332413244224433";

        model.load(s);

        GameSudokuView gameSudoku = new GameSudokuView(model, controller, this);
        window.setScene(gameSudoku.scene);
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

//TODO: duidoku
//TODO: language
//TODO: load
//TODO: make files
//TODO: player management
//TODO: player stats
//TODO: save player stats
//TODO: unit tests controller
//TODO: comments
