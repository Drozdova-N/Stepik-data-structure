package com.company.stepik.disjoint_sets;

import java.util.Scanner;


/**
 * Формат входа. Первая строка содержит числа n и m — число таблиц
 * и число запросов, соответственно. Вторая строка содержит n це-
 * лых чисел r 1 , . . . , r n — размеры таблиц. Каждая из последующих
 * m строк содержит два номера таблиц destination i и source i , кото-
 * рые необходимо объединить.
 * <p>
 * Формат выхода. Для каждого из m запросов выведите максималь-
 * ный размер таблицы после соответствующего объединения.
 */

public class MergingTables {

    private int[] tables;
    private int[] sizeTables;
    private int maxSizeTable = 0;

    public MergingTables(int size) {
        tables = new int[size + 1];
        sizeTables = new int[size + 1];
    }

    public void makeSet(int number, int sizeTable) {
        if (sizeTable > maxSizeTable) maxSizeTable = sizeTable;
        tables[number] = number;
        sizeTables[number] = sizeTable;
    }

//    public int find(int i) {
//        while (i != tables[i]) {
//            i = tables[i];
//        }
//        return i;
//    }


    public int find(int i) {

        if (i != tables[i])
            tables[i] = find(tables[i]);
        return tables[i];
    }

    public void union(int var1, int var2) {
        int idVar1 = find(var1);
        int idVar2 = find(var2);
        if (idVar1 == idVar2) return;
        if (sizeTables[idVar1] > sizeTables[idVar2]) {
            tables[idVar2] = idVar1;
            increaseSize(idVar1, idVar2);
        } else {
            tables[idVar1] = idVar2;
            increaseSize(idVar2, idVar1);
        }
    }

    public void increaseSize(int idVar1, int idVar2) {
        sizeTables[idVar1] += sizeTables[idVar2];
        if (sizeTables[idVar1] > maxSizeTable) maxSizeTable = sizeTables[idVar1];
    }

    public int getMaxSize() {
        return this.maxSizeTable;
    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int countUnion = in.nextInt();
        StringBuilder result = new StringBuilder();
        MergingTables merging = new MergingTables(size);

        for (int i = 1; i <= size; i++) {
            merging.makeSet(i, in.nextInt());
        }

        for (int i = 0; i < countUnion; i++) {
            merging.union(in.nextInt(), in.nextInt());
            result.append(merging.getMaxSize());
            result.append("\n");

        }
        System.out.println(result);

    }
}
