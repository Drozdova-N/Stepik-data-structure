package com.company;

import java.util.Arrays;
import java.util.Random;
/*
* */
public class Matrix {
    private int[][] matrix;
    private final int countRows;
    private final int countColumns;

    public Matrix(int countRows, int countColumns) {
        if (countRows < 1 || countColumns < 1) {
            throw new MatrixException("MatrixException : incorrect size matrix");
        }
        this.matrix = new int[countRows][countColumns];
        this.countRows = countRows;
        this.countColumns = countColumns;
        addRandomElements();


    }

    protected void addElements() {
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                if (i == j) {
                    this.setElement(i, j, 1);
                } else this.setElement(i, j, 0);
            }
        }
    }

    protected void addRandomElements() {
        Random random = new Random();
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                this.setElement(i, j, random.nextInt(3));
            }
        }
    }

    public Matrix sum(Matrix matrixOther) {
        if (matrixOther.getCountRows() != this.getCountRows()
                || matrixOther.getCountColumns() != this.getCountColumns()) {
            throw new MatrixException("MatrixException : matrix of different sizes");
        }
        Matrix newMatrix = new Matrix(matrixOther.countRows, matrixOther.countColumns);
        int value;
        for (int i = 0; i < newMatrix.getCountRows(); i++) {
            for (int j = 0; j < newMatrix.getCountColumns(); j++) {
                newMatrix.matrix[i][j] = this.matrix[i][j] + matrixOther.matrix[i][j];


            }
        }
        return newMatrix;
    }

    public Matrix product(Matrix otherMatrix) {
        if (this.getCountColumns() != otherMatrix.getCountRows()) {
            throw new MatrixException("MatrixException : the number of columns in the first " +
                    "matrix is not equal to the number " +
                    "of rows in the second matrix.");
        }
        Matrix newMatrix = new Matrix(this.countRows, otherMatrix.countColumns);
        for (int i = 0; i < this.countRows; i++) {
            for (int j = 0; j < otherMatrix.countColumns; j++) {
                int value = 0;
                for (int k = 0; k < this.getCountColumns(); k++) {
                    value+= this.matrix[i][k] * otherMatrix.matrix[k][j];
                }
                newMatrix.matrix[i][j]= value;
            }
        }
        return newMatrix;
    }


    public void setElement(int row, int column, int value) {
        this.matrix[row][column] = value;
    }

    public int getElement(int row, int column) {
        return this.matrix[row][column];
    }

    public int getCountRows() {
        return this.countRows;
    }

    public int getCountColumns() {
        return this.countColumns;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int[] mas : matrix) {
            for (int element : mas) {
                str.append(element + " ");
            }
            str.append("\n");
        }
        String endString = str.toString();
        return endString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix matrix1 = (Matrix) o;
        if (this.getCountRows() != matrix1.getCountRows() | this.getCountColumns() != matrix1.getCountColumns())
            return false;
        for (int i = 0; i < this.getCountRows(); i++) {
            for (int j = 0; j < this.getCountColumns(); j++) {
                if (this.getElement(i, j) != matrix1.getElement(i, j)) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int[] mas : matrix)
            hash += Arrays.hashCode(mas);
        return hash;
    }

}