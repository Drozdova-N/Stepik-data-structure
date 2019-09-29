package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
 * Вычислить высоту данного дерева.
 * Вход. Корневое дерево с вершинами {0, . . . , n−1}, заданное
 * как последовательность parent 0 , . . . , parent n−1 , где parent i —
 * родитель i-й вершины.
 * Выход. Высота дерева.
 */

public class Tree {

    private int size;
    private Node[] listNode;
    private int root;

    public Tree(int size) {
        this.size = size;
        listNode = new Node[size];
    }

    public int getRoot() {
        return this.root;
    }

    public void addNode(int valueNode, int parent) {
        if (parent == -1) {
            root = valueNode;
            return;
        }
        if (listNode[parent] == null) {
            listNode[parent] = new Node(parent);
        }
        listNode[parent].addChild(new Node(valueNode));
    }

    public void printTree(int r) {
        System.out.println("Node " + r);
        if (listNode[r] == null) return;
        for (Node n : listNode[r].listChild) {
            printTree(n.value);
        }

    }

    public int getHeight(int r) {
        int height = 1;

        if (listNode[r] == null) return 0;

        for (Node n : listNode[r].listChild) {

            height = Integer.max(height, getHeight(n.value) + 1);
        }
        return height;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        Tree tree = new Tree(size);
        for (int i = 0; i < size; i++) {
            tree.addNode(i, in.nextInt());
        }
        tree.printTree(tree.getRoot());
        System.out.println((tree.getHeight(tree.getRoot()) + 1));
    }

    class Node {
        private int value;
        private List<Node> listChild = new LinkedList();

        public Node(int value) {
            this.value = value;
        }

        public void addChild(Node node) {
            listChild.add(node);
        }
    }

}