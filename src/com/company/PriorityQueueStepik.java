package com.company;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

/**
 * Построение кучи
 * Переставить элементы заданного массива чисел так, чтобы он удовле-
 * творял свойству мин-кучи.
 * Вход. Массив чисел A[0 . . . n − 1].
 * Выход. Переставить элементы массива так, чтобы выпол-
 * нялись неравенства A[i] ≤ A[2i + 1] и A[i] ≤ A[2i + 2] для
 * всех i
 */
public class PriorityQueueStepik {

    private static int[] H;
    private static int count = 0;
    private static List<Answer> answers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        H = new int[n];
        for (int i = 0; i < n; i++) {
            H[i] = in.nextInt();
        }
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
        System.out.println(count);

        for (Answer a : answers)
            System.out.println(a.toString());
    }

    public static void siftDown(int i) {
        if (i == H.length - 1) return;
        int minIndex = i;
        int leftChild = getLeftChild(i);
        if (leftChild < H.length && H[leftChild] < H[minIndex])
            minIndex = leftChild;

        int rightChild = getRightChild(i);
        if (rightChild < H.length && H[rightChild] < H[minIndex])
            minIndex = rightChild;

        if (minIndex != i) {
            swap(i, minIndex);
            siftDown(minIndex);
        }
    }


    public static void siftUp(int i) {

        while (i > 0 & H[i] < H[getParent(i)]) {
            swap(i, getParent(i));
            count++;
            System.out.println(i + "   " + getParent(i));
            i = getParent(i);
        }
    }


    public static int getParent(int i) {
        return (i - 1) / 2;
    }

    public static int getLeftChild(int i) {
        return 2 * i + 1;
    }

    public static int getRightChild(int i) {
        return 2 * i + 2;
    }

    public static void swap(int i, int j) {
        count++;
        answers.add(new Answer(i, j));
        int temp = H[i];
        H[i] = H[j];
        H[j] = temp;
    }


}

class Answer {
    private int i;
    private int j;

    public Answer(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return i + " " + j;
    }
}