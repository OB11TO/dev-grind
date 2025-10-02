package ru.ob11to.architecture.tdd;

import org.junit.jupiter.api.Test;
import ru.ob11to.architecture.solid.tdd.Fool;

import static org.assertj.core.api.Assertions.*;

class FoolTest {

    @Test
    void whenSetupDel5ThenReturnBuzz() {
        int startAt = 5;
        assertThat(Fool.setup(startAt)).isEqualTo("Buzz");
    }

    @Test
    public void whenFizzBuzz() {
        assertThat(Fool.setup(15)).isEqualTo("FizzBuzz");
    }

    @Test
    public void whenFizz() {
        assertThat(Fool.setup(3)).isEqualTo("Fizz");
    }

    @Test
    public void whenBuzz() {
        assertThat(Fool.setup(5)).isEqualTo("Buzz");
    }

    @Test
    public void when11() {
        assertThat(Fool.setup(11)).isEqualTo("11");
    }
}