package ru.ob11to.functionstyle.functions;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

public class ArrayRunner {
    public static void main(String[] args) {
        List<String> list = List.of("a", "b", "c", "d", "e", "f", "g", "h");
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
