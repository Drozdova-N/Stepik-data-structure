package com.company;

public class MyInt {
    private int value;

    public MyInt() {
        value = 0;
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }

    public void add(int n) {
        this.value += n;
    }

    public void substract(int n) {
        this.value -= n;
    }

    public String toString() {
        return "" + value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int v) {
        this.value = v;
    }

    public static void main(String[] args) {
        MyInt myInt = new MyInt();
        myInt.add(1000);
        System.out.println(myInt.toString());
    }
}
