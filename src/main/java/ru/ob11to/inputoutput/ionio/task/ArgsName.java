package ru.ob11to.inputoutput.ionio.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("This key: '%s' is missing".formatted(key));
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("String[] args is empty");
        }
        Arrays.stream(args).forEach(arg -> {
            if (!arg.startsWith("-") || arg.startsWith("-=") || arg.contains("==") || !arg.contains("=")) {
                throw new IllegalArgumentException("argument '" + arg + "' is invalid");
            }
            String[] parts = arg.split("=");
            values.put(parts[0].substring(1), parts[1]);
        });
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[]{"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[]{"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}