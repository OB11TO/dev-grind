package ru.ob11to.architecture.srp.formatter;

public interface DateTimeParser<T> {
    String parse(T t);
}