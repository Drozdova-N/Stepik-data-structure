package com.company.stepik.tree_search;


import java.util.Scanner;

public class AVLTree {
    private Node headNode;
    private long sum = 0;
    private final int CONST_MOD = 1_000_000_001;

    private int l;
    private int r;

    public void insert(int i) {
        headNode = insert(headNode, func(i));
        System.out.println("insert i = " +func(i));
    }

    public void delete(int i) {
        headNode = delete(headNode, func(i));
        System.out.println("delete i = " +func(i));
    }

    public long sum(int l, int r) {
        if (headNode == null) {
            return  sum= 0;
        }
        this.l = func(l);
        this.r = func(r);
        System.out.format("l=%d  r=%d\n", this.l, this.r);
        sum = 0;
        orderForSum(headNode);

        return sum;
    }

    private void orderForSum(Node node) {
        if (node.getLeftChild() != null) orderForSum(node.getLeftChild());
        if (node.key >= l && node.key <= r) {
            sum += node.key;
        }
        if (node.getRightChild() != null) orderForSum(node.getRightChild());
    }

    private int func(int x) {
        return (x % CONST_MOD + ((int) (sum % CONST_MOD))) % CONST_MOD;
    }

    public String find(int i) {
        int i_func = func(i);
        Node temp = headNode;
        while (temp != null) {
            if (i_func == temp.key) return "Found";
            if (i_func < temp.key) {
                temp = temp.leftChild;
            } else {
                temp = temp.rightChild;
            }
        }
        return "Not found";
    }

    public void printTree() {
        if (headNode != null)
            in_order(headNode);
        else System.out.println("end");
    }

    private Node insert(Node node, int i) {
        if (node == null) {
            return new Node(i);
        }
        if (i < node.key) {
            node.setLeftChild(insert(node.getLeftChild(), i));

        } else if (i > node.key) node.setRightChild(insert(node.getRightChild(), i));

        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return rebalance(node);
    }

    private Node delete(Node node, int i) {
        if (node == null) return null;
        if (i < node.key) node.setLeftChild(delete(node.getLeftChild(), i));
        else if (i > node.key) node.setRightChild(delete(node.getRightChild(), i));
        else {
            if (node.getLeftChild() == null && node.getRightChild() == null) return null;
            if (node.getLeftChild() == null) return node.getRightChild();
            if (node.getRightChild() == null) return node.getLeftChild();

            Node temp = getMin(node.getRightChild());
            int tempKey = node.key;
            node.key = temp.key;
            temp.key = tempKey;
            node.setRightChild(delete(node.getRightChild(), i));

        }

        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return rebalance(node);

    }

    private Node getMin(Node node) {
        Node temp = node;
        Node min = null;
        while (temp != null) {
            min = temp;
            temp = temp.leftChild;
        }
        return min;
    }

    private Node getMax(Node node) {
        Node temp = node;
        Node max = null;
        while (temp != null) {
            max = temp;
            temp = temp.rightChild;
        }
        return max;
    }

    private void in_order(Node node) {
        if (node.getLeftChild() != null) in_order(node.getLeftChild());
        System.out.println("key: " + node.key + " height: " + node.getHeight() + " balance: " + getBalanceFactor(node));
        if (node.getRightChild() != null) in_order(node.getRightChild());
    }

    private int height(Node node) {
        if (node == null) return 0;
        return node.getHeight();
    }

    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        return height(node.getLeftChild()) - height(node.getRightChild());
    }

    private Node rebalance(Node node) {
        int balance = getBalanceFactor(node);
        if (balance == -2) {
            if (getBalanceFactor(node.getRightChild()) > 0) return bigLeftRotation(node);
            else return smallLeftRotation(node);
        }
        if (balance == 2) {
            if (getBalanceFactor(node.getLeftChild()) < 0) return bigRightRotation(node);
            else return smallRightRotation(node);
        }
        return node;
    }

    private Node smallLeftRotation(Node node) {
        Node temp = node.getRightChild();
        node.setRightChild(temp.getLeftChild());
        temp.setLeftChild(node);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        temp.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return temp;

    }

    private Node smallRightRotation(Node node) {
        Node temp = node.leftChild;
        node.setLeftChild(temp.getRightChild());
        temp.setRightChild(node);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        temp.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return temp;
    }

    private Node bigRightRotation(Node node) {
        smallLeftRotation(node.getLeftChild());
        return smallRightRotation(node);
    }

    private Node bigLeftRotation(Node node) {
        smallRightRotation(node.getRightChild());
        return smallLeftRotation(node);
    }

    private Node merge(Node treeLeft, Node treeRight) {
        if (treeLeft == null) return treeRight;
        if (treeRight == null) return treeLeft;
        Node maxNodeInLeft = getMax(treeLeft);
        delete(maxNodeInLeft.key);
        headNode = maxNodeInLeft;
        return mergeWithRoot(treeLeft, treeRight, maxNodeInLeft);


    }

    private Node mergeWithRoot(Node treeLeft, Node treeRight, Node root) {
        if (Math.abs(height(treeLeft) - height(treeRight)) <= 1) {
            root.setLeftChild(treeLeft);
            root.setRightChild(treeRight);
            root.setHeight(Math.max(height(treeLeft), height(treeRight)) + 1);
            return root;
        } else if (height(treeLeft) > height(treeRight)) {
            Node temp = mergeWithRoot(treeLeft.getRightChild(), treeRight, root);
            treeLeft.setRightChild(temp);
            return rebalance(treeLeft);

        } else {
            Node temp = mergeWithRoot(treeLeft, treeRight.getLeftChild(), root);
            treeRight.setLeftChild(temp);
            return rebalance(treeRight);

        }
    }

    private Node[] split(Node node, int key) {
        if (node == null) return new Node[]{null, null};
        if (key < node.key) {
            Node[] nodes = split(node.getLeftChild(), key);
            nodes[1] = mergeWithRoot(nodes[1], node.getRightChild(), node);
            return nodes;
        } else {
            Node[] nodes = split(node.getRightChild(), key);
            nodes[0] = mergeWithRoot(node.getLeftChild(), nodes[0], node);
            return nodes;
        }
    }

    public static void main(String[] args) {
        AVLTree tree1 = new AVLTree();
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        in.nextLine();
        for (int i = 0; i < count; i++) {
            String request = in.nextLine();
            String[] value = request.split(" ", 2);
            if (value[0].equals("?")) {
                System.out.println(tree1.find(Integer.parseInt(value[1])));
            } else if (value[0].equals("+")) {
                tree1.insert(Integer.parseInt(value[1]));
            } else if (value[0].equals("-")) {
                tree1.delete(Integer.parseInt(value[1]));
            } else {
                String[] interval = value[1].split(" ", 2);
                System.out.println(tree1.sum(Integer.parseInt(interval[0]), Integer.parseInt(interval[1])));
            }

            tree1.printTree();
            System.out.println("_____________");
        }
    }

    class Node {
        private int key;
        private Node leftChild;
        private Node rightChild;

        private int height;


        private Node(int key) {
            this.key = key;
            this.height = 1;
        }

        private void setLeftChild(Node node) {
            this.leftChild = node;
        }

        private void setRightChild(Node node) {
            this.rightChild = node;
        }

        private int getHeight() {
            return height;
        }

        private void setHeight(int height) {
            this.height = height;
        }

        private Node getLeftChild() {
            return leftChild;
        }

        private Node getRightChild() {
            return rightChild;
        }


    }
}


/*
88
+ 1
+ 2
+ 3
+ 4
+ 5
+ 6
+ 7
+ 7
+ 8
+ 9
+ 10
+ 11
+ 12
+ 13
+ 14
+ 15
+ 16
+ 17
+ 18
+ 19
+ 20
+ 21
+ 22
+ 23
+ 24
+ 25
+ 26
+ 27
+ 28
+ 29
+ 30
+ 31
s 19 19
s 100 100
s 1 100
s 100000 100000
s 18 18
s 100 100
- 18
- 19
- 20
- 15
- 16
- 17
- 21
- 22
- 23
- 16
- 17
- 21
- 22
- 23
- 24
- 15
- 16
- 17
- 21
- 22
- 15
- 16
- 17
- 21
- 22
- 23
- 10
- 11
- 12
- 13
- 14
- 24
- 25
- 26
- 6
- 7
- 7
- 8
- 9
- 27
- 28
- 29
- 2
- 3
- 4
- 5
- 30
- 31
- 1
 */