package ru.ob11to.inputoutput.ionio;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

public class Abuse {

    public static void drop(String source, String target, List<String> words) throws IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(source));
             PrintWriter output = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)))) {
            input.lines()
                    .flatMap(line -> Stream.of(line.split("\\s+")))
                    .filter(word -> !words.contains(word)).map(word -> word + " ")
                    .forEach(output::print);
        }
    }
}