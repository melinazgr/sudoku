package game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

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
    private MenuInterface mainMenu;
    private GameController controller;
    private Stage window;

    //saves the last cell position the user clicked to get the popup
    private int lastPopupCol;
    private int lastPopupRow;

    private boolean hintEnabled;

    private boolean isGameOver;
    private boolean isGameDuidoku;

    private Language language;

    GridPane[] grid;
    StackPane[] tiles;
    Text[] cellText;

    /**
     * constructor which creates the game scene
     * @param model of the game. all the information about the game is handled
     * @param controller the connection between model and view takes place
     * @param window main window of the game
     * @param menu main menu of the game
     */
    public GameSudokuView(Model model, GameController controller, Stage window, MenuInterface menu, Language language, boolean isGameDuidoku) {
        this.model = model;
        this.mainMenu = menu;
        this.controller = controller;
        this.window = window;
        this.language = language;
        this.isGameDuidoku = isGameDuidoku;
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
        CheckBox hintButton = new CheckBox(language.getText("hint"));
        Button clearButton = new Button(language.getText("clear"));
        Button solutionButton = new Button(language.getText("solution"));
        Button mainMenuButton = new Button(language.getText("menu"));

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
        mainMenuButton.setOnAction(e -> {this.mainMenu.showMainMenu(); });

        //HBox that has buttons in the bottom of the main panel
        HBox bottomPanel = new HBox();

        if(!this.isGameDuidoku){
            bottomPanel.getChildren().addAll(hintButton, clearButton, solutionButton, mainMenuButton);
        }

        else {
            bottomPanel.getChildren().addAll(hintButton, mainMenuButton);
        }

        //sets distance between the bottom buttons
        bottomPanel.setMargin(clearButton, new Insets(0,0,0,20));
        bottomPanel.setMargin(solutionButton, new Insets(0,0,0,20));
        bottomPanel.setMargin(mainMenuButton, new Insets(0,0,0,20));

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

        //number of cells in a block of cells
        int gridSize = model.getGroupSize() * model.getGroupSize();
        //number of cells in a game board
        int tileSize = model.getSize() * model.getSize();

        this.grid = new GridPane[gridSize];
        this.tiles = new StackPane[tileSize];

        //create text to write numbers on the cells
        cellText = new Text[tileSize];

        for (int i = 0; i < gridSize; i++) {
            grid[i] = new GridPane();

            for (int row = 0; row < model.getGroupSize(); row++) {
                for (int col = 0; col < model.getGroupSize(); col++) {

                    //calculate the column and row according to i = serial number of cells
                    int tempCol = (i % model.getGroupSize()) * model.getGroupSize() + col;
                    int tempRow = (i / model.getGroupSize()) * model.getGroupSize() + row;

                    int index = tempRow * this.model.getSize() + tempCol;
                    int cell = model.getDisplayCell(tempRow, tempCol);

                    cellText[index] = new Text();
                    Text sumText = null;

                    // sum used in killer sudoku mode
                    int sum = this.model.getSum(tempRow, tempCol);

                    // prints out sum text only if sum is not 0
                    // in sudoku all sums are by default zero
                    if(sum != 0){
                        sumText = new Text();
                        sumText.setId("sumText");
                        sumText.setText(String.valueOf(sum));
                    }

                    setCellValue(tempRow, tempCol, index, cell);

                    //create the rectangles used as cells
                    Rectangle rect = new Rectangle(30, 30, 50, 50);
                    int color = this.model.getColor(tempRow, tempCol);

                    // set cell color according to cell type
                    // in sudoku cells are gray
                    // in killer sudoku cells are colored
                    // 1 = pink
                    // 2 = blue
                    // 3 = orange
                    // 4 = yellow

                    switch(color)
                    {
                        case 1:
                            rect.getStyleClass().add("rectColor1");
                            break;
                        case 2:
                            rect.getStyleClass().add("rectColor2");
                            break;
                        case 3:
                            rect.getStyleClass().add("rectColor3");
                            break;
                        case 4:
                            rect.getStyleClass().add("rectColor4");
                            break;
                        default:
                            rect.getStyleClass().add("rectColor");
                        break;
                    }

                    rect.setId("rect");

                    // events on clicking the cells
                    rect.setOnMouseClicked(e -> {
                        if (!popup.isShowing()) {
                            popup.setX(e.getScreenX());
                            popup.setY(e.getScreenY());

                            if(!this.model.isOriginalCell(tempRow, tempCol)){
                                showPopup(tempRow, tempCol);
                            }
                        }
                    });

                    //TODO  move to Css
                    //round cells
                    rect.setArcHeight(20);
                    rect.setArcWidth(20);
                    tiles[index] = new StackPane();

                    // puts the sum in the top left corner of sells if there is one
                    if(sum != 0 ){
                        tiles[index].setAlignment(sumText, Pos.TOP_LEFT);
                        tiles[index].setMargin(sumText, new Insets(7, 0, 0, 11));
                        tiles[index].getChildren().add(sumText);
                    }

                    tiles[index].getChildren().addAll(rect, cellText[index]);
                    tiles[index].setPrefSize(70, 70);

                    grid[i].add(tiles[index], col, row);
                }
            }
            gamePanel.add(grid[i], i % model.getGroupSize(), i / model.getGroupSize());
        }
    }

    /**
     * updates the panel each time the user
     * puts a number in a cell or erases it
     */
    private void updateGamePanel() {
        int tileSize = model.getSize() * model.getSize();

        for (int index = 0; index < tileSize; index++) {
            int row = index / this.model.getSize();
            int col = index % this.model.getSize();

            int cell = this.model.getDisplayCell(row, col);

            setCellValue(row, col, index, cell);
        }
    }

    /**
     * sets the cell value
     *
     * @param row row coordinate
     * @param col column coordinate
     * @param index index of the cell text array
     * @param cellValue value to be written in the cell
     */
    private void setCellValue(int row, int col, int index, int cellValue) {

        // if the cell is original - given on the board - the number is bolder
        // if the cell is added by the user the number is thinner
        if (this.model.isOriginalCell(row, col)){
            cellText[index].setId("original");
        }
        else{
            cellText[index].setId("userCell");
        }

        if (cellValue != 0) {

            //create a key string for the bundle to use it
            // the string is created by concatenating the word key
            // followed by the number or letter that has to be written.

            char ch = this.model.toCharCell(cellValue);
            String key = new StringBuilder("key").append(ch).toString();

            cellText[index].setText(language.getText(key));
        }
        else {
            cellText[index].setText("");
        }
    }

    /**
     * Created the popup buttons.
     * Popup buttons are used to play the sudoku.
     * Once the user clicks on a cell, a popup with
     * all the number appears next to the cell clicked
     */
    private void createPopup() {
        // create a popup
        popup = new Popup();

        HBox hbox = new HBox();
        hbox.setId("popup");

        popupButtons = new Button[10];

        //clear cell button
        popupButtons[0] = new Button("X");
        hbox.getChildren().add(popupButtons[0]);

        // once a number from the popup is selected the popup is hidden
        popupButtons[0].setOnAction(e -> {
            controller.setCell(lastPopupRow, lastPopupCol, 0);
            updateGamePanel();
            popup.hide();


        });

          for (int i = 1; i <= 9; i++) {

            //create a key string for the bundle to use it
            // the string is created by concatenating the word key
            // followed by the number or letter that has to be written.
            char ch = this.model.toCharCell(i);

            String key = new StringBuilder("key").append(ch).toString();
            popupButtons[i] = new Button(language.getText(key));
            hbox.getChildren().add(popupButtons[i]);

            // the game success dialog is only shown when the last
            //cell is filled using the popup dialog and not the solution button
            int buttonIndex = i;
            popupButtons[i].setOnAction(e -> {
                controller.setCell(lastPopupRow, lastPopupCol, buttonIndex);
                updateGamePanel();
                popup.hide();
                if(this.model.gameOver()){
                    if(showSuccessDialog()){
                        this.mainMenu.showMainMenu();
                    }
                }

                if(isGameDuidoku){
                    controller.computerMove();
                    updateGamePanel();
                }
            });
        }

        popup.getContent().add(hbox);

        // set auto hide
        popup.setAutoHide(true);
    }

    /**
     * Shows the popup in order for the user to select
     * which number to input in the game board cells
     * based on the row and column clicked
     * @param row row coordination
     * @param col column coordination
     */
    private void showPopup(int row, int col) {

        lastPopupCol = col;
        lastPopupRow = row;

        // all the buttons on the popup are able to be clicked
        for (int i = 0; i <= 9; i++) {
            popupButtons[i].setDisable(false);
            popupButtons[i].setId("popupOn");
        }
        // if the hint option is enabled the unavailable
        // buttons are not to be clicked
        if (hintEnabled) {

            ArrayList<Integer> hints = this.model.getHint(row, col);

            for (int i = 1; i <= 9; i++) {
                if (!hints.contains(i)) {
                    popupButtons[i].setDisable(true);
                    popupButtons[i].setId("popupOff");
                }
            }
        }
        popup.show(this.window);
    }

    /**
     * Shows the success dialog if the user solved the puzzle successfully
     * @return true if the user chose the OK option
     *          false otherwise
     */
    private boolean showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(language.getText("success"));
        alert.setHeaderText(language.getText("congrats"));
        alert.setContentText(language.getText("playagain"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}