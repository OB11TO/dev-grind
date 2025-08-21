package ru.ob11to.functionstyle.streamapi.collect;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CaseCollectorsGPT {

    public static void main(String[] args) {
        //listOrderTask();
        //listOrderTask2();
        //listOrderTask3();
        listOrderTask4();
    }

    /**
     * Собрать {@code Map<String, CategoryStats>}, где: <p>
     */
    private static void listOrderTask4() {
        record Order(String id, double amount) {}
        record Customer(String name, List<Order> orders) {}
        record Product(String name, String category, List<Customer> customers) {}

        record CategoryStats(
                double totalRevenue,         // сумма всех заказов в категории
                Set<String> uniqueCustomers, // уникальные имена клиентов в категории
                Optional<String> topCustomer // имя клиента с наибольшим суммарным чеком в категории (если есть)
        ) {}

        List<Product> products = List.of(
                new Product("Laptop", "Electronics", List.of(
                        new Customer("Alice", List.of(new Order("o1", 1200.0), new Order("o2", 1500.0))),
                        new Customer("Bob",   List.of(new Order("o3", 800.0)))
                )),
                new Product("Phone", "Electronics", List.of(
                        new Customer("Charlie", List.of(new Order("o4", 700.0))),
                        new Customer("Alice",   List.of(new Order("o5", 900.0)))
                )),
                new Product("Shirt", "Clothes", List.of(
                        new Customer("Diana", List.of(new Order("o6", 50.0))),
                        new Customer("Eve",   List.of(new Order("o7", 30.0)))
                ))
        );

        Map<String, CategoryStats> result = products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                productList -> {

                                    List<Customer> customers = productList.stream()
                                            .flatMap(product -> product.customers.stream())
                                            .toList();

                                    double totalRevenue = customers.stream()
                                            .flatMap(customer -> customer.orders.stream())
                                            .mapToDouble(order -> order.amount)
                                            .sum();

                                    Set<String> uniqueCustomers = customers.stream()
                                            .map(Customer::name)
                                            .collect(Collectors.toSet());

                                    Optional<String> topCustomer = customers.stream()
                                            .max(Comparator.comparing(
                                                    customer -> customer.orders.stream()
                                                            .mapToDouble(Order::amount)
                                                            .sum()))
                                            .map(Customer::name);


                                    Map<String, Double> sumByCustomer = customers.stream()
                                            .collect(Collectors.groupingBy(
                                                    Customer::name,
                                                    Collectors.summingDouble(
                                                            customer -> customer.orders.stream()
                                                                    .mapToDouble(Order::amount)
                                                                    .sum()
                                                    )
                                            ));

                                    Optional<String> topCustomer2 = sumByCustomer.entrySet().stream()
                                            .max(Comparator.comparing(Map.Entry::getValue))
                                            .map(Map.Entry::getKey);


                                    return new CategoryStats(totalRevenue, uniqueCustomers, topCustomer);
                                }
                        )
                ));

        result.entrySet().forEach(System.out::println);
    }

    /**
     * Для каждого департамента нужно получить {@code Map<String, Map<String, Object>> }, где: <p>
     * ключ {@code "totalRevenue"} → сумма всех заказов всех клиентов всех курсов этого департамента <p>
     * ключ {@code "uniqueCustomers"} → список уникальных имён клиентов, которые покупали хотя бы один заказ <p>
     * <p>
     * {@code   {
     * "IT" = {
     * "totalRevenue" = 600.0,
     * "uniqueCustomers" = ["Alice", "Bob", "Charlie"]
     * },
     * "Finance" = {
     * "totalRevenue" = 550.0,
     * "uniqueCustomers" = ["Diana", "Eve"]
     * }
     * } }
     */
    private static void listOrderTask3() {
        record Order(String id, double amount) {
        }
        record Customer(String name, List<Order> orders) {
        }
        record Course(String name, List<Customer> customers) {
        }
        record Department(String name, List<Course> courses) {
        }


        List<Department> departments = List.of(
                new Department("IT", List.of(
                        new Course("Java", List.of(
                                new Customer("Alice", List.of(new Order("o1", 120.0), new Order("o2", 80.0))),
                                new Customer("Bob", List.of(new Order("o3", 50.0)))
                        )),
                        new Course("Python", List.of(
                                new Customer("Charlie", List.of(new Order("o4", 200.0))),
                                new Customer("Alice", List.of(new Order("o5", 150.0)))
                        ))
                )),
                new Department("Finance", List.of(
                        new Course("Accounting", List.of(
                                new Customer("Diana", List.of(new Order("o6", 300.0))),
                                new Customer("Eve", List.of(new Order("o7", 250.0)))
                        ))
                ))
        );

        Map<String, Map<String, Object>> resultCollectingAndThen = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::name,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    Department department = list.get(0);
                                    double totalRevenue = department.courses.stream()
                                            .flatMap(course -> course.customers.stream())
                                            .flatMap(customer -> customer.orders.stream())
                                            .mapToDouble(Order::amount)
                                            .sum();

                                    Set<String> uniqueCustomers = department.courses.stream()
                                            .flatMap(course -> course.customers.stream())
                                            .map(Customer::name)
                                            .collect(Collectors.toSet());

                                    return Map.of(
                                            "totalRevenue", totalRevenue,
                                            "uniqueCustomers", uniqueCustomers);
                                }
                        )
                ));

        resultCollectingAndThen.entrySet().forEach(System.out::println);

        Map<String, Map<String, Object>> gg = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::name,
                        Collectors.collectingAndThen(
                                Collectors.toList(),   // список всех департаментов с одинаковым именем
                                list -> {
                                    // объединяем все курсы всех департаментов
                                    List<Course> allCourses = list.stream()
                                            .flatMap(d -> d.courses().stream())
                                            .toList();

                                    double totalRevenue = allCourses.stream()
                                            .flatMap(c -> c.customers().stream())
                                            .flatMap(cust -> cust.orders().stream())
                                            .mapToDouble(Order::amount)
                                            .sum();

                                    Set<String> uniqueCustomers = allCourses.stream()
                                            .flatMap(c -> c.customers().stream())
                                            .map(Customer::name)
                                            .collect(Collectors.toSet());

                                    return Map.of(
                                            "totalRevenue", totalRevenue,
                                            "uniqueCustomers", uniqueCustomers
                                    );
                                }
                        )
                ));

        gg.entrySet().forEach(System.out::println);

        Map<String, Map<String, Object>> resultWithTeeing = departments.stream()
                .collect(Collectors.toMap(
                        Department::name,
                        dept -> dept.courses().stream()
                                .flatMap(course -> course.customers().stream())
                                .collect(Collectors.teeing(
                                        // 1-й агрегат: totalRevenue
                                        Collectors.summingDouble(c -> c.orders().stream().mapToDouble(Order::amount).sum()),
                                        // 2-й агрегат: уникальные имена клиентов
                                        Collectors.mapping(Customer::name, Collectors.toSet()),
                                        // комбинируем в Map<String,Object>
                                        (total, names) -> Map.of(
                                                "totalRevenue", total,
                                                "uniqueCustomers", names
                                        )
                                ))
                ));
        resultWithTeeing.entrySet().forEach(System.out::println);
    }

    /**
     * Собрать {@code Map<String, List<String>>}, где: <p>
     * ключ — название курса {@code (Course.name)}<p>
     * значение — список уникальных имён клиентов, которые купили курс
     */
    private static void listOrderTask2() {
        record Order(String id, double amount) {
        }
        record Customer(String name, List<Order> orders) {
        }
        record Course(String name, List<Customer> customers) {
        }

        List<Course> courses = List.of(
                new Course("Java", List.of(
                        new Customer("Alice", List.of(new Order("o1", 120.0), new Order("o2", 80.0))),
                        new Customer("Bob", List.of(new Order("o3", 50.0)))
                )),
                new Course("Python", List.of(
                        new Customer("Charlie", List.of(new Order("o4", 200.0))),
                        new Customer("Alice", List.of(new Order("o5", 150.0)))
                ))
        );
        courses.stream()
                .collect(Collectors.groupingBy(
                        Course::name,
                        Collectors.flatMapping(
                                course -> course.customers.stream()
                                        .map(customer -> customer.name),
                                Collectors.toList())))
                .entrySet().forEach(System.out::println);


    }

    /**
     * Собрать список всех заказов всех клиентов в один {@code List<Order>.} <p>
     * Т.е. результат: {@code [Order(o1,120.0), Order(o2,80.0), Order(o3,50.0), Order(o4,200.0), Order(o5,150.0)]}
     */
    private static void listOrderTask() {
        record Order(String id, double amount) {
        }
        record Customer(String name, List<Order> orders) {
        }

        List<Customer> customers = List.of(
                new Customer("Alice", List.of(new Order("o1", 120.0), new Order("o2", 80.0))),
                new Customer("Bob", List.of(new Order("o3", 50.0))),
                new Customer("Charlie", List.of(new Order("o4", 200.0), new Order("o5", 150.0)))
        );

        customers.stream()
                .flatMap(customer -> customer.orders.stream())
                .forEach(System.out::println);

        customers.stream()
                .collect(Collectors.flatMapping(customer -> customer.orders.stream(), Collectors.toList()))
                .forEach(System.out::println);

    }
}
