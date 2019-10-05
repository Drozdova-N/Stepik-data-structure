package com.company;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


/**
*Найти максимум в каждом окне размера m данного массива чисел
* A[1 . . . n].
* Вход. Массив чисел A[1 . . . n] и число 1 ≤ m ≤ n.
* Выход. Максимум подмассива A[i . . . i + m − 1] для всех 1 ≤
* i ≤ n − m + 1.
*
*/
public class MaxInWindowStepik {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();

        Queue<Integer> queue = new ArrayDeque<>(size);
        QueueInteger queueInteger = new QueueInteger();

        for (int i = 0; i < size; i++) {
            queue.offer(in.nextInt());
        }
        int m = in.nextInt();
        int[] outPut = new int[size - m + 1];
        int count = 0;
        for (int i = 0; i < m; i++) {
            queueInteger.push(queue.poll());
        }
        outPut[count++] = queueInteger.getMax();

        while (!queue.isEmpty()) {
            queueInteger.pool();
            queueInteger.push(queue.poll());
            outPut[count++] = queueInteger.getMax();
        }
        for (int i = 0; i < outPut.length; i++) {
            System.out.print(outPut[i] + " ");
        }
    }
}

//2 7 3 1 5 2 6 2

class QueueInteger {
    int size = 0;
    private StackInteger stackInteger1 = new StackInteger();
    private StackInteger stackInteger2 = new StackInteger();

    public Integer pool() {
        if (stackInteger2.isEmpty()) {
            while (!stackInteger1.isEmpty()) {
                stackInteger2.push(stackInteger1.pop());
            }
        }
        size--;
        return stackInteger2.pop();
    }

    public void push(int value) {

        stackInteger1.push(value);
        size++;

    }

    public boolean isEmpty() {
        if (size == 0) return true;
        else return false;
    }

    public int getMax() {
        int m1 = 0, m2 = 0;
        if (stackInteger2.isEmpty()) {
            while (!stackInteger1.isEmpty()) {
                stackInteger2.push(stackInteger1.pop());
            }
            return stackInteger2.getMax();
        } else if (!stackInteger1.isEmpty()) {
            m1 = stackInteger1.getMax();
            m2 = stackInteger2.getMax();

        }
        if (m1 > m2) return m1;
        else return m2;
    }

    class StackInteger extends Stack<Integer> {
        private Stack<Integer> stack = new Stack<>();


        @Override
        public Integer push(Integer integer) {
            if (this.empty()) stack.push(integer);
            else if (stack.peek() < integer) stack.push(integer);
            else stack.push(stack.peek());
            return super.push(integer);

        }

        public int getMax() {
            return stack.peek();
        }

        @Override
        public synchronized Integer pop() {
            stack.pop();
            return super.pop();
        }

        @Override
        public synchronized Integer peek() {
            return super.peek();
        }

        @Override
        public boolean empty() {
            return super.empty();
        }
    }


}