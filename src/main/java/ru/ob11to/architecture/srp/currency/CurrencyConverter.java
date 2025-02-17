package ru.ob11to.architecture.srp.currency;

public interface CurrencyConverter {
    double convert(Currency source, double sourceValue, Currency target);
}