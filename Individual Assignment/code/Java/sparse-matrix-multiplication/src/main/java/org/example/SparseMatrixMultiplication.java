package org.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SparseMatrixMultiplication {

    public static void execute(int n, double percentage) {

        double[][] a = new double[n][n];
        double[][] b = new double[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double n1 = random.nextDouble();
                double n2 = random.nextDouble();
                if (n1>percentage){a[i][j] = n1;} else{a[i][j]=0;}
                if (n2>percentage){b[i][j] = n2;} else{b[i][j]=0;}
            }
        }


        // Convert matrices A and B to CSC format
        CSCMatrix cscA = convertToCSC(a);
        CSCMatrix cscB = convertToCSC(b);
        CSCMatrix resultMatrix = cscA.multiply(cscB);
    }
    // Sparse matrix in CSC format
    static class CSCMatrix {
        double[] values;          // Non-zero values
        int[] rowIndices;         // Row indices corresponding to values
        int[] colPointers;        // Column pointers

        int rows, cols;           // Number of rows and columns in the matrix

        CSCMatrix(double[] values, int[] rowIndices, int[] colPointers, int rows, int cols) {
            this.values = values;
            this.rowIndices = rowIndices;
            this.colPointers = colPointers;
            this.rows = rows;
            this.cols = cols;
        }

        // Method to multiply two CSC matrices
        public CSCMatrix multiply(CSCMatrix B) {

            List<Double> resultValues = new ArrayList<>();
            List<Integer> resultRowIndices = new ArrayList<>();
            List<Integer> resultColPointers = new ArrayList<>();
            resultColPointers.add(0);

            // Temporary array to store result for a single column in C
            double[] colResult = new double[this.rows];

            // Perform CSC matrix multiplication (this * B)
            for (int jB = 0; jB < B.cols; jB++) {
                // Clear colResult
                Arrays.fill(colResult, 0.0);

                // For each non-zero element in column jB of matrix B
                for (int k = B.colPointers[jB]; k < B.colPointers[jB + 1]; k++) {
                    int rowB = B.rowIndices[k];   // Row index in matrix B
                    double valB = B.values[k];    // Value of B at rowB and column jB

                    // Multiply column jB of B by corresponding row of this matrix
                    for (int i = this.colPointers[rowB]; i < this.colPointers[rowB + 1]; i++) {
                        int rowA = this.rowIndices[i];
                        double valA = this.values[i];
                        colResult[rowA] += valA * valB;
                    }
                }

                // Save the result of column jB in CSC format
                int nonZeroCount = 0;
                for (int i = 0; i < this.rows; i++) {
                    if (colResult[i] != 0.0) {
                        resultValues.add(colResult[i]);
                        resultRowIndices.add(i);
                        nonZeroCount++;
                    }
                }

                resultColPointers.add(resultColPointers.get(resultColPointers.size() - 1) + nonZeroCount);
            }

            // Convert lists to arrays
            double[] resultValuesArray = resultValues.stream().mapToDouble(Double::doubleValue).toArray();
            int[] resultRowIndicesArray = resultRowIndices.stream().mapToInt(Integer::intValue).toArray();
            int[] resultColPointersArray = resultColPointers.stream().mapToInt(Integer::intValue).toArray();

            return new CSCMatrix(resultValuesArray, resultRowIndicesArray, resultColPointersArray, this.rows, B.cols);
        }
    }

    // Method to convert a sparse matrix to CSC format
    public static CSCMatrix convertToCSC(double[][] matrix) {
        List<Double> valuesList = new ArrayList<>();
        List<Integer> rowIndicesList = new ArrayList<>();
        List<Integer> colPointersList = new ArrayList<>();

        int rows = matrix.length;
        int cols = matrix[0].length;

        colPointersList.add(0);

        // Traverse the matrix column by column and convert it into CSC format
        for (int j = 0; j < cols; j++) {
            int nonZeroCount = 0;
            for (int i = 0; i < rows; i++) {
                if (matrix[i][j] != 0) {
                    valuesList.add(matrix[i][j]);
                    rowIndicesList.add(i);
                    nonZeroCount++;
                }
            }
            colPointersList.add(colPointersList.get(colPointersList.size() - 1) + nonZeroCount);
        }

        // Convert lists to arrays
        double[] values = valuesList.stream().mapToDouble(Double::doubleValue).toArray();
        int[] rowIndices = rowIndicesList.stream().mapToInt(Integer::intValue).toArray();
        int[] colPointers = colPointersList.stream().mapToInt(Integer::intValue).toArray();

        return new CSCMatrix(values, rowIndices, colPointers, rows, cols);
    }
}
