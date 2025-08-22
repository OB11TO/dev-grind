package ru.ob11to.functionstyle.streamapi.collect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.teeing;

public class CollectorsGPT {
    public static void main(String[] args) {
        //example1();
        //example2();
        example3();
        //example4();
        //example5();
    }

    /**
     * Сгруппировать сотрудников по департаментам.<p>
     * Для каждого департамента посчитать сумму зарплат.
     * <p>
     * Сгруппировать сотрудников по департаменту.<p>
     * В каждом департаменте найти сотрудника с максимальной зарплатой.
     */
    private static void example5() {
        record Employee(String name, String department, int salary) {
        }
        List<Employee> employees = List.of(
                new Employee("Alice", "IT", 1000),
                new Employee("Bob", "HR", 1500),
                new Employee("Charlie", "IT", 2000),
                new Employee("Diana", "Finance", 3000),
                new Employee("Eve", "Finance", 2500)
        );

        Map<String, Integer> result = employees.stream()
                .collect(groupingBy(employee -> employee.department,
                        Collectors.summingInt(employee -> employee.salary)));

        result.forEach((key, value) -> System.out.println(key + ": " + value));

        Map<String, Optional<Employee>> collect = employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        maxBy(Comparator.comparing(Employee::salary))));

        collect.entrySet().forEach(System.out::println);


    }

    /**
     * Убрать дубликаты.<p>
     * Собрать в одну строку, разделив запятой.<p>
     */
    private static void example4() {
        List<String> words = List.of("apple", "banana", "pear", "banana", "apple", "cherry");

        String result = words.stream()
                .distinct()
                .collect(joining(", "));

        System.out.println(result);
    }


    /**
     * Нужно сгруппировать людей по городу, а для каждого города получить:<p>
     * средний возраст людей;<p>
     * список имён, объединённых в одну строку через запятую (например, "Alice, Bob, Diana").<p>
     * {@code London -> {avgAge=31.0, names="Alice, Bob, Diana"}}<p>
     * {@code Paris  -> {avgAge=30.0, names="Charlie, Eve"}}<p>
     * {@code Rome   -> {avgAge=50.0, names="Frank"}}<p>
     */
    private static void example3() {
        record Person(String name, String city, int age) {
        }
        List<Person> people = List.of(
                new Person("Alice", "London", 23),
                new Person("Bob", "London", 30),
                new Person("Charlie", "Paris", 25),
                new Person("Diana", "London", 40),
                new Person("Eve", "Paris", 35),
                new Person("Frank", "Rome", 50)
        );

        Map<String, Map<String, ? extends Serializable>> collect = people.stream()
                .collect(groupingBy(
                        Person::city,
                        teeing(
                                averagingInt(Person::age),
                                mapping(Person::name, joining(", ")),
                                (avgAge, names) -> Map.of("avgAge", avgAge, "names", names)
                        )));

        Map<String, Map<String, ? extends Serializable>> result = people.stream()
                .collect(groupingBy(
                        Person::city,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    String personInCity = list.stream().map(Person::name).collect(joining(", "));
                                    Double averageAge = list.stream().collect(averagingInt(Person::age));
                                    return Map.of("avgAge", averageAge, "names", personInCity);
                                }
                        )
                ));
        result.forEach((key, value) -> System.out.println(key + ": " + value));

        collect.forEach((k, v) -> System.out.println(k + " " + v));


    }

    public static record Person(String name, String city, int age) {
    }

    // Накопитель для одного города — собираем имена в List, сумму возрастов и количество
    static class Acc {
        final List<String> names = new ArrayList<>();
        long sum = 0L;
        long count = 0L;

        void add(Person p) {
            names.add(p.name());
            sum += p.age();
            count++;
        }

        Acc combine(Acc other) {
            // объединяем два аккумулятора (вызовется при параллельном выполнении)
            this.names.addAll(other.names);
            this.sum += other.sum;
            this.count += other.count;
            return this;
        }
    }

    // Коллектор: в один проход собирает список имён и статистику по возрасту
    static Collector<Person, Acc, Map<String, ? extends Serializable>> cityStatsOnePassCollector() {
        return Collector.of(
                Acc::new, // supplier: создаём новый аккумулятор
                Acc::add, // accumulator: добавляем одного Person
                (a, b) -> a.combine(b), // combiner: как объединить два аккумулятора
                acc -> { // finisher: формируем финальный Map для одной группы
                    double avg = acc.count == 0 ? 0.0 : ((double) acc.sum) / acc.count;
                    String namesJoined = String.join(", ", acc.names);
                    return Map.of("avgAge", avg, "names", namesJoined);
                },
                Collector.Characteristics.UNORDERED // можно указать UNORDERED (не влияет на корректность здесь)
        );
    }

    /**
     * Нужно:<p>
     * Сгруппировать людей по городу.<p>
     * Для каждого города сгруппировать людей по признаку: "молодые" (age < 30) и "взрослые" (age ≥ 30).<p>
     * В каждой такой подгруппе посчитать количество людей.<p>
     */
    private static void example2() {
        record Person(String name, String city, int age) {
        }
        List<Person> people = List.of(
                new Person("Alice", "London", 23),
                new Person("Bob", "London", 30),
                new Person("Charlie", "Paris", 25),
                new Person("Diana", "London", 40),
                new Person("Eve", "Paris", 35),
                new Person("Frank", "Rome", 50)
        );

        Map<String, Map<String, Long>> collect = people.stream()
                .collect(groupingBy(Person::city,
                        groupingBy(k -> k.age >= 30 ? "взрослые" : "молодые", counting())));

        collect.forEach((key, value) -> System.out.println(key + ": " + value));

    }


    /**
     * Нужно:<p>
     * Посчитать частоту встречаемости каждого слова
     * {@code (Map<String, Long>)}.<p>
     * Найти слово, которое встречается чаще всего.
     */
    private static void example1() {
        List<String> words = List.of("apple", "banana", "pear", "banana", "apple", "cherry", "banana");

        Map<String, Long> result = words.stream()
                .collect(groupingBy(Function.identity(), counting()));

        result.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println);

        result.entrySet().forEach(System.out::println);
    }
}
