import java.util.ArrayList;

public class Sudoku_hw4 {
    
    public static int[] find_possible_values(int[][] matrix, int x, int y) {
        ArrayList<Integer> possible_values = new ArrayList<>();

        // Iterate through all possible values
        for (int value = 1; value <= 9; value++) {
            boolean valid = true;
            // Check if row and column has a value
            for (int i = 0; i < 9; i++) {
                if (matrix[x][i] == value || matrix[i][y] == value) {
                    valid = false;
                    break;
                }
            }
            // Check if block has a value
            if (valid) {
                for (int i = 0; i < 9; i++) {
                    if (matrix[3 * (x / 3) + i / 3][3 * (y / 3) + i % 3] == value) {
                        valid = false;
                        break;
                    }
                }
            }
            // If value is valid, add to possible values
            if (valid) {
                possible_values.add(value);
            }
        }

        return possible_values.stream().mapToInt(i -> i).toArray();
    }

    public static void solve(int[][] matrix) {
        solve(matrix, 0, 0);
    }

    public static boolean solve(int[][] matrix, int x, int y) {
        // Go to the next 0 position
        while (matrix[x][y] != 0) {
            // Base case
            if (x == 8 && y == 8) return true;

            // Move to the next position
            if (y == 8) {
                y = 0;
                x++;
            } else {
                y++;
            }
        }

        int[] possible_values = find_possible_values(matrix, x, y);
        
        // Pruning case
        if (possible_values.length == 0) return false;

        // Recursive branching case
        for (int value: possible_values) {
            matrix[x][y] = value;
            if (solve(matrix, x, y)) return true;
        }

        // Reset and backtrack
        matrix[x][y] = 0;
        return false;
    }

    private static void print_matrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("[");
            for (int i = 0; i < row.length; i++) {
                System.out.print(row[i]);
                if (i < row.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix_1 = {
            {0, 5, 0, 0, 0, 2, 0, 0, 6},
            {0, 8, 0, 0, 0, 6, 7, 0, 3},
            {0, 0, 0, 7, 4, 0, 0, 0, 8},
            {6, 7, 0, 9, 8, 0, 0, 0, 0},
            {0, 3, 1, 0, 5, 0, 6, 0, 9},
            {0, 0, 0, 0, 0, 3, 8, 2, 0},
            {0, 0, 0, 0, 0, 0, 1, 8, 0},
            {0, 0, 0, 3, 0, 0, 0, 0, 2},
            {9, 2, 0, 5, 0, 0, 0, 7, 0}
        };
        int[][] results = {
            Sudoku_hw4.find_possible_values(matrix_1, 0, 0), // returns [1, 3, 4, 7]
            Sudoku_hw4.find_possible_values(matrix_1, 3, 5), // returns [1, 4]
            Sudoku_hw4.find_possible_values(matrix_1, 6, 3) // returns [2, 4, 6]
        };
        print_matrix(results);

        solve(matrix_1);
        print_matrix(matrix_1);
        
    }

}
