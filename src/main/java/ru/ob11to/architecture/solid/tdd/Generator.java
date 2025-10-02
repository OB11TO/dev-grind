package ru.ob11to.architecture.solid.tdd;

import java.util.Map;

public interface Generator {
    String produce(String template, Map<String, String> args);
}
