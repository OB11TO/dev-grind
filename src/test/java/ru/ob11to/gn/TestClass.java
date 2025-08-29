package ru.ob11to.gn;

import java.util.ArrayList;
import java.util.List;

public class TestClass {

    public static void main(String[] args) {
        testPECS();
        testPECS1();
    }

    private static void testPECS1() {
        List<? extends Animal > producer = new ArrayList<>();
        List<? super Animal > consumer = new ArrayList<>();

    }

    private static void testPECS() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Dog());
        animals.add(new Puppy());

        animals.forEach(System.out::println);

        Animal animal = animals.get(0);
        Dog d = (Dog) animals.get(1);
        Animal animal2 = animals.get(2);

        System.out.println(animal + " " + d + " " + animal2);

    }
}

class Animal {}
class Dog extends Animal {}
class Puppy extends Dog {}

