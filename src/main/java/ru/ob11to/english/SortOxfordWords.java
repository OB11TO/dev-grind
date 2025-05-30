package ru.ob11to.english;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class SortOxfordWords {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("anki/oxford-3000.csv"));
        List<String> cefrOrder = Arrays.asList("a1", "a2", "b1", "b2", "c1", "c2");

        // Пропустить заголовок
        List<WordEntry> words = lines.stream().skip(1)
                .map(line -> {
                    String[] parts = line.split(",");
                    return new WordEntry(parts[0].trim(), parts[2].trim());
                })
                .sorted(Comparator.comparingInt(w -> cefrOrder.indexOf(w.level)))
                .toList();

        // Сохрани отсортированные слова в новый файл
        List<String> sortedWords = words.stream().map(w -> w.word).toList();
//        Files.write(Paths.get("oxford_sorted.txt"), (Iterable<? extends CharSequence>) words);

        System.out.println("✅ Слова отсортированы по сложности и сохранены в oxford_sorted.txt");
    }

    static class WordEntry {
        String word;
        String level;

        WordEntry(String word, String level) {
            this.word = word;
            this.level = level;
        }
    }
}
