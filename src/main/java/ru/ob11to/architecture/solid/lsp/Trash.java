package ru.ob11to.architecture.solid.lsp;

import java.time.LocalDate;

public class Trash extends AbstractStore {

    @Override
    public boolean accept(Food product, LocalDate instantDate) {
        return product.getExpiryPercent(instantDate) >= 100;
    }
}
