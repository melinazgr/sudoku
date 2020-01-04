/**
 * This class implements all the actions the UI performs on the game model.
 * It implements the MVC pattern (Model View Controller)
 *
 * @author Melina Zikou
 */

public class GameController {

    private Model model;

    public GameController(Model model){
        this.model = model;
    }

    public void setCell(int lastPopupRow, int lastPopupCol, int buttonIndex) {

        if(buttonIndex == 0) {
            this.model.clearCell(lastPopupRow, lastPopupCol);
        }

        else{
            this.model.setCell(lastPopupRow, lastPopupCol, buttonIndex);

        }
    }

    public void clearGame() {
        this.model.restartGame();
    }

    public void showSolution() {
        this.model.solveSudoku();
    }
}
