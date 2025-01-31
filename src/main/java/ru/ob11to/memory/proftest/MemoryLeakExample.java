package ru.ob11to.memory.proftest;

import java.util.ArrayList;
import java.util.List;

public class MemoryLeakExample {
    private static final List<byte[]> memoryLeakList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Запускаем утечку памяти...");
        while (true) {
            memoryLeakList.add(new byte[1_000_000]); // 1 MB данных
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
