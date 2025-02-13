package ru.ob11to.architecture.tdd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Disabled("Тесты отключены. Удалить аннотацию после реализации всех методов.")
class GeneratorImplTest {
    @Test
    public void whenProduceSuccessful() {
        Generator generator = new GeneratorImpl();
        Map<String, String> args = Map.of("name", "Petr Arsentev", "subject", "you");
        String expected = generator.produce("I am a ${name}, Who are ${subject}? ", args);
        String actual = "I am a Petr Arsentev, Who are you? ";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenNoKeyInArgsThanGetException() {
        Generator generator = new GeneratorImpl();
        Map<String, String> args = Map.of("name", "Petr Arsentev");
        assertThatThrownBy(() -> generator.produce("I am a ${name}, Who are ${subject}? ", args))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenExtraKeyInArgsThanGetException() {
        Generator generator = new GeneratorImpl();
        Map<String, String> args = Map.of("name", "Petr Arsentev", "subject", "you", "age", "35");
        assertThatThrownBy(() -> generator.produce("I am a ${name}, Who are ${subject}? ", args))
                .isInstanceOf(IllegalArgumentException.class);
    }

}