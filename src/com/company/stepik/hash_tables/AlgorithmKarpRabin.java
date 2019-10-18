package com.company.stepik.hash_tables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Поиск образца в тексте
 * Найти все вхождения строки Pattern в строку Text.
 * Вход. Строки Pattern и Text.
 * Выход. Все индексы i строки Text, начиная с которых стро-
 * ка Pattern входит в Text:
 * Text[i..i + |Pattern| − 1] = Pattern
 */

public class AlgorithmKarpRabin {

    private final StringBuilder text;
    private final StringBuilder pattern;
    private StringBuilder result;
    private final long[] powerX; // массив степеней
    private final int p = 1000000007;
    private final int X = 263;
    private final int hashPattern;
    private final long[] hashText;

    public AlgorithmKarpRabin(StringBuilder pattern, StringBuilder text) {
        this.pattern = pattern;
        this.text = text;
        result = new StringBuilder("");
        powerX = new long[pattern.length()];
        this.getPowerX(); // расчет степеней x
        hashPattern = this.hash(pattern.toString());
        hashText = new long[text.length() - pattern.length() + 1];
        this.getHashText();
    }

    public String getResult() {
        System.out.print("");
        for (int i = 0; i < hashText.length; i++) {
            if (hashText[i] == hashPattern) {
                if (text.substring(i, i + pattern.length()).equals(pattern.toString()))
                    System.out.print(i + " ");
            }
        }
        return result.toString();
    }

    private void getPowerX() {
        powerX[0] = 1;
        if (powerX.length > 1) {
            powerX[1] = X;
            for (int i = 2; i < powerX.length; i++) {
                powerX[i] = (powerX[i - 1] * this.X) % p;
            }
        }
    }

    private int hash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (int) ((s.charAt(i) * powerX[i]) % p + hash) % p;
        }
        return hash;
    }

    private void getHashText() {
        int sizeArray = hashText.length;
        int sizePattern = pattern.length();
        int sizeText = text.length();
        hashText[sizeArray - 1] = this.hash(this.text.substring(sizeText - sizePattern, sizeText));
        for (int i = sizeArray - 2; i >= 0; i--) {
            int asciiCharINew = text.charAt(i);
            int asciiCharIOld = text.charAt(i + sizePattern);
            hashText[i] = ((((((hashText[i + 1] % p - (asciiCharIOld * powerX[sizePattern - 1]) % p)) * this.X) % p
                    + asciiCharINew * powerX[0] % p) % p) + p) % p;

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder pattern = new StringBuilder(in.readLine());
        StringBuilder text = new StringBuilder(in.readLine());
        AlgorithmKarpRabin searchPattern = new AlgorithmKarpRabin(pattern, text);
        System.out.println(searchPattern.getResult());
    }
}
