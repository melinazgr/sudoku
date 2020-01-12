package game;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;

/**
 * Creates and organises the main menu page.
 *
 * @author Melina Zikou
 */
public class GameMenu implements MenuInterface {

    private final int GAME_COUNT = 10;
    private final String PLAYER_PATH = "players.bin";
    private final String DEFAULT_PLAYER_NAME = "Anonymous";

    private boolean wordokuGame;
    private Stage window;
    private BorderPane mainPane;
    private Scene menuScene;
    private PlayerManagement playerManagement;
    private FileManagement fileManagement;
    private ComboBox playerBox;
    private Language language;

    /**
     * Implements essential actions for the game to begin
     * @param window stage window
     */
    public GameMenu(Stage window, Language language) {
        this.window = window;
        this.language = language;

        playerManagement = new PlayerManagement();
        playerManagement.load(PLAYER_PATH);

        fileManagement = new FileManagement();

        try{
            fileManagement.load(".\\resources\\games.csv");
        }
        catch (IOException e) {
            //TODO display error
            e.printStackTrace();
        }

        createScene();
    }

    private void createScene() {
        mainPane = createMenuScene();
        menuScene = new Scene(mainPane);
        menuScene.getStylesheets().add("app.css");
    }

    /**
     * Creates all the buttons in the main menu and handles their actions
     * @return the panel
     */
    private BorderPane createMenuScene() {

        //create the logo text
        GridPane logoPane = new GridPane();
        Text logo = new Text("Sudoku");
        logoPane.getChildren().add(logo);
        logo.setId("mainLabel");
        logoPane.getStyleClass().add("main");

        // create main menu pane
        GridPane centerPane = new GridPane();
        centerPane.getStyleClass().add("main");

        // select game label
        Label selectGame = new Label(language.getText("selectgame"));
        selectGame.setId("selectGameLabel");

        // button Sudoku
        Button buttonSud = new Button("Sudoku");
        buttonSud.getStyleClass().add("gameButtons");

        // button killer sudoku
        Button buttonKillSud = new Button("Killer Sudoku");
        buttonKillSud.getStyleClass().add("gameButtons");

        // button duidoku
        Button buttonDuidoku = new Button("Duidoku");
        buttonDuidoku.getStyleClass().add("gameButtons");

        // button/checkbox wordoku
        CheckBox buttonWordoku = new CheckBox("Wordoku");

        // set position of each button
        centerPane.setAlignment(Pos.TOP_CENTER);

        centerPane.setMargin(buttonSud, new Insets(20, 0, 0, 0));
        buttonSud.setAlignment(Pos.CENTER);
        buttonSud.setPrefSize(500, 100);

        centerPane.setMargin(buttonKillSud, new Insets(10, 0, 0, 0));
        buttonKillSud.setAlignment(Pos.CENTER);
        buttonKillSud.setPrefSize(500, 100);

        centerPane.setMargin(buttonDuidoku, new Insets(10, 0, 0, 0));
        buttonDuidoku.setAlignment(Pos.CENTER);
        buttonDuidoku.setPrefSize(500, 100);

        centerPane.setMargin(buttonWordoku, new Insets(10, 0, 0, 0));

        centerPane.add(selectGame, 0, 0);
        centerPane.add(buttonSud, 0, 1);
        centerPane.add(buttonKillSud, 0, 2);
        centerPane.add(buttonDuidoku, 0, 3);
        centerPane.add(buttonWordoku, 0, 4);
        buttonSud.setOnAction(e -> sudokuGameButtonAction());
        buttonKillSud.setOnAction(e -> killerSudokuGameButtonAction());

        buttonSud.setLayoutX(70);
        buttonSud.setLayoutY(80);

        buttonWordoku.setOnAction(e -> {
            wordokuGame = buttonWordoku.isSelected();
        });

        // set the alignment of the logo text
        BorderPane.setAlignment(logo, Pos.TOP_CENTER);

        // create player pane
        HBox playerPane = new HBox();
        Label selectPlayer = new Label();
        selectPlayer.setText(language.getText("selectplayer"));

        selectPlayer.setId("playersId");

        playerBox = new ComboBox();
        playerBox.getItems().add(DEFAULT_PLAYER_NAME);
        playerBox.getItems().addAll(playerManagement.getPlayerNames());

        playerBox.setPromptText("Select Player");
        playerBox.setEditable(true);

        playerBox.getSelectionModel().selectFirst();


        playerPane.getChildren().addAll(selectPlayer, playerBox);
        centerPane.add(playerPane, 0, 5);

        //create language pane
        HBox languagePane = new HBox();
        Label lang = new Label();
        lang.setText(language.getText("lang"));

        Button buttonEn = new Button(language.getText("english"));
        Button buttonGr = new Button(language.getText("greek"));

        languagePane.getChildren().addAll(lang, buttonEn, buttonGr);

        buttonEn.setOnAction(e -> {
            language.switchLanguage(new Locale("en", "US"));
            createScene();
            showMainMenu();
        });
        buttonGr.setOnAction(e -> {
            language.switchLanguage(new Locale("el", "GR"));
            createScene();
            showMainMenu();
        });

        BorderPane root = new BorderPane();

        root.setTop(logo);
        root.setBottom(languagePane);
        root.setCenter(centerPane);

        root.setPrefSize(700, 700);

        return root;
    }

    /**
     *  Action when button Killer Sudoku is clicked
     */
    private void killerSudokuGameButtonAction() {
        ModelKillerSudoku model = new ModelKillerSudoku();
        GameController controller = new GameController(model);

        GameType gameType = GameType.KillerSudoku;

        chooseGame(model, controller, gameType);
    }

    /**
     *  Action when button Sudoku is clicked
     */
    private void sudokuGameButtonAction() {
        ModelSudoku model = new ModelSudoku(wordokuGame);
        GameController controller = new GameController(model);

        GameType gameType = GameType.Sudoku;

        chooseGame(model, controller, gameType);
    }

    /**
     * TODO
     * @param model
     * @param controller
     * @param gameType
     */
    private void chooseGame(Model model, GameController controller, GameType gameType) {
        Player player = new Player();

        if (playerBox.getValue() != null && !playerBox.getValue().toString().isEmpty()){
            String name = playerBox.getValue().toString();
            if(!name.equals(DEFAULT_PLAYER_NAME)){
                if(!playerManagement.searchPlayer(name)){
                    player = playerManagement.createPlayer(name);
                    playerBox.getItems().add(name);
                }
            else{
                    player = playerManagement.getPlayer(name);
                }
            }
        }

        // choose a game to play
        int nextGame = player.randomGame(gameType, GAME_COUNT);

        if (nextGame != -1) {
            GameDefinition game = fileManagement.getGame(nextGame, gameType);
            model.load(game);

            player.addGame(nextGame, gameType);

            GameSudokuView gameSudoku = new GameSudokuView(model, controller, window, this, language);
            window.setScene(gameSudoku.scene);
        }
    }

    /**
     * Shows the main menu
     */
    @Override
    public void showMainMenu() {
        window.setScene(this.menuScene);
        window.show();
    }

    /**
     * TODO
     */
    public void saveState() {
        playerManagement.save(PLAYER_PATH);
    }
}