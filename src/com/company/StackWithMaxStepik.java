package com.company;


import java.util.Scanner;
import java.util.Stack;

/**
 * Стек с поддержкой максимума
 * Реализовать стек с поддержкой операций push, pop и max.
 * Вход. Последовательность запросов push, pop и max .
 * Выход. Для каждого запроса max вывести максимальное
 * число, находящее на стеке
 */
public class StackWithMaxStepik {
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> stackMaximum = new Stack<>();

    public void push(int value) {
        if (stack.isEmpty()) {
            stack.push(value);
            stackMaximum.push(value);
        } else {
            stack.push(value);
            if (stack.peek() > stackMaximum.peek()) {
                stackMaximum.push(stack.peek());
            } else stackMaximum.push(stackMaximum.peek());
        }
    }

    public int pop() {
        stackMaximum.pop();
        return stack.pop();
    }

    public int max() {
        return stackMaximum.peek();
    }

    public static void main(String[] args) {

        StackWithMaxStepik stack = new StackWithMaxStepik();
        Scanner in = new Scanner(System.in);
        StringBuilder result = new StringBuilder();
        int count = in.nextInt();
        in.nextLine();
        for (int i = 0; i < count; i++) {
            String operation = in.nextLine();
            if (operation.length() > 3) {
                StringBuilder number = new StringBuilder();
                int value = 0;
                for (int j = 5; j < operation.length(); j++) {
                    number.append(operation.charAt(j));
                }
                if (number.length() > 0)
                {
                    value = Integer.valueOf(number.toString());
                }
                stack.push(value);

            } else if ("pop".equals(operation)) {
                stack.pop();
            } else if ("max".equals(operation)) {
               result.append(stack.max()+" ");
            }

        }

        System.out.println(result.toString());
    }

}
