package ru.ob11to.benchmark;

import java.util.HashMap;

public class BinaryTest {
    public static void main(String[] args) {
        int a = 14;
        int b = 10;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("a", "1");
        stringStringHashMap.put("a", "2");

        stringStringHashMap.put("b", "3");
        System.out.println();
    }
}
