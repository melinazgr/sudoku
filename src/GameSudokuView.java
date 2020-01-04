import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

/**
 * This class implements the Graphic User Interface of the game sudoku.
 *
 * @author Melina Zikou
 */
public class GameSudokuView {

    public Scene scene;

    private Popup popup;
    private Button[] popupButtons;

    private Model model;
    private Main mainWindow;
    private GameController controller;

    //saves the last cell position the user clicked to get the popup
    private int lastPopupCol;
    private int lastPopupRow;

    private boolean hintEnabled;

    private boolean isGameOver;

    GridPane[] grid;
    StackPane[] tiles;
    Text[] cellText;

    //constructor
    public GameSudokuView(Model model, GameController controller, Main mainWindow) {
        this.model = model;
        this.mainWindow = mainWindow;
        this.controller = controller;
        this.scene = createGamePanel();
        createPopup();
    }

    /**
     * Sets the main panel of the game.
     * This panel consists of a Label, a GridPane, an HBox and a VBox
     * @return the scene created
     */
    public Scene createGamePanel() {
        Label logo = new Label("Sudoku");
        logo.setId("mainLabel");

        //create the main game panel
        GridPane gamePanel = new GridPane();
        gamePanel.getStyleClass().add("main");

        // distance between grid panels within the main grid panel
        gamePanel.setVgap(25);
        gamePanel.setHgap(25);
        gamePanel.setAlignment(Pos.CENTER);

        //create the bottom action buttons
        CheckBox hintButton = new CheckBox("Hint");
        Button clearButton = new Button("Clear");
        Button solutionButton = new Button("Solution");
        Button mainMenuButton = new Button("Main Menu");

        //bottom action buttons

        //hint button
        hintButton.setOnAction(e -> {hintEnabled = hintButton.isSelected();});

        //clear button
        clearButton.setOnAction(e -> {
            controller.clearGame();
            updateGamePanel();
        });

        //solution button
        solutionButton.setOnAction(e -> {
            controller.showSolution();
            updateGamePanel();
            isGameOver = true;
            this.model.gameOver();
        });

        //main menu button
        mainMenuButton.setOnAction(e -> {this.mainWindow.showMainMenu(); });

        //HBox that has buttons in the bottom of the main panel
        HBox bottomPanel = new HBox();
        bottomPanel.getChildren().addAll(hintButton, clearButton, solutionButton, mainMenuButton);

        //main panel and its components
        VBox layout = new VBox();
        layout.getChildren().addAll(logo, gamePanel, bottomPanel);

        createGamePanel(gamePanel);

        //create game scene
        Scene scene = new Scene(layout, 700, 700);
        scene.getStylesheets().add("app.css");
        return scene;
    }

    /**
     * Creates the grid of the cells
     * @param gamePanel takes the structure of the main panel and adds to it
     */
    private void createGamePanel(GridPane gamePanel) {
        int gridSize = model.getGroupSize() * model.getGroupSize();
        int tileSize = model.getSize() * model.getSize();

        this.grid = new GridPane[gridSize];
        this.tiles = new StackPane[tileSize];

        cellText = new Text[tileSize];

        for (int i = 0; i < gridSize; i++) {
            grid[i] = new GridPane();

            for (int row = 0; row < model.getGroupSize(); row++) {
                for (int col = 0; col < model.getGroupSize(); col++) {

                    int tempCol = (i % model.getGroupSize()) * model.getGroupSize() + col;
                    int tempRow = (i / model.getGroupSize()) * model.getGroupSize() + row;

                    int index = tempRow * this.model.getSize() + tempCol;
                    int cell = model.getDisplayCell(tempRow, tempCol);

                    cellText[index] = new Text();

                    setCellValue(tempCol, tempRow, index, cell);

                    Rectangle rect = new Rectangle(30, 30, 50, 50);
                    rect.setId("rect");
                    rect.setOnMouseClicked(e -> {
                        if (!popup.isShowing()) {
                            popup.setX(e.getScreenX());
                            popup.setY(e.getScreenY());

                            if(!this.model.isOriginalCell(tempRow, tempCol)){
                                showPopup(tempRow, tempCol);
                            }
                        }
                    });

                    //TODO  mve to Css
                    rect.setArcHeight(20);
                    rect.setArcWidth(20);
                    tiles[index] = new StackPane(rect, cellText[index]);
                    tiles[index].setPrefSize(70, 70);

                    grid[i].add(tiles[index], col, row);
                }
            }

            gamePanel.add(grid[i], i % model.getGroupSize(), i / model.getGroupSize());
        }
    }

    private void updateGamePanel() {
        int tileSize = model.getSize() * model.getSize();

        for (int index = 0; index < tileSize; index++) {
            int row = index / this.model.getSize();
            int col = index % this.model.getSize();

            int cell = this.model.getDisplayCell(row, col);

            setCellValue(col, row, index, cell);
        }
    }

    private void setCellValue(int col, int row, int index, int cellValue) {
        if (this.model.isOriginalCell(row, col)){
            cellText[index].setId("original");
        }
        else{
            cellText[index].setId("userCell");
        }

        if (cellValue != 0) {
            char ch = this.model.toCharCell(cellValue);

            cellText[index].setText(String.valueOf(ch));
        }
        else {
            cellText[index].setText("");
        }
    }

    private void showPopup(int row, int col) {

        lastPopupCol = col;
        lastPopupRow = row;

        for (int i = 0; i <= 9; i++) {
            popupButtons[i].setDisable(false);
            popupButtons[i].setId("popupOn");
        }
        if (hintEnabled) {

            ArrayList<Integer> hints = this.model.getHint(row, col);

            for (int i = 1; i <= 9; i++) {
                if (!hints.contains(i)) {
                    popupButtons[i].setDisable(true);
                    popupButtons[i].setId("popupOff");
                }
            }
        }
        popup.show(mainWindow.window);
    }

    private void createPopup() {
        // create a popup
        popup = new Popup();

        HBox hbox = new HBox();
        hbox.setId("popup");

        popupButtons = new Button[10];

        //clear cell button
        popupButtons[0] = new Button("X");
        hbox.getChildren().add(popupButtons[0]);

        popupButtons[0].setOnAction(e -> {
            controller.setCell(lastPopupRow, lastPopupCol, 0);
            updateGamePanel();
            popup.hide();
        });

        for (int i = 1; i <= 9; i++) {
            char ch = this.model.toCharCell(i);

            popupButtons[i] = new Button(String.valueOf(ch));
            hbox.getChildren().add(popupButtons[i]);

            int buttonIndex = i;
            popupButtons[i].setOnAction(e -> {
                controller.setCell(lastPopupRow, lastPopupCol, buttonIndex);
                updateGamePanel();
                popup.hide();
                if(this.model.gameOver()){
                    if(showSuccessDialog()){
                        this.mainWindow.showMainMenu();
                    }
                }
            });
        }

        popup.getContent().add(hbox);

        // set auto hide
        popup.setAutoHide(true);
    }

    private boolean showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!!");
        alert.setHeaderText("Congratulations, you won the game!");
        alert.setContentText("Would you like to go back to play a new game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}