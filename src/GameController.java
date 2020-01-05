/**
 * This class implements all the actions the UI performs on the game model.
 * It implements the MVC pattern (Model View Controller)
 *
 * @author Melina Zikou
 */
public class GameController {

    private Model model;

    //constructor
    public GameController(Model model){
        this.model = model;
    }

    /**
     * insert in a cell with given coordinates a given number
     * @param lastPopupRow row coordinate
     * @param lastPopupCol column coordinate
     * @param buttonIndex number to insert
     */
    public void setCell(int lastPopupRow, int lastPopupCol, int buttonIndex) {

        if(buttonIndex == 0) {
            this.model.clearCell(lastPopupRow, lastPopupCol);
        }

        else{
            this.model.setCell(lastPopupRow, lastPopupCol, buttonIndex);
        }
    }

    /**
     * clears all the cells that contain user entries
     */
    public void clearGame() {
        this.model.restartGame();
    }

    /**
     * show solution to the current puzzle
     */
    public void showSolution() {
        this.model.solveSudoku();
    }
}