import java.awt.*;
import javax.swing.*;
/**
 * MAIN WINDOW
 * Displays the full Sudoku solver UI.
 * 
 * Methods of Sudoku_board:
 *    setNumber(int row, int column, int value)
 *    getNumber(int row, int column)
 *    changeColor(int row, int column, Color color)
 *    reset()
 *    checkBoard()
 *    validateBoard()
 *    isComplete()
 * 
 * Methods of SlidingWindow:
 *    setPanelState(int index, boolean isValid)
 */
public class Main_Window extends JFrame {

    private Sudoku_board board;
    private SlidingWindow slidingWindow;

    public Main_Window() {
        super("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(new BorderLayout());

        //Create instances of the components
        board = new Sudoku_board(
            new int [][] {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
            }
        );

        //Create the sliding window
        slidingWindow = new SlidingWindow();
        
        //Add the components to the frame
        add(board, BorderLayout.CENTER); //Add the main board
        add(slidingWindow, BorderLayout.NORTH); //Add the SlidingWindow on top

        setVisible(true);
    }

    public static void main(String[] args) {
        Main_Window main = new Main_Window();
        
        main.board.setNumber(0, 0, 9);
        main.board.setNumber(1, 1, 8);
        main.slidingWindow.setPanelState(0, true);
        main.slidingWindow.setPanelState(1, false);
    }
}