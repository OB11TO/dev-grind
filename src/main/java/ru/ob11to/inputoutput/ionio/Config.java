package ru.ob11to.inputoutput.ionio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            in.lines()
                    .filter(l -> !l.isEmpty() && !l.startsWith("#"))
                    .forEach(l -> {
                        if (l.startsWith("=")) {
                            throw new IllegalArgumentException();
                        }
                        String[] parts = l.split("=");
                        if (parts.length == 2) {
                            values.put(parts[0], parts[1]);
                        } else if (parts.length == 1) {
                            values.put(parts[0], "");
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String value(String key) {
        throw new UnsupportedOperationException("Don't impl this method yet!");
    }

    @Override
    public String toString() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            reader.lines().forEach(output::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("data/app.properties"));
    }

}