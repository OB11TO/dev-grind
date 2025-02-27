package ru.ob11to.architecture.lsp;

import java.time.LocalDate;

public class Warehouse extends AbstractStore {

    @Override
    public boolean accept(Food product, LocalDate instantDate) {
        return product.getExpiryPercent(instantDate) < 25;
    }
}
