package com.company.stepik.tree_search;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Цель в данной задаче — реализовать структуру данных Rope.
 * Данная структура данных хранит строку и позволяет эффективно вы-
 * резать кусок строки и переставить его в другое место.
 *
 * Формат входа. Первая строка содержит исходную строку S, вто-
 * рая — число запросов q. Каждая из последующих q строк задаёт
 * запрос тройкой чисел i, j, k и означает следующее: вырезать под-
 * строку S[i..j] (где i и j индексируются с нуля) и вставить её после
 * k-го символа оставшейся строки (где k индексируется с едини-
 * цы), при этом если k = 0, то вставить вырезанный кусок надо в
 * начало.
 * Формат выхода. Выведите полученную (после всех q запросов) стро-
 * ку.
 *
 * @param <Key>
 * @param <Value>
 */
public class SplayTreeRope<Key extends Comparable<Key>, Value> {

   private Node root;

    public  void put(Key key, Value value){
      if(root == null) {
          root = new Node(key,value);
          return;
      }
      root = splay(root, key);
      int comp = key.compareTo(root.key);
      if(comp<0){
          Node node = new Node(key,value);
          node.leftChild = root.leftChild;
          node.rightChild = root;
          root.leftChild = null;
          root= node;
      }
      else  if(comp>0){
          Node node = new Node(key,value);
          node.rightChild = root.rightChild;
          node.leftChild = root;
          root.rightChild = null;
          root = node;
      }
      else root.value =value;
   }

    public void remove(Key key){
       if(root == null) return;
       root = splay(root, key);
       int comp = key.compareTo(root.key);
       if(comp == 0){
           if(root.leftChild == null) root = root.rightChild;
           else {
               Node temp = root.rightChild;
               root= root.leftChild;
               splay(root, key);
               root.rightChild = temp;
           }
       }
    }

    @Nullable
    public Value get(Key key){
        root = splay(root, key);
        int comp = key.compareTo(root.key);
        if(comp == 0) return  root.value;
         else  return  null;
    }

    public void  swap(Key i, Key j, Key k){

    }

    private Node splay(Node node, Key key){
       if(node == null) return  null;
       int comp = key.compareTo(node.key);
       if(comp<0){
           if(node.leftChild == null) return  node;
           int comp2 = key.compareTo(node.leftChild.key);
             if(comp2<0) {
                   node.leftChild.leftChild = splay(node.leftChild.leftChild, key);
                   node = rightRotation(node);
             }
             else if(comp2>0){
                 node.leftChild.rightChild = splay(node.leftChild.rightChild, key);
                 if(node.leftChild.rightChild != null) node.leftChild = leftRotation(node.leftChild);
             }
             if(node.leftChild == null) return node;
               else  return rightRotation(node);
       }
       else  if(comp>0){
           if(node.rightChild == null) return  node;
           int comp2 = key.compareTo(node.rightChild.key);
           if(comp2 > 0 ){
               node.rightChild.rightChild = splay(node.rightChild.rightChild, key);
               node = leftRotation(node);
           }
            else  if(comp2 < 0){
                node.rightChild.leftChild = splay(node.rightChild.leftChild,key);
                if(node.rightChild.leftChild != null)  node.rightChild = rightRotation(node.rightChild);
           }
       if(node.rightChild == null) return node;
         else  return leftRotation(node);

       }
       else  return  node;
    }

    public void printTree(){
        if(root!=null) inOrder(root);
    }

    private  Node rightRotation(Node node){
     Node temp = node.leftChild;
     node.leftChild = temp.rightChild;
     temp.rightChild = node;
     return temp;
    }

    private Node leftRotation(Node node){
      Node temp = node.rightChild;
      node.rightChild =temp.leftChild;
      temp.leftChild = node;
      return  temp;
    }

    private void inOrder(Node node){
        if (node.leftChild != null) inOrder(node.leftChild);
        System.out.println("key: " +node.key+" value: "+node.value);
        if (node.rightChild != null) inOrder(node.rightChild);
    }


   private SplitTree splitLeft(Key i){
       SplitTree result = new SplitTree();
       Node temp =  splay(root, i);
       if(temp!= null) {
           result.leftTree.root=temp;
           result.rightTree.root = temp.rightChild;
           temp.rightChild = null;
       }
       return  result;
   }

   private SplitTree splitRight(Key i){
        SplitTree result = new SplitTree();
        Node temp =  splay(root, i);
        if(temp!= null) {
            result.leftTree.root = temp.leftChild;
            result.rightTree.root = temp;
            temp.leftChild = null;
        }
        return  result;
    }

   private void merge(SplayTreeRope<Key, Value> treeLeft, SplayTreeRope<Key, Value> treeRight){
        Node temp = treeLeft.getMax();
        treeLeft.splay(treeLeft.root, temp.key);
        treeLeft.root.rightChild = treeRight.root;
        this.root = treeLeft.root;
    }

   private Node getRoot(){
       return this.root;
   }

   private void setRoot(Node node){
       this.root = node;
   }

   private Node getMax(){
       Node temp = root;
       Node max = null;
       while (temp != null) {
           max = temp;
           temp = temp.rightChild;
       }
       return max;
   }

   private Node getMin(){
       Node temp = root;
       Node min = null;
       while (temp != null) {
           min = temp;
           temp = temp.leftChild;
       }
       return min;
   }

    private class Node {
        private Key key;
        private Value value;
        private Node leftChild;
        private Node rightChild;
        private  int height;

        private Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private class SplitTree{
       private SplayTreeRope<Key, Value> leftTree;
       private SplayTreeRope<Key, Value> rightTree;

        private SplitTree() {
            leftTree = new SplayTreeRope<>();
            rightTree = new SplayTreeRope<>();
        }
    }

    public static void main(String[] args) throws  IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();
        int count = Integer.parseInt(reader.readLine());
       // int i, j, k;
        SplayTreeRope<Integer,Character> treeRope = new SplayTreeRope<>();

        for(int iter = 0; iter<str.length(); iter++){
             treeRope.put(iter,str.charAt(iter));
             if(iter%5 == 0) treeRope.splay(treeRope.root, treeRope.getMax().key);
        }
        treeRope.printTree();

//        for(int iter = 0 ; iter<count; iter++){
//            i = Integer.parseInt(reader.readLine());
//            j = Integer.parseInt(reader.readLine());
//            k = Integer.parseInt(reader.readLine());
//            treeRope.swap(i, j, k);
//        }
//        System.out.println("after swap");
//        treeRope.printTree();
    }
}
