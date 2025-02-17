package ru.ob11to.architecture.srp.report;

import ru.ob11to.architecture.srp.model.Employee;

import java.util.function.Predicate;

public interface Report {
    String generate(Predicate<Employee> filter);
}