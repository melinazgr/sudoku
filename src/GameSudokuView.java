import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameSudokuView {

    public Scene scene;

    private Popup popup;
    private Stage window;

    private Model model;
    private GameController controller;

    //saves the last cell position the user clicked to get the popup
    private int lastPopupCol;
    private int lastPopupRow;

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

    public Scene createGamePanel()
    {
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
        Button hintButton = new Button("Hint");
        Button clearButton = new Button("Clear");
        Button mainMenuButton = new Button("Main Menu");

        //bottom action buttons
        hintButton.setOnAction(e -> { System.out.println("hint"); });
        clearButton.setOnAction(e -> { System.out.println("clear"); });
        mainMenuButton.setOnAction(e -> { System.out.println("main menu"); });

        HBox bottomPanel = new HBox();
        bottomPanel.getChildren().addAll(hintButton, clearButton, mainMenuButton);

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

                    if(cell != 0){
                        cellText[index].setText(String.valueOf(cell));
                        cellText[index].setId("original");
                    }
                    else {
                        cellText[index].setText("");
                        cellText[index].setId("zero");
                    }

                    Rectangle rect = new Rectangle(30,30, 50, 50);
                    rect.setId("rect");
                    rect.setOnMouseClicked(e -> {
                        if(!popup.isShowing()){
                            popup.setX(e.getScreenX());
                            popup.setY(e.getScreenY());

                            lastPopupCol = tempCol;
                            lastPopupRow = tempRow;

                            popup.show(window);
                        }
                    });

                    //TODO  mve to Css
                    rect.setArcHeight(20);
                    rect.setArcWidth(20);
                    tiles[index] = new StackPane(rect, cellText[index]);
                    tiles[index].setPrefSize(70,70);

                    grid[i].add(tiles[index], col, row);
                }
            }

            gamePanel.add(grid[i], i % model.getGroupSize(), i / model.getGroupSize());
        }
    }

    private void updateGamePanel(){
        int tileSize = model.getSize() * model.getSize();

        for (int index = 0; index < tileSize ; index++) {
            int row = index / this.model.getSize();
            int col = index % this.model.getSize();

            int cell = this.model.getDisplayCell(row, col);

            //System.out.printf("[%d] (%d,%d) = %d\n",index, row, col, cell);

            if(cell != 0){
                cellText[index].setText(String.valueOf(cell));
                cellText[index].setId("original");
            }
            else {
                cellText[index].setText("");
                cellText[index].setId("zero");
            }
        }
    }

    private void createPopup()
    {
        // create a popup
        popup = new Popup();

        HBox hbox = new HBox();
        hbox.setId("popup");

        //TODO HINT

        for (int i = 0; i <= 9; i++)
        {
            Button button = new Button(String.valueOf(i));
            hbox.getChildren().add(button);

            int buttonIndex = i;
            button.setOnAction(e -> {
                controller.setCell(lastPopupRow, lastPopupCol, buttonIndex);
                updateGamePanel();
                popup.hide();
            });
        }

        popup.getContent().add(hbox);

        // set auto hide
        popup.setAutoHide(true);
    }
}