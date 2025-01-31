package ru.ob11to.memory.proftest;

import java.util.Random;

public class CpuProfilingExample {
    public static void main(String[] args) {
        System.out.println("Запускаем CPU-нагрузку...");
        while (true) {
            performHeavyCalculation();
        }
    }

    private static void performHeavyCalculation() {
        Random random = new Random();
        double result = 0;
        for (int i = 0; i < 100_000; i++) {
            result += Math.sqrt(random.nextDouble()) * Math.pow(random.nextDouble(), 2);
        }
    }
}
