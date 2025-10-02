package ru.ob11to.architecture.solid.srp.store;

import ru.ob11to.architecture.solid.srp.model.Employee;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    void add(Employee employee);

    List<Employee> findBy(Predicate<Employee> filter);
}