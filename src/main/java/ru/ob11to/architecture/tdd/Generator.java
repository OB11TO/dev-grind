package ru.ob11to.architecture.tdd;

import java.util.Map;

public interface Generator {
    String produce(String template, Map<String, String> args);
}
