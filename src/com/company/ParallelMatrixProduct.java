package com.company;

import java.util.concurrent.*;

public class ParallelMatrixProduct {
    private int countThreads;
    private ExecutorService thread;
    private Matrix m1;
    private Matrix m2;
    private Matrix newMatrix;

    public ParallelMatrixProduct(int countThreads) {
        this.countThreads = countThreads;

    }

    public Matrix product2(Matrix firstMatrix, Matrix secondMatrix) throws InterruptedException {
        if (firstMatrix.getCountColumns() != secondMatrix.getCountRows()) {
            throw new MatrixException("MatrixException : the number of columns in the first " +
                    "matrix is not equal to the number " +
                    "of rows in the second matrix.");
        }
        thread = Executors.newFixedThreadPool(countThreads);
        this.m1 = firstMatrix;
        this.m2 = secondMatrix;
        newMatrix = new Matrix(m1.getCountRows(), m2.getCountColumns());
        for (int i = 0; i < m1.getCountRows(); i++) {
            thread.submit(new Product2(i));
        }
        thread.shutdown();
        thread.awaitTermination(1, TimeUnit.DAYS);
        return newMatrix;
    }

    public Matrix product(Matrix firstMatrix, Matrix secondMatrix) throws InterruptedException {
        if (firstMatrix.getCountColumns() != secondMatrix.getCountRows()) {
            throw new MatrixException("MatrixException : the number of columns in the first " +
                    "matrix is not equal to the number " +
                    "of rows in the second matrix.");
        }
        this.m1 = firstMatrix;
        this.m2 = secondMatrix;
        newMatrix = new Matrix(m1.getCountRows(), m2.getCountColumns());
        Product[] thread = new Product[countThreads];
        // распределение строк матрицы между заданным количеством потоков-1
        int begin = 0;
        int dif = m1.getCountRows() / (countThreads - 1);
        int end = dif;
        for (int i = 0; i < countThreads - 1; i++) {
            thread[i] = new Product(begin, end);
            thread[i].start();
            begin += dif;
            end += dif;

        }
        // последний поток получает осатвшиеся строки матрицы
        thread[countThreads - 1] = new Product(m1.getCountRows() - (m1.getCountRows() % countThreads - 1), m1.getCountRows());
        thread[countThreads - 1].start();
        for (Product p : thread) {
            p.join();
        }

        return newMatrix;

    }

    class Product extends Thread {
        private int begin;
        private int end;

        public Product(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = begin; i < end; i++) {
                for (int j = 0; j < m2.getCountColumns(); j++) {
                    int value = 0;
                    for (int k = 0; k < m1.getCountColumns(); k++) {
                        value += m1.getElement(i, k) * m2.getElement(k, j);
                    }
                    newMatrix.setElement(i, j, value);
                }
            }
        }
    }


    class Product2 extends Thread {
        private int i;

        public Product2(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            for (int j = 0; j < m2.getCountColumns(); j++) {
                int value = 0;
                for (int k = 0; k < m1.getCountColumns(); k++) {
                    value += m1.getElement(i, k) * m2.getElement(k, j);
                }
                newMatrix.setElement(i, j, value);
            }
        }
    }
}

