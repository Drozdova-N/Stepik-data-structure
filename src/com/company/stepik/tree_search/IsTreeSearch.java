package com.company.stepik.tree_search;

import java.util.Scanner;

/**
 * 1_ Вход. Двоичное дерево.
 * Выход. Проверить, является ли оно корректным деревом
 * поиска: верно ли, что для любой вершины дерева её ключ
 * больше всех ключей в левом поддереве данной вершины и
 * меньше всех ключей в правом поддереве.
 * <p>
 * 2_ Дереву разрешается содержать
 * равные ключи, но они всегда должны находиться в правом поддереве.
 */

public class IsTreeSearch {

    private final Node[] tree;
    private final int size;
    private int iterator = 0;
    private long min = Long.MIN_VALUE;
    private long min_2 = Long.MIN_VALUE;
    boolean result = true;
    boolean result_2 = true;

    public IsTreeSearch(int size) {
        this.size = size;
        tree = new Node[this.size];

    }

    public void addNode(long key, int leftChild, int rightChild) {
        if (iterator < tree.length)
            tree[iterator++] = new Node(key, leftChild, rightChild);
    }

    public boolean isTreeSearch() {
        if (size == 0) return true;
        in_order(tree[0]);
        return result;
    }

    public boolean isTreeSearch_2() {
        if (size == 0) return true;
        in_order_2(tree[0]);
        return result_2;
    }

    private void in_order_2(Node node) {
        if (node.leftChild != -1) in_order_2(tree[node.leftChild]);
        if (min_2 > node.key) result_2 = false;
        if (min_2 == node.key && node.leftChild != -1) {
            if (tree[node.leftChild].key == node.key)
                result_2 = false;
        } else min_2 = node.key;
        if (node.rightChild != -1) in_order_2(tree[node.rightChild]);
    }

    private void in_order(Node node) {
        if (node.leftChild != -1) in_order(tree[node.leftChild]);
        if (min >= node.key) result = false;
        else min = node.key;
        if (node.rightChild != -1) in_order(tree[node.rightChild]);

    }

    class Node {
        private long key;
        private int leftChild;
        private int rightChild;

        public Node(long key, int leftChild, int rightChild) {
            this.key = key;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        IsTreeSearch tree = new IsTreeSearch(size);
        for (int i = 0; i < size; i++) {
            tree.addNode(in.nextLong(), in.nextInt(), in.nextInt());
        }
        System.out.println(tree.isTreeSearch_2() ? "CORRECT" : "INCORRECT");
    }
}
