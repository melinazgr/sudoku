import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameSudokuView {

    public Scene scene;

    private Popup popup;
    private Model model;
    private Stage window;

    public GameSudokuView(Model model, Stage window){
        this.model = model;
        this.window = window;
        this.scene = createGamePanel();

        createPopup();

    }

    public Scene createGamePanel()
    {
        VBox layout = new VBox();
        HBox bottomPanel = new HBox();
        GridPane gamePanel = new GridPane();
        gamePanel.setVgap(25);
        gamePanel.setHgap(25);
        gamePanel.setAlignment(Pos.CENTER);

        Label logo = new Label("Sudoku");
        logo.setId("mainLabel");

        layout.getChildren().addAll(logo, gamePanel, bottomPanel);

        Button hintButton = new Button("Hint");
        Button clearButton = new Button("Clear");
        Button mainMenuButton = new Button("Main Menu");

        hintButton.setOnAction(e -> { System.out.println("hint"); });
        clearButton.setOnAction(e -> { System.out.println("clear"); });
        mainMenuButton.setOnAction(e -> { System.out.println("main menu"); });

        bottomPanel.getChildren().addAll(hintButton, clearButton, mainMenuButton);

        int gridSize = model.getGroupSize() * model.getGroupSize();

        GridPane [] grid = new GridPane[gridSize];
        StackPane [] tiles = new StackPane[model.getSize() * model.getSize()];

        for (int i = 0; i < gridSize; i++) {
            grid[i] = new GridPane();

            for (int row = 0; row < model.getGroupSize(); row++) {
                for (int col = 0; col < model.getGroupSize(); col++) {
                    int index = ( i * gridSize) + ( row * model.getGroupSize()) + col;

                    int cell = model.getDisplayCell((i / model.getGroupSize()) * model.getGroupSize() + row,
                            (i % model.getGroupSize()) * model.getGroupSize() + col);

                    Text text = new Text();

                    if(cell != 0){
                        text.setText(String.valueOf(cell));
                        text.setId("original");
                    }
                    else {
                        text.setId("zero");
                    }

                    Rectangle rect = new Rectangle(30,30, 50, 50);
                    rect.setId("rect");
                    rect.setOnMouseClicked(e -> {
                        if(!popup.isShowing()){
                            popup.show(window);
                        }
                    });

                    rect.setArcHeight(20);
                    rect.setArcWidth(20);
                    //rect.setFill(Color.TRANSPARENT);
                    //rect.setStroke(Color.BLUE);
                    tiles[index] = new StackPane(rect, text);
                    tiles[index].setPrefSize(70,70);

                    grid[i].add(tiles[index], col, row);
                }
            }

            gamePanel.add(grid[i], i % model.getGroupSize(), i / model.getGroupSize());
        }

        Scene scene = new Scene(layout, 700, 700);
        scene.getStylesheets().add("app.css");
        return scene;
    }




    private void createPopup()
    {
        // create a popup
        popup = new Popup();

        HBox hbox = new HBox();

        //TODO HINT

        for (int i = 0; i <= 9; i++)
        {
            Button button = new Button(String.valueOf(i));
            hbox.getChildren().add(button);

            button.setOnAction(e -> {
                popup.hide();
            });
        }

        hbox.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

        popup.getContent().add(hbox);

        // set auto hide
        popup.setAutoHide(true);
    }
}