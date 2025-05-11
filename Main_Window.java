import javax.swing.*;

public class Main_Window {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int[][] initialBoard = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
            };

            Sudoku_board board = new Sudoku_board(initialBoard);
            SlidingWindow selector = new SlidingWindow(board);

            board.setSlidingWindow(selector);  // ensures bottom panel is setup
            board.setVisible(true);            // ✅ make window visible AFTER everything
            selector.start();                  // begin logic
        });
    }
}
