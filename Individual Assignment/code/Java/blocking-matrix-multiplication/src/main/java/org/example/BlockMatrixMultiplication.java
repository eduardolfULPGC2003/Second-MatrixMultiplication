package org.example;
import java.util.Random;
public class BlockMatrixMultiplication {

    // Block size (tile size)

    public static void execute(int n, int block_size) {

        double[][] a = new double[n][n];
        double[][] b = new double[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = random.nextDouble();
                b[i][j] = random.nextDouble();
            }
        }

        double[][] c = new double[n][n]; // Result matrix

        blockMatrixMultiplication(a, b, c, n, block_size);
    }

    // Matrix multiplication using the blocking approach
    private static void blockMatrixMultiplication(double[][] a, double[][] b, double[][] c, int n, int block_size) {
        for (int i = 0; i < n; i += block_size) {
            for (int j = 0; j < n; j += block_size) {
                for (int k = 0; k < n; k += block_size) {
                    multiplyBlock(a, b, c, i, j, k, n, block_size);
                }
            }
        }
    }

    // Multiply individual blocks of the matrices
    private static void multiplyBlock(double[][] a, double[][] b, double[][] c, int rowBlock, int colBlock, int kBlock, int n, int block_size) {
        for (int i = rowBlock; i < Math.min(rowBlock + block_size, n); i++) {
            for (int j = colBlock; j < Math.min(colBlock + block_size, n); j++) {
                double sum = 0;
                for (int k = kBlock; k < Math.min(kBlock + block_size, n); k++) {
                    sum += a[i][k] * b[k][j];
                }
                c[i][j] += sum; // Update the result matrix
            }
        }
    }
}
