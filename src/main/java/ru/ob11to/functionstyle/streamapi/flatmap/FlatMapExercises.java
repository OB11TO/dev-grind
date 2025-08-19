package ru.ob11to.functionstyle.streamapi.flatmap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlatMapExercises {

    // Задача 1: сумма всех чисел из List<List<Integer>> с помощью flatMap
    public static int sumAll(List<List<Integer>> lists) {
        return lists.stream()                      // Stream<List<Integer>>
                .flatMap(Collection::stream)  // Stream<Integer> (схлопнули вложенные списки)
                .mapToInt(Integer::intValue)  // IntStream
                .sum();                       // сумма всех чисел
    }

    // Задача 2: из List<String> sentences получить список уникальных слов (split + flatMap + distinct)
    public static List<String> uniqueWords(List<String> sentences) {
        return sentences.stream()
                .flatMap(s -> Arrays.stream(s.split("\\W+"))) // разбиваем каждую строку на слова -> Stream<String>
                .map(String::toLowerCase)                    // нормализуем к нижнему регистру
                .filter(w -> !w.isBlank())                  // убираем пустые строки
                .distinct()                                 // оставляем только уникальные
                .collect(Collectors.toList());              // в List<String>
    }

    // Пример простого класса User для задачи 3
    public static class User {
        private final String name;
        public User(String name) { this.name = name; }
        public String getName() { return name; }
        @Override
        public String toString() { return "User(" + name + ")"; }
    }

    // Задача 3: из List<Optional<User>> получить List<User> (используем flatMap(Optional::stream))
    public static List<User> presentUsers(List<Optional<User>> maybeUsers) {
        return maybeUsers.stream()
                .flatMap(Optional::stream)            // Optional::stream превращает Optional<T> -> 0 или 1 элемент в потоке
                .collect(Collectors.toList());        // собираем найденных пользователей
    }

    // main с демонстрацией
    public static void main(String[] args) {
        // --- Задача 1 demo ---
        List<List<Integer>> lists = List.of(
                List.of(1, 2, 3),
                List.of(10, 20),
                List.of(5)
        );
        System.out.println("Sum all -> " + sumAll(lists)); // ожидается 1+2+3+10+20+5 = 41

        // --- Задача 2 demo ---
        List<String> sentences = List.of(
                "Hello, world!",
                "Hello Java; Streams ARE cool.",
                "Streams and flatMap - hello world"
        );
        List<String> uniq = uniqueWords(sentences);
        System.out.println("Unique words -> " + uniq);

        // --- Задача 3 demo ---
        List<Optional<User>> maybeUsers = List.of(
                Optional.of(new User("Alice")),
                Optional.empty(),
                Optional.of(new User("Bob")),
                Optional.empty(),
                Optional.of(new User("Charlie"))
        );
        List<User> users = presentUsers(maybeUsers);
        System.out.println("Present users -> " + users);
    }
}

