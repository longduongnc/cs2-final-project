import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Sudoku_board extends JPanel {

    private JTextField[][] board;

    public Sudoku_board() {
        this(null);
    }

    // Constructor
    public Sudoku_board(int[][] values) {
        setLayout(new GridLayout(9, 9));

        // Create new board
        board = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            final int index = i;
            for (int j = 0; j < 9; j++) {
                final int jndex = j;
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);

                // Add a trigger to change value when typed in
                field.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int value = Integer.parseInt(field.getText());
                            if (value < 1 || value > 9) {
                                throw new NumberFormatException();
                            }
                            int row = board[index][jndex].getLocation().y / 50;
                            int column = board[index][jndex].getLocation().x / 50;
                            setNumber(row, column, value);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(getRootPane(), "Invalid number");
                            field.setText("");
                        }
                    }
                });
                add(field);
                board[i][j] = field;

                // Set values of the board
                if (values != null && values[i][j] != 0) {
                    setNumber(i, j, values[i][j]);
                }
            }
        }
    }

    public void setNumber(int row, int column, int value) {
        board[row][column].setText(String.valueOf(value));
        board[row][column].transferFocus();
    }

    public void changeColor(int row, int column, Color color) {
        board[row][column].setBackground(color);
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setText("");
            }
        }
    }

    // Check if board contains no errors, returns true if valid 
    // NOTE: Incomplete boards without errors return true
    public boolean validateBoard() {
        // Check repetition in rows
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[9];
            for (int j = 0; j < 9; j++) {
                String text = board[i][j].getText();
                if (!text.isEmpty()) {
                    int num = Integer.parseInt(text) - 1;
                    if (seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }
        }

        // Check repetition in columns
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[9];
            for (int i = 0; i < 9; i++) {
                String text = board[i][j].getText();
                if (!text.isEmpty()) {
                    int num = Integer.parseInt(text) - 1;
                    if (seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }
        }

        // Check repetition in blocks
        for (int block = 0; block < 9; block++) {
            boolean[] seen = new boolean[9];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int row = 3 * (block / 3) + i;
                    int col = 3 * (block % 3) + j;
                    String text = board[row][col].getText();
                    if (!text.isEmpty()) {
                        int num = Integer.parseInt(text) - 1;
                        if (seen[num]) {
                            return false;
                        }
                        seen[num] = true;
                    }
                }
            }
        }
        return true;
    }

    // Check if board is complete and valid
    public boolean isComplete() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return validateBoard();
    }

    public static void main(String[] args) {
        
        // Creating a frame (window)
        JFrame frame = new JFrame("Sudoku Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        
        // This is how to use this class, by adding it to a frame
        Sudoku_board board = new Sudoku_board(
            new int[][] {
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
        frame.add(board);
        
        // Display the frame (must be AFTER adding the board for some reason)
        frame.setVisible(true);

        // Set some values
        board.setNumber(0, 0, 5);
        board.setNumber(1, 1, 3);

        // Test to change color
        board.changeColor(0, 0, Color.MAGENTA);
        board.changeColor(1, 1, Color.LIGHT_GRAY);

    }

}

