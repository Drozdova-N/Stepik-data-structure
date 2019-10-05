package com.company;


import java.util.Arrays;
import java.util.Scanner;

/**
 * Параллельная обработка
 * По данным n процессорам и m задач определите, для каждой из задач,
 * каким процессором она будет обработана.
 * Вход. Число процессоров n и последовательность чисел
 * t 0 , . . . , t m−1 , где t i — время, необходимое на обработку i-й
 * задачи.
 * Выход. Для каждой задачи определите, какой процессор
 * и в какое время начнёт её обрабатывать, предполагая, что
 * каждая задача поступает на обработку первому освободив-
 * шемуся процессору
 */

public class ParallelProcessingStepik {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int countCPU = in.nextInt();
        PriorityQueue queue = new PriorityQueue(countCPU);
        for (int i = 0; i < countCPU; i++) {
            queue.insert(new CPU());
        }

        int countTask = in.nextInt();

        for (int i = 0; i < countTask; i++) {
            queue.progressingTask(in.nextLong());
        }

        System.out.println(queue.getResult());


    }


    static class PriorityQueue {
        int maxSize;
        int currentSize = 0;
        CPU[] cpus;

        StringBuilder result = new StringBuilder();

        public PriorityQueue(int maxSize) {
            this.maxSize = maxSize;
            cpus = new CPU[maxSize];

        }

        void progressingTask(long value) {
            result.append(cpus[0].getId() + " " + cpus[0].getTime());
            result.append("\n");
            cpus[0].increaseTime(value);
            siftDown(0);
        }


        String getResult() {
            return result.toString();
        }

        void insert(CPU cpu) {
            cpus[currentSize] = cpu;
            siftUp(currentSize);
            currentSize++;
        }

        int getParent(int i) {
            return (i - 1) / 2;
        }

        int getLeftChild(int i) {
            return 2 * i + 1;
        }

        int getRightChild(int i) {
            return 2 * i + 2;
        }

        void swap(int i, int j) {
            CPU temp = cpus[i];
            cpus[i] = cpus[j];
            cpus[j] = temp;
        }


        void siftUp(int i) {
            if (i == 0 || cpus[getParent(i)].getTime() < cpus[i].getTime()) return;
            if (cpus[getParent(i)].getTime() == cpus[i].getTime()) {
                if (cpus[i].getId() < cpus[getParent(i)].getId())
                    swap(i, getParent(i));
            } else
                swap(i, getParent(i));

            siftUp(getParent(i));
        }

        void siftDown(int i) {
            if (i == cpus.length - 1) return;
            int minIndex = i;
            int leftIndex = getLeftChild(i);
            int rightIndex = getRightChild(i);

            if (leftIndex < cpus.length && cpus[leftIndex].getTime() < cpus[minIndex].getTime())
                minIndex = leftIndex;

            if (leftIndex < cpus.length && cpus[leftIndex].getTime() == cpus[minIndex].getTime()) {
                minIndex = cpus[leftIndex].getId() < cpus[minIndex].getId() ? leftIndex : minIndex;
            }

            if (rightIndex < cpus.length && cpus[rightIndex].getTime() < cpus[minIndex].getTime())
                minIndex = rightIndex;

            if (rightIndex < cpus.length && cpus[rightIndex].getTime() == cpus[minIndex].getTime()) {
                minIndex = cpus[rightIndex].getId() < cpus[minIndex].getId() ? rightIndex : minIndex;
            }

            if (i != minIndex) {
                swap(i, minIndex);
                siftDown(minIndex);
            }
        }
    }


}

class CPU {
    private static int index = 0;
    private final int id;
    private long time = 0;

    public CPU() {
        this.id = index++;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public void increaseTime(long value) {
        this.time += value;
    }

    @Override
    public String toString() {
        return "CPU{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}