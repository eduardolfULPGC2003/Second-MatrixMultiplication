package org.example;

import java.util.Random;

public class StrassenMatrixMultiplication {
    public static void execute(int n) {

        double[][] a = new double[n][n];
        double[][] b = new double[n][n];
        double[][] c = new double[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = random.nextDouble();
                b[i][j] = random.nextDouble();
            }
        }
        c = StrassenMultiply(a, b);
    }
    public static double[][] StrassenMultiply(double[][] A, double[][] B){
        int n = A.length;

        // Caso base: multiplicación de 1x1
        if (n == 1) {
            double[][] C = { { A[0][0] * B[0][0] } };
            return C;
        }

        // Dividir las matrices en 4 submatrices
        int newSize = n / 2;
        double[][] A11 = new double[newSize][newSize];
        double[][] A12 = new double[newSize][newSize];
        double[][] A21 = new double[newSize][newSize];
        double[][] A22 = new double[newSize][newSize];

        double[][] B11 = new double[newSize][newSize];
        double[][] B12 = new double[newSize][newSize];
        double[][] B21 = new double[newSize][newSize];
        double[][] B22 = new double[newSize][newSize];

        // Llenar las submatrices
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + newSize];
                A21[i][j] = A[i + newSize][j];
                A22[i][j] = A[i + newSize][j + newSize];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + newSize];
                B21[i][j] = B[i + newSize][j];
                B22[i][j] = B[i + newSize][j + newSize];
            }
        }

        // Calcular las siete multiplicaciones de Strassen
        double[][] M1 = StrassenMultiply(add(A11, A22), add(B11, B22));
        double[][] M2 = StrassenMultiply(add(A21, A22), B11);
        double[][] M3 = StrassenMultiply(A11, subtract(B12, B22));
        double[][] M4 = StrassenMultiply(A22, subtract(B21, B11));
        double[][] M5 = StrassenMultiply(add(A11, A12), B22);
        double[][] M6 = StrassenMultiply(subtract(A21, A11), add(B11, B12));
        double[][] M7 = StrassenMultiply(subtract(A12, A22), add(B21, B22));

        // Calcular las submatrices del resultado
        double[][] C11 = add(subtract(add(M1, M4), M5), M7);
        double[][] C12 = add(M3, M5);
        double[][] C21 = add(M2, M4);
        double[][] C22 = add(subtract(add(M1, M3), M2), M6);

        // Combinar las submatrices en la matriz resultante
        double[][] C = new double[n][n];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                C[i][j] = C11[i][j];
                C[i][j + newSize] = C12[i][j];
                C[i + newSize][j] = C21[i][j];
                C[i + newSize][j + newSize] = C22[i][j];
            }
        }

        return C;
    }

    // Función para sumar matrices
    private static double[][] add(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    // Función para restar matrices
    private static double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }
}
