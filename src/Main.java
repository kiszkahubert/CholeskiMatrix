import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        readData();
    }

    public static void readData() {
        Scanner in = new Scanner(System.in);
        System.out.println("Podaj liczbę wierszy macierzy");
        int m = in.nextInt();
        System.out.println("Podaj liczbę kolumn macierzy");
        int n = in.nextInt();
        double[][] matrix = new double[m][n];
        System.out.println("Wprowadź elementy macierzy");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextDouble();
            }
        }
        double[] b = new double[m];
        System.out.println("Wprowadź elementy wektora wyrazów wolnych b");
        for (int i = 0; i < m; i++) {
            b[i] = in.nextDouble();
        }
        double[][] L = calculateLowerTriangular(matrix, m);
        double[] y = solveLowerTriangular(L, b, m);
        double[] x = solveUpperTriangular(transpose(L, m), y, m);

        System.out.println("Rozwiązanie układu równań: ");
        for (double d : x) {
            System.out.print(d + " ");
        }
    }

    public static double[][] calculateLowerTriangular(double[][] matrix, int n) {
        double[][] lower = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                double sum = 0;
                if (j == i) {
                    for (int k = 0; k < j; k++)
                        sum += Math.pow(lower[j][k], 2);
                    lower[j][j] = Math.sqrt(matrix[j][j] - sum);
                } else {
                    for (int k = 0; k < j; k++)
                        sum += lower[i][k] * lower[j][k];
                    lower[i][j] = (matrix[i][j] - sum) / lower[j][j];
                }
            }
        }
        System.out.println("Dolna macierz trójkątna:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.printf("%.2f\t",lower[i][j]);
            System.out.println();
        }
        return lower;
    }

    public static double[][] transpose(double[][] matrix, int n) {
        double[][] transposedMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }

    public static double[] solveLowerTriangular(double[][] L, double[] b, int n) {
        double[] y = new double[n];
        y[0] = b[0] / L[0][0];
        for (int i = 1; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L[i][j] * y[j];
            }
            y[i] = (b[i] - sum) / L[i][i];
        }
        return y;
    }

    public static double[] solveUpperTriangular(double[][] U, double[] y, int n) {
        double[] x = new double[n];
        x[n - 1] = y[n - 1] / U[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / U[i][i];
        }
        return x;
    }
}
