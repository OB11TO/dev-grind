package ru.ob11to.architecture.solid.lsp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStore implements Store {

    private final List<Food> foods = new ArrayList<>();

    @Override
    public void add(Food product, LocalDate instantDate) {
        foods.add(product);
    }

    @Override
    public abstract boolean accept(Food product, LocalDate date);

    public List<Food> getFoods() {
        return new ArrayList<>(foods);
    }
}
