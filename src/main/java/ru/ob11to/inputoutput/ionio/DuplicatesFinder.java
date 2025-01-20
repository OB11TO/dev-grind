package ru.ob11to.inputoutput.ionio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DuplicatesFinder {
    public static void main(String[] args) throws IOException {
//        Files.walkFileTree(Path.of("./"), new DuplicatesVisitor());
        DuplicatesVisitor visitor = new DuplicatesVisitor();
        Files.walkFileTree(Path.of("."), visitor);
        visitor.getDuplicates().keySet().forEach(System.out::println);
    }
}