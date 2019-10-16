package com.company.stepik.disjoint_sets;

import java.util.Scanner;

/**
 * Формат входа. Первая строка содержит числа n, e, d. Каждая из сле-
 * дующих e строк содержит два числа i и j и задаёт равенство
 * x i = x j . Каждая из следующих d строк содержит два числа i и j и
 * задаёт неравенство x i 6 = x j . Переменные индексируются с 1:
 * x 1 , . . . , x n .
 * Формат выхода. Выведите 1, если переменным x 1 , . . . , x n можно
 * присвоить целые значения, чтобы все равенства и неравенства
 * выполнились. В противном случае выведите 0.
 */
public class ProgramAnalysis {

    private int[] parent;
    private int[] rank;
    private final int size;

    public ProgramAnalysis(int size) {
        this.size = size + 1;
        parent = new int[this.size];
        rank = new int[this.size];
    }

    public void makeSet(int value) {
        parent[value] = value;
    }

    public void union(int var1, int var2) {
        int idVar1 = find(var1);
        int idVar2 = find(var2);
        if (idVar1 == idVar2) return;
        if (rank[idVar1] > rank[idVar2])
            parent[idVar2] = idVar1;
        else {
            parent[idVar1] = idVar2;

            if (rank[idVar1] == rank[idVar2])
                rank[idVar2]++;
        }
    }

    public int find(int i) {

        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        int e = in.nextInt(); // equality
        int d = in.nextInt();  // inequality

        ProgramAnalysis analysis = new ProgramAnalysis(count);
        for (int i = 1; i <= count; i++) analysis.makeSet(i);

        for (int i = 0; i < e; i++) analysis.union(in.nextInt(), in.nextInt());

        for (int i = 0; i < d; i++)
            if (analysis.find(in.nextInt()) == analysis.find(in.nextInt())) {
                System.out.println(0);
                return;
            }

        System.out.println(1);
    }
}
