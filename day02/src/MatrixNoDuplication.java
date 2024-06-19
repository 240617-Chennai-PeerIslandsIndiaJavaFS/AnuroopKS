import java.util.Scanner;

public class MatrixNoDuplication {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of rows:");
        int rows = scan.nextInt();
        System.out.println("Enter the number of columns:");
        int cols = scan.nextInt();

        int[][] arr = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {
                    System.out.println("Enter the value:");
                    int n = scan.nextInt();
                    if (isUnique(arr, n)) {
                        arr[i][j] = n;
                        break;
                    } else {
                        System.out.println("Value already exists. Enter another value:");
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isUnique(int[][] arr, int n) {
        for (int[] row : arr) {
            for (int elem : row) {
                if (elem == n) {
                    return false;
                }
            }
        }
        return true;
    }
}
