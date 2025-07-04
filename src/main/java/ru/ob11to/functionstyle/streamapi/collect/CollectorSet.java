package ru.ob11to.functionstyle.streamapi.collect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectorSet {

    public static void main(String[] args) {
        List<Integer> numericList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        CollectorSetMain<Integer> collector = new CollectorSetMain<>();
        numericList.stream().collect(collector)
                .forEach(System.out::println);

    }

    private static final class CollectorSetMain<T> implements Collector<T, Set<T>, Set<T>> {

        @Override
        public Supplier<Set<T>> supplier() {
            return HashSet::new;
        }

        @Override
        public BiConsumer<Set<T>, T> accumulator() {
            return Set::add;
        }

        @Override
        public BinaryOperator<Set<T>> combiner() {
            return (set1, set2) -> {
                Set<T> combinerSet = new HashSet<>();
                combinerSet.addAll(set1);
                combinerSet.addAll(set2);
                return combinerSet;
            };
        }

        @Override
        public Function<Set<T>, Set<T>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
        }
    }
}
