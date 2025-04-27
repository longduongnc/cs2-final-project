import java.awt.*;
import javax.swing.*;

public class SlidingWindow extends JFrame {
    private JPanel[] numberPanels;

    // Constructor
    public SlidingWindow() {
        //Make the Window Frame
        super("Sudoku Sliding Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 100);
        setResizable(false);
        setLayout(new GridLayout(1, 9, 10, 10));

        // Array to hold the panels for numbers 1 to 9
        numberPanels = new JPanel[9];

        // Create panels for numbers 1 to 9
        for (int i = 0; i < 9; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.LIGHT_GRAY); // Default color
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
            panel.add(new JLabel(String.valueOf(i + 1))); // Add the number label
            panel.setPreferredSize(new Dimension(50, 50));
            numberPanels[i] = panel;
            add(panel);
        }

        setVisible(true);
    }

    // Method to set panel state using a boolean for "valid" or "invalid"
    /**
     * Set the color of a panel to indicate its state (valid or invalid).
     *
     * @param index Index of the panel to update (0-based)
     * @param isValid Boolean state for the panel (true for valid, false for invalid)
     */
    private void setPanelState(int index, boolean isValid) {
        if (index < 0 || index >= numberPanels.length) return;

        if (isValid) {
            // "valid" state - set to light green
            numberPanels[index].setBackground(new Color(144, 238, 144));
        } else {
            // "invalid" state - set to light red
            numberPanels[index].setBackground(new Color(255, 102, 102));
        }
    }

    public static void main(String[] args) {
        SlidingWindow slidingWindow = new SlidingWindow();

        // Example of using the window to set panel states:
        slidingWindow.setPanelState(0, true);   // Set the first panel to valid (green)
        slidingWindow.setPanelState(1, false);  // Set the second panel to invalid (red)
    }
}
