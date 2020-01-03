import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window;
    Scene menu, game;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = createMainScene();

        window = primaryStage;
        window.setTitle("Sudoku");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("app.css");

        window.setScene(scene);
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

        languagePane.getChildren().add(lang);
        languagePane.getChildren().add(buttonEn);
        languagePane.getChildren().add(buttonGr);

        buttonEn.setOnAction(e -> {
            System.out.println("english");
        });
        buttonGr.setOnAction(e -> {
            System.out.println("greek");
        });


        // create main menu pane
        VBox centerPane = new VBox();
        Button buttonSud = new Button("Sudoku");
        centerPane.getChildren().add(buttonSud);
        buttonSud.setOnAction(e -> {
            ModelSudoku model = new ModelSudoku();
            model.load("..3.....92....43..461.........8..9.6...4..8.........3...9....15.2.68..9.63.5..2.8," +
                    "573268149298154367461793582342871956957436821816925734789342615125687493634519278");

            GameSudokuView gameSudoku = new GameSudokuView(model);
            window.setScene(gameSudoku.scene);

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
}




