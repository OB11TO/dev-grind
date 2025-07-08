package ru.ob11to.functionstyle.functions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Socks {
    public static void main(String[] args) {
        List<String> socks = List.of("red", "green", "blue", "red", "black", "black", "black");
         socks.stream()
                .collect(new SocksPair())
                 .entrySet().stream()
                 .filter(e -> e.getValue() % 2 != 0)
                 .forEach(e -> System.out.println(e.getKey()));

         socks.stream()
                 .collect(Collectors.groupingBy(obj -> obj, Collectors.counting()))
                 .forEach((key, value) -> System.out.println(key + " " + value));

        Map<Boolean, List<String>> collect = socks.stream()
                .collect(Collectors.partitioningBy(a -> a.length() % 2 != 0));

    }

    private static final class SocksPair implements Collector<String, Map<String, Integer>, Map<String, Integer>> {

        @Override
        public Supplier<Map<String, Integer>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<String, Integer>, String> accumulator() {
            return (map, key) -> map.put(key, map.getOrDefault(key, 0) + 1);
        }

        @Override
        public BinaryOperator<Map<String, Integer>> combiner() {
            return (firstMap, secondMap) -> {
                Map<String, Integer> result = new HashMap<>();
                result.putAll(firstMap);
                result.putAll(secondMap);
                return result;
            };
        }

        @Override
        public Function<Map<String, Integer>, Map<String, Integer>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
        }
    }
}
