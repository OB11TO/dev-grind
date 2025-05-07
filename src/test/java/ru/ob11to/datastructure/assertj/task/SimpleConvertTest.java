package ru.ob11to.datastructure.assertj.task;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Добавьте тестовые методы на каждый метод класса SimpleConvert,
 * в которых покажите возможности проверки содержимого коллекции и отдельных ее элементов.
 */
class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> array = simpleConvert.toList("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second", Index.atIndex(1))
                .endsWith("four", "five")
                .isNotNull()
                .allMatch(a -> a.length() < 10)
                .element(0).isNotNull().isEqualTo("first");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("first", "second", "three", "four", "five");
        assertThat(set).isNotNull()
                .hasSize(5)
                .anyMatch(e -> e.length() == 6)
                .noneMatch(e -> e.length() < 3)
                .containsAnyOf("second", "six", "nine")
                .allMatch(e -> e.length() >= 4);
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("first", "second", "three", "four", "five");
        assertThat(map).isNotNull()
                .hasSize(5)
                .containsKeys("first", "three")
                .containsValues(3, 4)
                .doesNotContainKey("eleven")
                .doesNotContainValue(11)
                .containsEntry("four", 3);
    }
}