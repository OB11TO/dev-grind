package ru.ob11to.datastructure.assertj.task;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Напишите тесты, в которых проверьте генерацию всех исключений,
 * которые должен генерировать класс NameLoad. Проверьте,
 * что в сообщении об ошибке передаются параметры, которые эту ошибку вызвали.
 * Например, если передать в метод parse строку с нарушением формата, например, такую
 * "key:value", то будет сгенерировано исключение и в сообщении об ошибке будет строка
 * "key:value", которая это исключение вызвала. Этот факт надо проверить в тесте.
 * Так же и для других исключений.
 */
class NameLoadTest {

    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void whenValidateThenFirstException() {
        NameLoad nameLoad = new NameLoad();
        String values = "key:value";
        assertThatThrownBy(() -> nameLoad.parse(values))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageMatching("^.+")
                .hasMessageContaining("symbol");
    }

    @Test
    void whenValidateThenSecondException() {
        NameLoad nameLoad = new NameLoad();
        String name = "=Denis";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name)
                .hasMessageContaining("key");
    }

    @Test
    void whenValidateThenThirdException() {
        NameLoad nameLoad = new NameLoad();
        String name = "first=";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name)
                .hasMessageContaining("value");
    }

    // АНАЛОГИЧЕН, НО ПЕРВЫЙ МНЕ НРАВИТСЯ БОЛЬШЕ !!!!
    @Test
    void whenValidateThenFourthException() {
        NameLoad nameLoad = new NameLoad();
        String name = "first=";
        var exception = assertThrows(IllegalArgumentException.class, () -> nameLoad.parse(name));
        assertThat(exception.getMessage()).isEqualTo("this name: %s does not contain a value".formatted(name));
    }
}