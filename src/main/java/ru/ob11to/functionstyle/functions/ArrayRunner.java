package ru.ob11to.functionstyle.functions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class ArrayRunner {
    public static void main(String[] args) {
        List<String> list = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i",  "j", "k", "l", "m", "n");
        String[] array = list.toArray(String[]::new);
        String[] array1 = list.toArray(new String[0]);
        String[] array2 = list.toArray(new ArrayIntFunction());
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));

        HardArray[] array3 = list.stream()
                .map(HardArray::new)
                .toArray(new HardArrayIntFunction());

        HardArray[] array4 = list.stream()
                .map(HardArray::new)
                .toArray(HardArray[]::new);

        System.out.println(Arrays.toString(array3));
        System.out.println(Arrays.toString(array4));

        String[] words = {"hi", "ok"};
        Stream<String> flatResult = Arrays.stream(words)
                .flatMap(w -> Arrays.stream(w.split("")));

        Map<String, List<String>> rockSongs = Map.of(
                "Queen", List.of("Bohemian Rhapsody", "Don't Stop Me Now"),
                "Nirvana", List.of("Smells Like Teen Spirit", "Come as You Are"),
                "AC/DC", List.of("Back in Black", "Highway to Hell"),
                "Pearl Jam", List.of("Alive", "Even Flow")
        );

        List<String> firstThree = rockSongs.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .flatMap(e -> e.getValue().stream())
                .limit(3)
                .toList();

        firstThree.forEach(System.out::println);
    }

    static class HardArray {

        private final String value;

        public HardArray(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

    static class HardArrayIntFunction implements IntFunction<HardArray[]> {

        @Override
        public HardArray[] apply(int value) {
            return new HardArray[value];
        }
    }

    static class ArrayIntFunction implements IntFunction<String[]> {
        @Override
        public String[] apply(int value) {
            return new String[value];
        }
    }
}
