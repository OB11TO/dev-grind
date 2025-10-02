package ru.ob11to.architecture.solid.srp.report;

import ru.ob11to.architecture.solid.srp.model.Employee;

import java.util.function.Predicate;

public interface Report {
    String generate(Predicate<Employee> filter);
}