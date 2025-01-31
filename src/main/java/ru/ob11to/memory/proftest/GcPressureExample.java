package ru.ob11to.memory.proftest;

import java.util.ArrayList;
import java.util.List;

public class GcPressureExample {
    public static void main(String[] args) {
        System.out.println("Создаем нагрузку на GC...");
        List<Integer> list = new ArrayList<>();
        while (true) {
            for (int i = 0; i < 100_000; i++) {
                list.add(i);
            }

        }
    }
}
