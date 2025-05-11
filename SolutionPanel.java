import javax.swing.*;
import java.awt.*;

public class SolutionPanel extends JPanel {
    public SolutionPanel(int[][] solution) {
        setLayout(new GridLayout(9, 9));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setPreferredSize(new Dimension(180, 180));  // Small thumbnail

        Font font = new Font("SansSerif", Font.PLAIN, 10);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = new JTextField();
                cell.setText(String.valueOf(solution[row][col]));
                cell.setEditable(false);
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);
                cell.setBorder(BorderFactory.createMatteBorder(
                    (row % 3 == 0 ? 2 : 1), (col % 3 == 0 ? 2 : 1),
                    (row == 8 ? 2 : 1), (col == 8 ? 2 : 1),
                    Color.BLACK));
                add(cell);
            }
        }
    }
}
