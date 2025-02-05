package ru.ob11to.memory.task244;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(5);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);

        list.forEach(System.out::println);
    }
}
