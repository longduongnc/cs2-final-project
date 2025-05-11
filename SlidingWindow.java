import javax.swing.*;
import java.awt.*;

public class SlidingWindow extends JPanel {
    private final int[][] board;
    private final JTextField[][] cells;
    private final Sudoku_board boardGui;
    private int currentRow = -1;
    private int currentCol = -1;
    private int previousRow = -1;
    private int previousCol = -1;

    public SlidingWindow(Sudoku_board boardGui) {
        this.boardGui = boardGui;
        this.board = boardGui.getBoard();
        this.cells = boardGui.getCells();

        setLayout(new GridLayout(1, 9));
        setPreferredSize(new Dimension(600, 50));
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (board[row][col] == 0) {
                        currentRow = row;
                        currentCol = col;
                        showOptions(row, col);
                        return;
                    }
                }
            }

            currentRow = -1;
            currentCol = -1;
            removeAll();
            revalidate();
            repaint();
        });
    }

    public void restart() {
        start();
    }

    private void showOptions(int row, int col) {
        if (previousRow != -1 && previousCol != -1) {
            cells[previousRow][previousCol].setBackground(Color.WHITE);
        }

        cells[row][col].requestFocus();
        cells[row][col].setBackground(Color.YELLOW);

        previousRow = row;
        previousCol = col;

        removeAll();

        for (int num = 1; num <= 9; num++) {
            JButton btn = new JButton(String.valueOf(num));
            btn.setBackground(isValid(row, col, num) ? Color.GREEN : Color.RED);
            btn.setOpaque(true);
            btn.setBorderPainted(false);

            int choice = num;
            btn.addActionListener(e -> {
                if (isValid(row, col, choice)) {
                    board[row][col] = choice;
                    cells[row][col].setText(String.valueOf(choice));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.WHITE);
                    boardGui.addMove(row, col, choice);
                    start();
                }
            });

            add(btn);
        }

        revalidate();
        repaint();
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num)
                return false;
        }

        int boxRow = (row / 3) * 3, boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == num)
                    return false;
            }
        }

        return true;
    }

    public void highlightCell(int row, int col) {
        if (previousRow != -1 && previousCol != -1) {
            cells[previousRow][previousCol].setBackground(Color.WHITE);
        }

        cells[row][col].setBackground(Color.YELLOW);
        previousRow = row;
        previousCol = col;
    }

    public void showAutoSolveOptions(int row, int col) {
        removeAll();
        for (int num = 1; num <= 9; num++) {
            JButton btn = new JButton(String.valueOf(num));
            btn.setEnabled(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setBackground(isValid(row, col, num) ? Color.GREEN : Color.RED);
            add(btn);
        }
        revalidate();
        repaint();
    }
    
}
