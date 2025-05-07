package ru.ob11to.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListInsertBenchmark {

    private static final int INSERTIONS = 100_000;
    private static final int ELEMENTS_COUNT = 40_000;
    private static final int ACCESS_COUNT = 40_000;

    public static void main(String[] args) {
//        benchmarkListInsertions(new ArrayList<>(), "ArrayList");
//        benchmarkListInsertions(new LinkedList<>(), "LinkedList");

        benchmarkListAccess(new ArrayList<>(), "ArrayList");
        benchmarkListAccess(new LinkedList<>(), "LinkedList");
    }

    private static void benchmarkListInsertions(List<Integer> list, String listType) {
        // Заполнение списка начальными элементами
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            list.add(i);
        }

        // Измерение времени добавления в конец
        long startEnd = System.nanoTime();
        for (int i = 0; i < INSERTIONS; i++) {
            list.add(ELEMENTS_COUNT + i);
        }
        long endEnd = System.nanoTime();
        long durationEnd = endEnd - startEnd;

        // Измерение времени добавления в середину
        long startMiddle = System.nanoTime();
        for (int i = 0; i < INSERTIONS; i++) {
            list.add(list.size() / 2, -i);
        }
        long endMiddle = System.nanoTime();
        long durationMiddle = endMiddle - startMiddle;

        System.out.println("Результаты для " + listType + ":");
        System.out.println("Добавление в конец: " + durationEnd / 1_000_000 + " мс");
        System.out.println("Добавление в середину: " + durationMiddle / 1_000_000 + " мс");
        System.out.println();
    }

    private static void benchmarkListAccess(List<Integer> list, String listType) {
        // Заполнение списка начальными элементами
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            list.add(i);
        }

        int firstIndex = 0;
        int middleIndex = ELEMENTS_COUNT / 2;
        int lastIndex = ELEMENTS_COUNT - 1;
        int dummy = 0; // Переменная для предотвращения оптимизации

        // Измерение времени доступа к элементам по индексу
        long startIndexAccess = System.nanoTime();
        for (int i = 0; i < ACCESS_COUNT; i++) {
            dummy += list.get(firstIndex);
            dummy += list.get(middleIndex);
            dummy += list.get(lastIndex);
        }
        long endIndexAccess = System.nanoTime();
        long durationIndexAccess = endIndexAccess - startIndexAccess;

        // Измерение времени поиска элементов по значению
        long startValueSearch = System.nanoTime();
        for (int i = 0; i < ACCESS_COUNT; i++) {
            dummy += list.indexOf(firstIndex);
            dummy += list.indexOf(middleIndex);
            dummy += list.indexOf(lastIndex);
        }
        long endValueSearch = System.nanoTime();
        long durationValueSearch = endValueSearch - startValueSearch;

        System.out.println("Результаты для " + listType + ":");
        System.out.println("Доступ по индексу: " + durationIndexAccess / 1_000_000 + " мс");
        System.out.println("Поиск по значению: " + durationValueSearch / 1_000_000 + " мс");
        System.out.println("Сумма dummy: " + dummy); // Выводим, чтобы избежать оптимизации
        System.out.println();
    }
}
