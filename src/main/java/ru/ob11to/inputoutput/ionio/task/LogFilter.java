package ru.ob11to.inputoutput.ionio.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> list;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            list = br.lines().filter(this::isStatus404).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private boolean isStatus404(String line) {
        String[] s = line.split(" ");
        return Objects.equals(s[s.length - 2], "404");
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        logFilter.filter().forEach(System.out::println);

    }
}
