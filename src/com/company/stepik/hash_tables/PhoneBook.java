package com.company.stepik.hash_tables;

import java.util.Scanner;

/**
 * Телефонная книга
 * Реализовать структуру данных, эффективно обрабатывающую запро-
 * сы вида add number name, del number и find number.
 *    Вход. Последовательность запросов вида add number
 * name, del number и find number, где number — те-
 * лефонный номер, содержащий не более семи знаков,
 * а name — короткая строка.
 *   Выход. Для каждого запроса find number выведите соот-
 * ветствующее имя или сообщите, что такой записи нет.
 */
public class PhoneBook {

//    private int size;
//    private String[] arrayPhone;
//
//    public PhoneBook() {
//        arrayPhone = new String[10000000];
//
//    }
//
//    public void put(Integer key, String value) {
//        arrayPhone[key] = value;
//        size++;
//    }
//
//    public boolean del(int key) {
//        if (arrayPhone[key] == null) return false;
//        arrayPhone[key] = null;
//        size--;
//        return true;
//    }
//
//
//    public String find(int key) {
//        if (arrayPhone[key] != null)
//            return arrayPhone[key];
//        else return "not found";
//    }


    /**прямая адресация
     */

    private int size;
    private Node[] arrayPhone;
    private int M = 100_000;

    public PhoneBook() {
        arrayPhone = new Node[M];
    }

    public void put(Integer key, String value) {
        int i = 0;
        Node temp = arrayPhone[hash(key, i)];
        while (temp != null) {
            if (temp.key == -1) break;
            if (temp.key == key) {
                temp.value = value;
                break;
            }
            temp = arrayPhone[hash(key, ++i)];
        }
        arrayPhone[hash(key, i)] = new Node(key, value);
        size++;
    }

    public void del(int key) {
        int i = 0;
        Node temp = arrayPhone[hash(key, i)];
        while (temp != null && temp.key != key) {
            temp = arrayPhone[hash(key, ++i)];
        }
        if (temp == null) return;
        if (temp.key == key) temp.key = -1;

    }


    public String find(int key) {
        int i = 0;
        Node temp = arrayPhone[hash(key, i)];
        while (temp != null) {
            if (temp.key == key) break;
            temp = arrayPhone[hash(key, ++i)];
        }
        if (temp == null) return "not found";
        else return temp.value;
    }

    private int hash0(int k) {
        return k % M;
    }

    private int hash(int k, int i) {
        return (hash0(k) + i) % M;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int countOperation = in.nextInt();
        StringBuilder result = new StringBuilder();
        String operation;
        String[] value;
        PhoneBook book = new PhoneBook();
        in.nextLine();
        for (int i = 0; i < countOperation; i++) {
            operation = in.nextLine();
            value = operation.split(" ", 3);

            if (value[0].equals("add")) {
                book.put(Integer.parseInt(value[1]), value[2]);
            } else if (value[0].equals("del")) {
                book.del(Integer.parseInt(value[1]));
            } else {
                result.append(book.find(Integer.parseInt(value[1])));
                result.append("\n");
            }
        }
        System.out.println(result.toString());


    }

    private class Node {
        int key;
        String value;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }

    }


}
