package com.company.stepik.tree_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

 class AVLTree {
    private Node headNode;
    private long sum = 0;
    private final int CONST_MOD = 1_000_000_001;



    public void insert(int i) {
        headNode = insert(headNode, func(i));
   System.out.println("insert i = " + func(i));
    }

    public void delete(int i) {
        headNode = delete(headNode, func(i));
     System.out.println("delete i = " + func(i));
    }

    public long sum(int l, int r) {
        long sum = 0;

        if (headNode == null)
            return sum;
        l = func(l);
        r = func(r);
      System.out.format("l=%d  r=%d\n", l, r);
        Node[] nodesSplitL = split(headNode, l); // nodesSplitL[0] -> min.... l-1; nodesSplitLх[1] -> l .... max
        Node[] nodesSplitR = split(nodesSplitL[1], r + 1); // nodesSplitR[0] -> l .....r-1; nodesSplitR[1] -> r .... max
        //  sum = orderForSum(nodesSplitR[0]);
        sum = nodesSplitR[0]==null?0:nodesSplitR[0].getSum();
        merge(nodesSplitL[0], nodesSplitR[0]);
        merge(headNode, nodesSplitR[1]);
        return sum;
    }

    private long orderForSum(Node node) {
        return (node == null) ? 0 : node.key + orderForSum(node.getLeftChild()) + orderForSum(node.getRightChild());
    }

    public void setSum(long sum) {
        this.sum = sum;
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

    }

    private Node insert(Node node, int i) {
        if (node == null) {
            return new Node(i);
        }
        if (i < node.key) {
            node.setLeftChild(insert(node.getLeftChild(), i));
        } else if (i > node.key) node.setRightChild(insert(node.getRightChild(), i));
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        node.setSum(node.key + sumNode(node.getLeftChild()) + sumNode(node.getRightChild()));
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
        node.setSum(node.key + sumNode(node.getLeftChild()) + sumNode(node.getRightChild()));
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
        System.out.println("key: " + node.key + " height: " + node.getHeight() + " balance: " + getBalanceFactor(node) + " sum =" + node.getSum());
        if (node.getRightChild() != null) in_order(node.getRightChild());
    }

    private int height(Node node) {
        if (node == null) return 0;
        return node.getHeight();
    }

    private long sumNode(Node node) {
        if (node == null) return 0;
        return node.getSum();
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
        node.setSum(node.key + sumNode(node.getLeftChild()) + sumNode(node.getRightChild()));
        temp.setHeight(Math.max(height(temp.getLeftChild()), height(temp.getRightChild())) + 1);
        temp.setSum(temp.key + sumNode(temp.getLeftChild()) + sumNode(temp.getRightChild()));
        return temp;

    }

    private Node smallRightRotation(Node node) {
        Node temp = node.leftChild;
        node.setLeftChild(temp.getRightChild());
        temp.setRightChild(node);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        node.setSum(node.key + sumNode(node.getLeftChild()) + sumNode(node.getRightChild()));
        temp.setHeight(Math.max(height(temp.getLeftChild()), height(temp.getRightChild())) + 1);
        temp.setSum(temp.key + sumNode(temp.getLeftChild()) + sumNode(temp.getRightChild()));
        return temp;
    }

    private Node bigRightRotation(Node node) {
        node.setLeftChild(smallLeftRotation(node.getLeftChild()));
        return smallRightRotation(node);
    }

    private Node bigLeftRotation(Node node) {
        node.setRightChild(smallRightRotation(node.getRightChild()));
        return smallLeftRotation(node);
    }


    private Node merge(Node treeLeft, Node treeRight) {
        if (treeLeft == null) return headNode = treeRight;
        if (treeRight == null) return headNode = treeLeft;
        Node maxNodeInLeft = getMax(treeLeft);
        treeLeft = delete(treeLeft, maxNodeInLeft.key);

        return headNode = mergeWithRoot(treeLeft, treeRight, maxNodeInLeft);
    }

    private Node mergeWithRoot(Node treeLeft, Node treeRight, Node root) {
        if (Math.abs(height(treeLeft) - height(treeRight)) <= 1) {
            root.setLeftChild(treeLeft);
            root.setRightChild(treeRight);
            root.setHeight(Math.max(height(treeLeft), height(treeRight)) + 1);
            root.setSum(root.key+sumNode(treeLeft)+ sumNode(treeRight));
            return root;
        } else if (height(treeLeft) > height(treeRight)) {
            Node temp = mergeWithRoot(treeLeft.getRightChild(), treeRight, root);
            treeLeft.setRightChild(temp);
            treeLeft.setSum(treeLeft.key+sumNode(treeLeft.getLeftChild())+sumNode(treeLeft.getRightChild()));
            return rebalance(treeLeft);

        } else {
            Node temp = mergeWithRoot(treeLeft, treeRight.getLeftChild(), root);
            treeRight.setLeftChild(temp);
            treeRight.setSum(treeRight.key+sumNode(treeRight.getLeftChild())+sumNode(treeRight.getRightChild()));
            return rebalance(treeRight);

        }
    }

    private Node[] split(Node node, int key) {
        if (node == null) return new Node[]{null, null};
        if (key <= node.key) {
            Node[] nodes = split(node.getLeftChild(), key);
            nodes[1] = mergeWithRoot(nodes[1], node.getRightChild(), node);
            return nodes;
        } else {
            Node[] nodes = split(node.getRightChild(), key);
            nodes[0] = mergeWithRoot(node.getLeftChild(), nodes[0], node);
            return nodes;
        }
    }

    public static void main(String[] args) throws IOException {
        AVLTree tree1 = new AVLTree();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(in.readLine());
        String[] value;
        for (int i = 0; i < count; i++) {
            value = in.readLine().split(" ");
            String operation = value[0];
            switch (operation) {
                case "?":
                    System.out.println(tree1.find(Integer.parseInt(value[1])));
                    break;
                case "+":
                    tree1.insert(Integer.parseInt(value[1]));
                    break;
                case "-":
                    tree1.delete(Integer.parseInt(value[1]));
                    break;
                case "s":
                    long s = tree1.sum(Integer.parseInt(value[1]), Integer.parseInt(value[2]));
                    tree1.setSum(s);
                    System.out.println("sum = :"+s);
                    break;
                default:
                    throw new RuntimeException(operation);
            }
            System.out.println("______");
            tree1.printTree();
            System.out.println("______");


        }
    }

    class Node {
        private int key;
        private Node leftChild;
        private Node rightChild;
        private long sum;
        private int height;


        private Node(int key) {
            this.key = key;
            this.height = 1;
            this.sum = key;
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

        private int getKey() {
            return key;
        }

        private void setKey(int key) {
            this.key = key;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }
    }
}

