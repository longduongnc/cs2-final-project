import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Sudoku_board extends JFrame {
    private static final int SIZE = 9;
    private final int[][] board;
    private final JTextField[][] cells = new JTextField[SIZE][SIZE];
    private final JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
    private final Stack<Step> moveHistory = new Stack<>();
    private final JPanel solutionListPanel = new JPanel();
    private final JScrollPane solutionScrollPane;
    private JButton reverseButton;
    private SlidingWindow slidingWindow;

    public Sudoku_board(int[][] initialBoard) {
        this.board = initialBoard;
        setTitle("Sudoku Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLayout(new BorderLayout());

        Font font = new Font("Monospaced", Font.BOLD, 20);

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);
                cell.setPreferredSize(new Dimension(50, 50));
                cell.setOpaque(true);

                int top = (r % 3 == 0) ? 4 : 1;
                int left = (c % 3 == 0) ? 4 : 1;
                int bottom = (r == 8) ? 4 : 1;
                int right = (c == 8) ? 4 : 1;

                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                if (board[r][c] != 0) {
                    cell.setText(String.valueOf(board[r][c]));
                    cell.setEditable(false);
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    cell.setBackground(Color.WHITE);
                }

                cells[r][c] = cell;
                gridPanel.add(cell);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        solutionListPanel.setLayout(new BoxLayout(solutionListPanel, BoxLayout.Y_AXIS));
        solutionScrollPane = new JScrollPane(solutionListPanel);
        solutionScrollPane.setPreferredSize(new Dimension(200, 0));
        add(solutionScrollPane, BorderLayout.EAST);
    }

    public void setSlidingWindow(SlidingWindow slidingWindow) {
        this.slidingWindow = slidingWindow;

        reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(e -> {
            undoLastMove();
            slidingWindow.restart();
        });

        JButton solveButton = new JButton("Auto-Solve");
        solveButton.addActionListener(e -> {
            new Thread(() -> {
                setReverseEnabled(false);
                AutoSolver solver = new AutoSolver(this, cells, slidingWindow);
                solver.solveAnimated();
                setReverseEnabled(true);
            }).start();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(slidingWindow, BorderLayout.CENTER);
        bottomPanel.add(reverseButton, BorderLayout.EAST);
        bottomPanel.add(solveButton, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public int[][] getBoard() {
        return board;
    }

    public JTextField[][] getCells() {
        return cells;
    }

    public void addMove(int row, int col, int value) {
        moveHistory.push(new Step(row, col, value));
    }

    public void undoLastMove() {
        if (!moveHistory.isEmpty()) {
            Step last = moveHistory.pop();
            board[last.row][last.col] = 0;
            JTextField cell = cells[last.row][last.col];
            cell.setText("");
            cell.setEditable(true);
            cell.setBackground(Color.WHITE);
        }
    }

    public void saveSolution(int[][] solution) {
        SolutionPanel panel = new SolutionPanel(solution);
        solutionListPanel.add(panel);
        solutionListPanel.revalidate();
        solutionListPanel.repaint();
    }

    public void setReverseEnabled(boolean enabled) {
        if (reverseButton != null) {
            reverseButton.setEnabled(enabled);
        }
    }
}
