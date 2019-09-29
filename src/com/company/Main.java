package com.company;

public class Main {

    public static void main(String[] args) {
        int countThreads = 4;
        for (int i = 100; i < 2000; i += 250) {
            Matrix matrix1 = new Matrix(i, i);
            Matrix matrix2 = new Matrix(i, i);
            System.out.printf("Matrix[%d][%d]\n",i,i);

            long start = System.currentTimeMillis();
            matrix1.product(matrix2);
            System.out.println("One Thread :  " + (System.currentTimeMillis() - start));

            ParallelMatrixProduct parallelMatrixProduct3 = new ParallelMatrixProduct(countThreads);
            ParallelMatrixProduct parallelMatrixProduct4 = new ParallelMatrixProduct(countThreads);
            try {
                long start2 = System.currentTimeMillis();
                parallelMatrixProduct3.product(matrix1, matrix2);
                System.out.println(countThreads + " Threads Array of threads: " + (System.currentTimeMillis() - start2));
                long start3 = System.currentTimeMillis();
                parallelMatrixProduct4.product2(matrix1, matrix2);
                System.out.println(countThreads + " Threads  Executors:  " + (System.currentTimeMillis() - start3));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("------------------------------------");
        }
    }


    static void quickSort(int[] mas, int left, int right) {
        int first = mas[left];
        int leftBorder = left;
        int rightBorder = right;

        while (left < right) {
            while (mas[left] <= first && left < rightBorder)
                left++;

            while (mas[right] >= first && right > leftBorder)
                right--;
            if (left < right) {
                int buf = mas[left];
                mas[left] = mas[right];
                mas[right] = buf;
            }
        }
        mas[leftBorder] = mas[right];
        mas[right] = first;

        if (leftBorder < right)
            quickSort(mas, leftBorder, right - 1);
        if (rightBorder > right)
            quickSort(mas, right + 1, rightBorder);
    }

    static void sortSelection(int[] mas) {
        for (int i = 0; i < mas.length; i++) {
            int min = i;
            for (int j = min + 1; j < mas.length; j++) {
                if (mas[j] < mas[min])
                    min = j;
            }
            int buf = mas[i];
            mas[i] = mas[min];
            mas[min] = buf;
        }
    }

    static int[] generationMas(int n) {
        int[] mas = new int[n];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 20);
        }
        System.out.println("Исходный массив: ");
        for (int e : mas) {
            System.out.print(e + "  ");
        }
        System.out.println();
        return mas;
    }

    static void print(int[] mas) {
        System.out.println("Отсортированый массив: ");
        for (int e : mas) {
            System.out.print(e + "  ");
        }
    }

    static void mergeSort(int[] mas) {
        if (mas.length > 1) {
            int m = mas.length / 2;
            int[] buf1 = new int[m];
            int[] buf2 = new int[mas.length - m];
            System.arraycopy(mas, 0, buf1, 0, m);
            System.arraycopy(mas, m, buf2, 0, mas.length - m);
            mergeSort(buf1);
            mergeSort(buf2);
            merge(mas, buf1, buf2);
        } else return;

    }

    static void merge(int[] a, int[] buf1, int[] buf2) {

        int positionA = 0;
        int positionB = 0;
        for (int i = 0; i < a.length; i++) {
            if (positionA < buf1.length && positionB < buf2.length) {
                if (buf1[positionA] <= buf2[positionB]) {
                    a[i] = buf1[positionA++];
                } else {
                    a[i] = buf2[positionB++];
                }

            } else if (positionA < buf1.length)
                a[i] = buf1[positionA++];
            else if (positionB < buf2.length)
                a[i] = buf2[positionB++];
        }
    }
}

