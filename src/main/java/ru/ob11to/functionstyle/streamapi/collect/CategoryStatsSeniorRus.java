package ru.ob11to.functionstyle.streamapi.collect;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Демонстрация "senior"-решения:
 * - использует BigDecimal для сумм денег
 * - один проход по Products с аккумулированием статистики по категориям
 * - корректный combiner для параллельного выполнения
 * - результат: Map<String, CategoryStats>
 */
public class CategoryStatsSeniorRus {

    // -----------------------------
    // domain records (входные данные)
    // -----------------------------
    public record Order(String id, double amount) {}
    public record Customer(String name, List<Order> orders) {}
    public record Product(String name, String category, List<Customer> customers) {}

    // -----------------------------
    // result record (тип результата)
    // -----------------------------
    public record CategoryStats(
            BigDecimal totalRevenue,        // общая выручка по категории
            Set<String> uniqueCustomers,    // уникальные клиенты (по именам)
            Optional<String> topCustomer    // клиент с наибольшей суммой по категории
    ) {}

    // ---------------------------------------------------------
    // Мутируемый аккумулятор для одной категории (внутренний тип)
    // ---------------------------------------------------------
    static final class CategoryAcc {
        // накопленная сумма по категории
        BigDecimal total = BigDecimal.ZERO;

        // сумма по каждому клиенту в рамках категории (name -> сумма)
        final Map<String, BigDecimal> sumByCustomer = new HashMap<>();

        // уникальные имена клиентов; LinkedHashSet сохраняет порядок первого вхождения
        final LinkedHashSet<String> uniqueCustomers = new LinkedHashSet<>();

        // Добавить продукт: перебираем всех customers в продукте
        void addProduct(Product p) {
            for (Customer c : p.customers()) {
                addCustomer(c);
            }
        }

        // Добавить одного клиента: суммируем его заказы и обновляем структуры
        void addCustomer(Customer c) {
            String name = c.name();
            BigDecimal custTotal = BigDecimal.ZERO;

            // суммируем все заказы клиента (в BigDecimal)
            for (Order o : c.orders()) {
                BigDecimal amt = BigDecimal.valueOf(o.amount());
                custTotal = custTotal.add(amt);
                total = total.add(amt); // увеличиваем общую сумму категории
            }

            // если у клиента есть ненулевая сумма — учитываем его
            if (custTotal.compareTo(BigDecimal.ZERO) > 0) {
                sumByCustomer.merge(name, custTotal, BigDecimal::add);
                uniqueCustomers.add(name);
            }
            // если у клиента не было заказов (custTotal == 0), мы здесь его не добавляем
            // это поведение можно изменить в зависимости от требований
        }

        // Объединить (merge) другой аккумулятор в этот (для combiner'а)
        void merge(CategoryAcc other) {
            // суммируем общие суммы
            this.total = this.total.add(other.total);

            // объединяем по-клиентски суммы (сложение по именам)
            other.sumByCustomer.forEach((name, amount) ->
                    this.sumByCustomer.merge(name, amount, BigDecimal::add));

            // добавляем уникальные имена (LinkedHashSet сохранит порядок: сначала this, потом other)
            this.uniqueCustomers.addAll(other.uniqueCustomers);
        }

        // Преобразовать аккумулятор в финальный CategoryStats
        CategoryStats toStats() {
            Optional<String> top = sumByCustomer.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);

            // Set.copyOf делает неизменяемую копию множества
            return new CategoryStats(total, Set.copyOf(uniqueCustomers), top);
        }
    }

    // ---------------------------------------------------------
    // Кастомный Collector<Product, ?, Map<String, CategoryStats>>
    // Собирает статистику по категориям за один проход
    // ---------------------------------------------------------
    public static Collector<Product, ?, Map<String, CategoryStats>> categoryStatsCollector() {
        // Поставщик: создаём пустую Map<category, CategoryAcc>
        Supplier<Map<String, CategoryAcc>> supplier = HashMap::new;

        // Аккумулятор: для каждого Product добавляем его данные в соответствующий CategoryAcc
        BiConsumer<Map<String, CategoryAcc>, Product> accumulator = (map, product) -> {
            map.computeIfAbsent(product.category(), k -> new CategoryAcc())
                    .addProduct(product);
        };

        // Комбайнер: объединяет две частичные карт (для parallelStream)
        BinaryOperator<Map<String, CategoryAcc>> combiner = (m1, m2) -> {
            // оптимизация: сливаем меньшую map в большую — меньше операций
            if (m1.size() < m2.size()) {
                Map<String, CategoryAcc> tmp = m1; m1 = m2; m2 = tmp;
            }
            // теперь merge m2 -> m1
            for (Map.Entry<String, CategoryAcc> e : m2.entrySet()) {
                m1.merge(e.getKey(), e.getValue(), (acc1, acc2) -> {
                    acc1.merge(acc2); // аккумулируем acc2 в acc1
                    return acc1;
                });
            }
            return m1;
        };

        // Финализатор: превращает Map<category, CategoryAcc> -> Map<category, CategoryStats>
        Function<Map<String, CategoryAcc>, Map<String, CategoryStats>> finisher = accMap -> {
            // используем LinkedHashMap чтобы сохранить порядок категорий (по вставке)
            Map<String, CategoryStats> out = new LinkedHashMap<>();
            accMap.forEach((category, acc) -> out.put(category, acc.toStats()));
            return out;
        };

        // Собираем Collector.of с заданными компонентами
        return Collector.of(supplier, accumulator, combiner, finisher, Collector.Characteristics.UNORDERED);
    }

    // -----------------------------
    // Демонстрация использования
    // -----------------------------
    public static void main(String[] args) {
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

        // Последовательный сбор
        Map<String, CategoryStats> stats = products.stream().collect(categoryStatsCollector());
        System.out.println("Sequential:");
        printStats(stats);

        // Параллельный сбор — должен дать тот же результат
        Map<String, CategoryStats> statsParallel = products.parallelStream().collect(categoryStatsCollector());
        System.out.println("Parallel:");
        printStats(statsParallel);



        ///-----------
        Map<String, CategoryStats> result = products.stream()
                // группируем продукты по категории
                .collect(Collectors.groupingBy(
                        Product::category,
                        // для каждой категории сначала собираем список всех customer-ов (в т.ч. повторяющихся)
                        Collectors.collectingAndThen(
                                Collectors.flatMapping(
                                        product -> product.customers().stream(),
                                        Collectors.toList()
                                ),
                                customersList -> {
                                    // 1) totalRevenue: сумма всех заказов (BigDecimal)
                                    BigDecimal totalRevenue = customersList.stream()
                                            .flatMap(c -> c.orders().stream())
                                            .map(o -> BigDecimal.valueOf(o.amount()))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    // 2) uniqueCustomers: уникальные имена клиентов (LinkedHashSet сохраняет порядок первого появления)
                                    Set<String> uniqueCustomers = customersList.stream()
                                            .map(Customer::name)
                                            .collect(Collectors.toCollection(LinkedHashSet::new));

                                    // 3) sumByCustomer: суммируем чеки по имени клиента внутри категории
                                    //    используем reducing, чтобы получить BigDecimal
                                    Map<String, BigDecimal> sumByCustomer = customersList.stream()
                                            .collect(Collectors.groupingBy(
                                                    Customer::name,
                                                    Collectors.reducing(
                                                            BigDecimal.ZERO,
                                                            c -> c.orders().stream()
                                                                    .map(o -> BigDecimal.valueOf(o.amount()))
                                                                    .reduce(BigDecimal.ZERO, BigDecimal::add),
                                                            BigDecimal::add
                                                    )
                                            ));

                                    // 4) topCustomer: имя клиента с максимальной суммой
                                    Optional<String> topCustomer = sumByCustomer.entrySet().stream()
                                            .max(Map.Entry.comparingByValue())
                                            .map(Map.Entry::getKey);

                                    return new CategoryStats(totalRevenue, uniqueCustomers, topCustomer);
                                }
                        )
                ));
    }

    private static void printStats(Map<String, CategoryStats> stats) {
        stats.forEach((cat, s) -> {
            System.out.println("Category: " + cat);
            System.out.println("  totalRevenue = " + s.totalRevenue());
            System.out.println("  uniqueCustomers = " + s.uniqueCustomers());
            System.out.println("  topCustomer = " + s.topCustomer().orElse("(none)"));
            System.out.println();
        });
    }
}

