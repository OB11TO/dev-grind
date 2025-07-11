package ru.ob11to.functionstyle.streamapi.collectors;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectorsRunner {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        extracted(numbers);
    }

    /** Соберите элементы потока целых чисел в две строки (одна для четных, вторая для нечетных чисел)
    в качестве разделителя используйте ";" */
    private static void extracted(List<Integer> numbers) {
        Predicate<Integer> charSequencePredicate = (i) -> i % 2 == 0;
        Map<Boolean, String> collect = numbers.stream()
                .collect(Collectors.partitioningBy(charSequencePredicate,
                                Collectors.mapping(
                                        String::valueOf,
                                        Collectors.joining(";")
                                )
                        )
                );
        collect.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
