package ru.ob11to.functionstyle.streamapi.collect;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class ReduceVsCollect {
    public static void main(String[] args) {
        List<Goods> list = List.of(
                new Goods("Apple", 50),
                new Goods("Orange", 70),
                new Goods("Pear", 65),
                new Goods("Cherry", 75)
        );

        BiFunction<Integer, Goods, Integer> biFunc = (a, b) -> {
            System.out.println("Accumulator: " + a + " " + b + "result: " + (a + b.getPrice()));
            return a + b.getPrice();
        };

        BinaryOperator<Integer> biOp = (a, b) -> {
            System.out.println("Combiner: a = " + a + ", b = " + b);
            return a + b;
        };

        Integer totalSum = list.stream()
                .parallel()
                .reduce(0, biFunc, biOp);

        System.out.println(totalSum);

        System.out.println("REDUCE");

        BiFunction<HashSet<Integer>, Goods, HashSet<Integer>> tiFunc = (a, b) -> {
            System.out.println(Thread.currentThread().getName() + " Accumulator: " + a + " " + b);
            a.add(b.getPrice());
            return a;
        };

        BinaryOperator<HashSet<Integer>> tiOp = (a, b) -> {
            System.out.println(Thread.currentThread().getName() + " Combiner: a = " + a + ", b = " + b);
            a.addAll(b);
            return a;
        };

        HashSet<Integer> test = list.stream()
                .parallel()
                .reduce(new HashSet<>(), tiFunc, tiOp);

        test.forEach(System.out::println);


        System.out.println("COLLECT");

        HashSet<Integer> result = list.parallelStream()
                .collect(HashSet::new,
                        (set, goods) -> {
                            System.out.println(Thread.currentThread().getName() + " collect add: " + goods);
                            set.add(goods.getPrice());
                        },
                        (left, right) -> {
                            System.out.println(Thread.currentThread().getName() + " collect combiner: left=" + left + ", right=" + right);
                            left.addAll(right);
                        });

        result.forEach(System.out::println);

    }
}

class Goods {
    private final String name;
    private final int price;

    public Goods(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Goods [name=" + name + ", price=" + price + "]";
    }
}