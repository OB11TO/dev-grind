package ru.ob11to.architecture.solid.srp.formatter;

public interface DateTimeParser<T> {
    String parse(T t);
}