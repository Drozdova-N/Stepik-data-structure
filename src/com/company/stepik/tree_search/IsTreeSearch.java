package com.company.stepik.tree_search;
import java.util.Scanner;
public class IsTreeSearch {

    private Node[] tree;
    private int size;
    private int iterator = 0;
    private long min = Long.MIN_VALUE;
    boolean result = true;

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
        System.out.println(tree.isTreeSearch() ? "CORRECT" : "INCORRECT");
    }
}
