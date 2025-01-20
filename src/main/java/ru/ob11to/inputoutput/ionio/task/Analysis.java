package ru.ob11to.inputoutput.ionio.task;

import java.io.*;
import java.util.Objects;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader in = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)))) {
            String start = "";
            String stop = "";
            while (in.ready()) {
                String[] parts = in.readLine().split(" ");
                if (("400".equals(parts[0]) || "500".equals(parts[0])) && start.isEmpty()) {
                    start = parts[1];
                } else if (!Objects.equals(start, "") && ("200".equals(parts[0]) || "300".equals(parts[0]))) {
                    stop = parts[1];
                }
                if (!Objects.equals(start, "") && !Objects.equals(stop, "")) {
                    out.println(start + ";" + stop);
                    start = "";
                    stop = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String source = "./data/server.log";
        String target = "./data/target.csv";
        Analysis analysis = new Analysis();
        analysis.unavailable(source, target);
    }
}