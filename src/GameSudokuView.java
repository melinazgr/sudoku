import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameSudokuView {

    public Scene scene;

    private Popup popup;
    private Button[] popupButtons;
    private Stage window;

    private Model model;
    private GameController controller;

    //saves the last cell position the user clicked to get the popup
    private int lastPopupCol;
    private int lastPopupRow;

    private boolean hintEnabled;

    GridPane[] grid;
    StackPane[] tiles;
    Text[] cellText;

    public GameSudokuView(Model model, GameController controller, Stage window) {
        this.model = model;
        this.window = window;
        this.controller = controller;
        this.scene = createGamePanel();
        createPopup();
    }

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
        hintButton.setOnAction(e -> {
            hintEnabled = hintButton.isSelected();
        });

        clearButton.setOnAction(e -> {
            controller.clearGame();
            updateGamePanel();
        });

        solutionButton.setOnAction(e -> {
            controller.showSolution();
            updateGamePanel();
        });

        mainMenuButton.setOnAction(e -> {
            System.out.println("main menu");
        });

        HBox bottomPanel = new HBox();
        bottomPanel.getChildren().addAll(hintButton, clearButton, solutionButton, mainMenuButton);

        VBox layout = new VBox();
        layout.getChildren().addAll(logo, gamePanel, bottomPanel);

        createGamePanel(gamePanel);

        Scene scene = new Scene(layout, 700, 700);
        scene.getStylesheets().add("app.css");
        return scene;
    }

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

                    //System.out.printf("[%d] (%d,%d) = %d\n",index, tempRow, tempCol, cell);


                    cellText[index] = new Text();

                    if (cell != 0) {
                        cellText[index].setText(String.valueOf(cell));
                        cellText[index].setId("original");
                    } else {
                        cellText[index].setText("");
                        cellText[index].setId("zero");
                    }

                    Rectangle rect = new Rectangle(30, 30, 50, 50);
                    rect.setId("rect");
                    rect.setOnMouseClicked(e -> {
                        if (!popup.isShowing()) {
                            popup.setX(e.getScreenX());
                            popup.setY(e.getScreenY());

                            showPopup(tempRow, tempCol);
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
        popup.show(window);
    }


    private void createPopup() {
        // create a popup
        popup = new Popup();

        HBox hbox = new HBox();
        hbox.setId("popup");

        popupButtons = new Button[10];
        for (int i = 0; i <= 9; i++) {
            popupButtons[i] = new Button(String.valueOf(i));
            hbox.getChildren().add(popupButtons[i]);

            int buttonIndex = i;
            popupButtons[i].setOnAction(e -> {
                controller.setCell(lastPopupRow, lastPopupCol, buttonIndex);
                updateGamePanel();
                popup.hide();
            });
        }

        popup.getContent().add(hbox);

        // set auto hide
        popup.setAutoHide(true);
    }

    private void updateGamePanel() {
        int tileSize = model.getSize() * model.getSize();

        for (int index = 0; index < tileSize; index++) {
            int row = index / this.model.getSize();
            int col = index % this.model.getSize();

            int cell = this.model.getDisplayCell(row, col);

            //System.out.printf("[%d] (%d,%d) = %d\n",index, row, col, cell);

            if (cell != 0) {
                cellText[index].setText(String.valueOf(cell));
                cellText[index].setId("original");
            } else {
                cellText[index].setText("");
                cellText[index].setId("zero");
            }
        }
    }

}
