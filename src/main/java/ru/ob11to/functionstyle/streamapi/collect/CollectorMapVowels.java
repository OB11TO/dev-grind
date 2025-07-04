package ru.ob11to.functionstyle.streamapi.collect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectorMapVowels {
    public static void main(String[] args) {
        CollectorMapVowelsMain collectorMapVowelsMain = new CollectorMapVowelsMain();
        List<String> words = List.of("Яма", "brr", "sky", "apple", "hello", "why", "GGGHH", "SKY", "sadadads", "SSSSSSSSSS");
        Map<String, Long> collect = words.stream().collect(collectorMapVowelsMain);
        System.out.println(collect);

    }

    private static final class CollectorMapVowelsMain implements Collector<String, Map<String, Long>, Map<String, Long>> {
        private static final Set<Character> VOWELS = Set.of(
                'a', 'e', 'i', 'o', 'u', 'y',
                'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я'
        );

        @Override
        public Supplier<Map<String, Long>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<String, Long>, String> accumulator() {
            return (map, word) -> {
                long count = word.toLowerCase().chars()
                        .mapToObj(c -> (char) c)
                        .filter(VOWELS::contains)
                        .count();
                if (count > 0) {
                    map.putIfAbsent(word.toLowerCase(), count);
                }
            };
        }

        @Override
        public BinaryOperator<Map<String, Long>> combiner() {
            return (map1, map2) -> {
                map2.forEach(map1::putIfAbsent);
                return map1;
            };
        }

        @Override
        public Function<Map<String, Long>, Map<String, Long>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
        }
    }
}
