package org.example;

import java.util.Random;

public class ColumnMajorMatrixMultiplication {
    public static void execute(int n) {

        double[][] a = new double[n][n];
        double[][] b = new double[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = random.nextDouble();
                b[i][j] = random.nextDouble();
            }
        }
        double[][] c = new double[n][n];

        columnMajorMatrixMultiplication(a, b, c, n);
    }

    private static void columnMajorMatrixMultiplication(double[][] A, double[][] B, double[][] C, int N) {
        for (int j = 0; j < N; j++) { // Loop over columns first
            for (int i = 0; i < N; i++) {
                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];  // Keep the correct multiplication logic
                }
            }
        }
    }
}
