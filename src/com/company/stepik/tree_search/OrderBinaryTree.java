package com.company.stepik.tree_search;
import java.util.Scanner;

/**
 *Обход двоичного дерева
 * Построить in-order, pre-order и post-order обходы данного двоичного де-
 * рева.
 * Вход. Двоичное дерево.
 * Выход. Все его вершины в трёх разных порядках: in-order,
 * pre-order и post-order.
 */
public class OrderBinaryTree {
    private Node[] tree;
    private int iterator = 0;
    public OrderBinaryTree(int size) {
        tree = new Node[size];
    }

    public void addNode(int key, int childLeft, int childRight) {
        if (iterator < tree.length)
        tree[iterator++] = new Node(key, childLeft, childRight);
    }
    public void in_order(Node node) {
        if (node.childLeft != -1) in_order(tree[node.childLeft]);
        printKey(node.key);
        if (node.childRight != -1) in_order(tree[node.childRight]);
    }

    public void pre_order(Node node) {
        printKey(node.key);
        if (node.childLeft != -1) pre_order(tree[node.childLeft]);
        if (node.childRight != -1) pre_order(tree[node.childRight]);
    }

    public void post_order(Node node) {
        if (node.childLeft != -1) post_order(tree[node.childLeft]);
        if (node.childRight != -1) post_order(tree[node.childRight]);
        printKey(node.key);
    }


    public void printKey(int key) {
        System.out.print(key+" ");
    }


    public Node getRoot() {
        return tree[0];
    }

    public static void main(String[] args)  {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        OrderBinaryTree binaryTree = new OrderBinaryTree(size);
        for (int i = 0; i < size; i++) {
            binaryTree.addNode(in.nextInt(), in.nextInt(), in.nextInt());
        }
        OrderBinaryTree.Node root = binaryTree.getRoot();
        binaryTree.in_order(root);
        System.out.println();
        binaryTree.pre_order(root);
        System.out.println();
        binaryTree.post_order(root);
    }

    class Node {
        private int key;
        private int childLeft;
        private int childRight;

        public Node(int key, int childLeft, int childRight) {
            this.key = key;
            this.childLeft = childLeft;
            this.childRight = childRight;
        }
    }
}
