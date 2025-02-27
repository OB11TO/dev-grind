package ru.ob11to.architecture.lsp;

import java.time.LocalDate;
import java.util.List;

public class ControlQuality {
    private final List<Store> stores;

    public ControlQuality(List<Store> stores) {
        this.stores = stores;
    }

    public void distributeProduct(Food product, LocalDate instantDate) {
        for (Store store : stores) {
            if (store.accept(product, instantDate)) {
                store.add(product, instantDate);
                break;
            }
        }
    }


}
