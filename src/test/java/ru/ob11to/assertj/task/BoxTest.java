package ru.ob11to.assertj.task;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .doesNotContain("something")
                .isNotEmpty();
    }

    @Test
    void whenNumberOfVertices10ThenTrue() {
        Box box = new Box(4, 10);
        int result = box.getNumberOfVertices();
        assertThat(result).isEqualTo(4)
                .isEven()
                .isGreaterThan(3)
                .isPositive();
    }

    @Test
    void whenNumberOfVerticesThenTrue() {
        Box box = new Box(8, 10);
        int result = box.getNumberOfVertices();
        assertThat(result).isEqualTo(8)
                .isEven()
                .isLessThan(10);
    }

    @Test
    void whenNotExistThenFalse() {
        Box box = new Box(-1, 10);
        boolean result = box.isExist();
        assertThat(result).isFalse();
    }

    @Test
    void whenExistThenTrue() {
        Box box = new Box(4, 10);
        boolean result = box.isExist();
        assertThat(result).isTrue();
    }

    @Test
    void whenVertexIs0ThenGetArea() {
        Box box = new Box(0, 2);
        double result = box.getArea();
        assertThat(result).isEqualTo(50.24d, withPrecision(0.03d))
                .isCloseTo(50.2d, withPrecision(0.1d))
                .isGreaterThan(10);
    }

    @Test
    void whenVertexIs4ThenGetArea() {
        Box box = new Box(4, 3);
        double result = box.getArea();
        assertThat(result).isEqualTo(15.57d, withPrecision(0.02d))
                .isCloseTo(15.5d, Percentage.withPercentage(2.5d))
                .isLessThan(15.59d);

    }
}