package ru.ob11to.functionstyle.streamapi.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CollectorsMapExample {
    public static void main(String[] args) {
        List<Integer> numbersExampleCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

//        toMap(numbersExampleCollection);

        partitioningByWithCollector(numbersExampleCollection);
        partitioningByMethod(numbersExampleCollection);

    }

    private static void partitioningByMethod(List<Integer> numbersExampleCollection) {
        Map<Boolean, List<Integer>> parts = numbersExampleCollection.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0
                ));

        System.out.println("Чётные");
        parts.getOrDefault(true, new ArrayList<>()).forEach(System.out::println);
        System.out.println("Нечётные");
        parts.getOrDefault(false, new ArrayList<>()).forEach(System.out::println);
    }

    private static void partitioningByWithCollector(List<Integer> numbersExampleCollection) {
        Map<Boolean, String> parts = numbersExampleCollection.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.mapping(
                                String::valueOf,
                                Collectors.joining(";")
                        )
                ));

        String evens = parts.get(true);   // "2;4;6;8;10"
        String odds = parts.get(false);  // "1;3;5;7;9"

        System.out.println("Чётные:   " + evens);
        System.out.println("Нечётные: " + odds);
    }

    private static void toMap(List<Integer> numbersExampleCollection) {
        Function<Integer, String> keyMapper = number -> (number % 2 == 0) ? "even" : "odd";
        Function<Integer, List<Integer>> valueMapper = number -> new ArrayList<>() {{
            add(number);
        }};
        BinaryOperator<List<Integer>> mergeFunction = (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
        Supplier<Map<String, List<Integer>>> mapFactory = HashMap::new;

        Map<String, List<Integer>> map = numbersExampleCollection.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapFactory));

        map.forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
