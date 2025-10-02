package ru.ob11to.architecture.lsp;

import org.junit.jupiter.api.Test;
import ru.ob11to.architecture.solid.lsp.ControlQuality;
import ru.ob11to.architecture.solid.lsp.Food;
import ru.ob11to.architecture.solid.lsp.Shop;
import ru.ob11to.architecture.solid.lsp.Trash;
import ru.ob11to.architecture.solid.lsp.Warehouse;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WarehouseTest {

    @Test
    void whenAddToWarehouse() {
        LocalDate now = LocalDate.now();
        Food food = new Food("Milk", now.minusDays(10), now.plusDays(50), 50d, 0.2d);
        Warehouse warehouse = new Warehouse();
        ControlQuality control = new ControlQuality(List.of(new Trash(), new Shop(), warehouse));
        control.distributeProduct(food, now);
        assertThat(warehouse.getFoods()).containsExactly(food);
    }

}