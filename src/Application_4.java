import java.util.Arrays; 

public class Application_4 {
    public static void main(String[] args) {
        int[] primitive_array = new int[]{1, 2, 3, 4, 5};
        String[] reference_array = new String[]{"one", "two", "three"};

        System.out.println("[primitive array]: " + Arrays.toString(primitive_array));
        System.out.println("[reference array]: " + Arrays.toString(reference_array));
 
        primitive_array = resize_array(primitive_array, 4);
        reference_array = resize_array(reference_array, 2);

        System.out.println("[primitive array after resizing]: " + Arrays.toString(primitive_array));
        System.out.println("[reference array after resizing]: " + Arrays.toString(reference_array));

        String primitive_array_string = array_to_string(primitive_array);
        String reference_array_string = array_to_string(reference_array);

        System.out.println("[primitive array as a string]: " + primitive_array_string);
        System.out.println("[reference array as a string]: " + reference_array_string);

        System.out.println("\nmatrix:");
        int[][] primitive_matrix = create_matrix(3, 3, 1);
        print_matrix(primitive_matrix);

        System.out.println("\nreference matrix:");
        String[][] reference_matrix = create_matrix(3, 3, "ref");
        print_matrix(reference_matrix);

        System.out.println("\nmatrix after resizing:");
        primitive_matrix = resize_matrix(primitive_matrix, 2, 2);
        print_matrix(primitive_matrix);

        System.out.println("\nreference matrix after resizing:");
        reference_matrix = resize_matrix(reference_matrix, 2, 2);
        print_matrix(reference_matrix);

        String matrix_string = matrix_to_string(primitive_matrix);
        System.out.println("\nprimitive matrix as a string: \n" + matrix_string);

        String matrix_string_ref = matrix_to_string(reference_matrix); 
        System.out.println("\nprimitive matrix as a string: \n" + matrix_string_ref);
    }

    public static int[] resize_array(int[] array, int new_size) {
        int[] new_array = new int[new_size];
        System.arraycopy(array, 0, new_array, 0, Math.min(array.length, new_size));
        return new_array;
    }

    public static String[] resize_array(String[] array, int new_size) {
        String[] new_array = new String[new_size];
        System.arraycopy(array, 0, new_array, 0, Math.min(array.length, new_size));
        return new_array;
    }

    public static String array_to_string(int[] array) {
        return Arrays.toString(array);
    }

    public static String array_to_string(String[] array) {
        return Arrays.toString(array);
    }

    public static int[][] create_matrix(int rows, int cols, int defaultValue) {
        int[][] matrix = new int[rows][cols];
        for (int[] row : matrix) {
            Arrays.fill(row, defaultValue);
        }
        return matrix;
    }

    public static String[][] create_matrix(int rows, int cols, String defaultValue) {
        String[][] matrix = new String[rows][cols];
        for (String[] row : matrix) {
            Arrays.fill(row, defaultValue);
        }
        return matrix;
    }

    public static void print_matrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void print_matrix(String[][] matrix) {
        for (String[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static int[][] resize_matrix(int[][] matrix, int new_rows, int new_cols) {
        int[][] new_matrix = new int[new_rows][new_cols];
        for (int i = 0; i < Math.min(matrix.length, new_rows); i++) {
            System.arraycopy(matrix[i], 0, new_matrix[i], 0, Math.min(matrix[i].length, new_cols));
        }
        return new_matrix;
    }
    
    public static String[][] resize_matrix(String[][] matrix, int new_rows, int new_cols) {
        String[][] new_matrix = new String[new_rows][new_cols];
        for (int i = 0; i < Math.min(matrix.length, new_rows); i++) {
            System.arraycopy(matrix[i], 0, new_matrix[i], 0, Math.min(matrix[i].length, new_cols));
        }
        return new_matrix;
    }

    public static String matrix_to_string(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }

    public static String matrix_to_string(String[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : matrix) {
            sb.append(Arrays.toString(row)).append("\n");
        } 
        return sb.toString();
    }
}

