import javax.swing.*;
import java.awt.Color;

public class AutoSolver {
    private final int[][] board;
    private final JTextField[][] cells;
    private final Sudoku_board boardGui;
    private final SlidingWindow sliding;

    public AutoSolver(Sudoku_board boardGui, JTextField[][] cells, SlidingWindow sliding) {
        this.boardGui = boardGui;
        this.cells = cells;
        this.board = deepCopy(boardGui.getBoard());
        this.sliding = sliding;
    }

    public void solveAnimated() {
        boardGui.setReverseEnabled(false);
        solveRecursive(0, 0);
        boardGui.setReverseEnabled(true);
    }

    private boolean solveRecursive(int row, int col) {
        if (row == 9) {
            boardGui.saveSolution(deepCopy(board));
            return false; // continue searching for more
        }
    
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;
    
        if (board[row][col] != 0) {
            return solveRecursive(nextRow, nextCol);
        }
    
        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                updateGui(row, col, num, new Color(255, 255, 153));
    
                solveRecursive(nextRow, nextCol); // ← no early return
    
                board[row][col] = 0;
                updateGui(row, col, "", new Color(255, 150, 150));
            }
        }
    
        return false;
    }
    

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num)
                return false;
        }

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == num)
                    return false;
            }
        }

        return true;
    }

    private void updateGui(int row, int col, Object value, Color bg) {
        SwingUtilities.invokeLater(() -> {
            cells[row][col].setText(value.toString());
            cells[row][col].setBackground(bg);
            sliding.highlightCell(row, col);
            sliding.showAutoSolveOptions(row, col);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(original[r], 0, copy[r], 0, 9);
        }
        return copy;
    }
}
