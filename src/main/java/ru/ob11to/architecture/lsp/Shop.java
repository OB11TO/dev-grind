package ru.ob11to.architecture.lsp;

import java.time.LocalDate;

public class Shop extends AbstractStore {

    @Override
    public boolean accept(Food product, LocalDate instantDate) {
        double percent = product.getExpiryPercent(instantDate);
        return percent >= 25 && percent < 100;
    }

    @Override
    public void add(Food product, LocalDate instantDate) {
        double percent = product.getExpiryPercent(instantDate);
        if (percent > 75) {
            product.setPrice(product.getPrice() * (1 - product.getDiscount()));
        }
        super.add(product, instantDate);
    }
}
