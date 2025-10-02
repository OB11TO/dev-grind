package ru.ob11to.architecture.solid.lsp;

import java.time.LocalDate;

public interface Store {

    void add(Food product, LocalDate instantDate);

    boolean accept(Food product, LocalDate date);
}
