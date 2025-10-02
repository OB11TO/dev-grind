package ru.ob11to.architecture.solid.srp.currency;

public interface CurrencyConverter {
    double convert(Currency source, double sourceValue, Currency target);
}