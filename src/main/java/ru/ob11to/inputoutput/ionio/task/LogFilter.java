package ru.ob11to.inputoutput.ionio.task;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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

    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        List<String> filterLog = logFilter.filter();
        filterLog.forEach(System.out::println);
        String fileErrorStatus = "data/task/404.txt";
        save(filterLog, fileErrorStatus);

    }
}
