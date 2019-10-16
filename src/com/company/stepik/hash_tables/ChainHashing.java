package com.company.stepik.hash_tables;

import java.util.LinkedList;
import java.util.Scanner;

public class ChainHashing {
    private final int p = 1000000007;
    private final int X = 263;
    private static LinkedList[] arrayValue;
    private final int m; //размер хеш-таблицы
    private final long[] powerX;

    public ChainHashing(int size) {
        this.m = size;
        arrayValue = new LinkedList[m];

        powerX = new long[15];
        powerX[0] = 1;
        powerX[1] = X % p;
        for (int i = 2; i < powerX.length; i++) {
            powerX[i] = (((powerX[i - 1]) % p) * (X % p)) % p;

        }
    }

    public void add(String str) {
        int index = getHash(str);
        if (arrayValue[index] == null)
            arrayValue[index] = new LinkedList();

        if (find(str).equals("yes")) return;
        arrayValue[index].add(str);

    }

    public void del(String str) {
        int index = getHash(str);
        if (arrayValue[index] == null) return;
        if (arrayValue[index].isEmpty()) return;
        for (int i = 0; i < arrayValue[index].size(); i++) {
            if (arrayValue[index].get(i).equals(str)) {
                arrayValue[index].remove(i);
            }
        }
    }

    public String find(String str) {
        int index = getHash(str);
        if (arrayValue[index] == null || arrayValue[index].isEmpty()) return "no";
        for (int i = 0; i < arrayValue[index].size(); i++) {
            if (arrayValue[index].get(i).equals(str))
                return "yes";
        }
        return "no";
    }


    public String check(int index) {
        StringBuilder res = new StringBuilder();
        if (arrayValue[index] == null || arrayValue[index].isEmpty()) return "";
        for (int i = arrayValue[index].size() - 1; i >= 0; i--) {
            res.append(arrayValue[index].get(i) + " ");
        }
        return res.toString();
    }


    private int getHash(String s) {
        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = ((((s.charAt(i) % p) * powerX[i]) % p) + result % p) % p;

        }
        return (int) result % m;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        ChainHashing chainHashing = new ChainHashing(m);
        int count = in.nextInt();
        in.nextLine();
        String operation;
        String[] value;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            operation = in.nextLine();
            value = operation.split(" ", 2);
            switch (value[0]) {
                case ("add"):
                    chainHashing.add(value[1]);
                    break;
                case ("del"):
                    chainHashing.del(value[1]);
                    break;
                case ("find"):
                    result.append(chainHashing.find(value[1]));
                    result.append("\n");
                    break;
                case ("check"):
                    result.append(chainHashing.check(Integer.parseInt(value[1])));
                    result.append("\n");
                    break;
                default:
                    break;
            }

        }
        System.out.println(result.toString());


    }
}
