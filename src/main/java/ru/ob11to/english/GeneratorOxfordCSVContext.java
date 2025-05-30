package ru.ob11to.english;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class GeneratorOxfordCSVContext {

    public static void main(String[] args) throws IOException, CsvException {
        String translationsPath = "anki/oxford3000_with_russian_translations.csv";
        String cefrPath = "anki/oxford-3000.csv";
        String outputPath = "anki/oxford3000WithCERF.csv";

        Map<String, WordRow> wordMap = loadWordMap(translationsPath);
        applyCefrLevels(wordMap, cefrPath);
        writeSortedCSV(wordMap, outputPath);
    }

    private static Map<String, WordRow> loadWordMap(String filePath) throws IOException, CsvException {
        Map<String, WordRow> map = new HashMap<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
                .build()) {

            List<String[]> lines = reader.readAll();
            for (String[] row : lines) {
                if (row.length >= 10) {
                    map.put(row[0].trim(), new WordRow(
                            row[0].trim(), row[1].trim(), row[2].trim(), row[3].trim(), row[4].trim(),
                            row[5].trim(), row[6].trim(), row[7].trim(), row[8].trim(), row[9].trim(), null
                    ));
                }
            }
        }
        return map;
    }

    private static void applyCefrLevels(Map<String, WordRow> wordMap, String cefrFilePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(cefrFilePath));
        if (lines.isEmpty()) {
            return;
        }

        // Обработка заголовка
        String[] header = lines.get(0).split(",");
        if (header.length >= 3) {
            WordRow headerRow = wordMap.get(header[0].trim());
            if (headerRow != null) {
                headerRow.cefr = "cefr";
            }
        }

        // Обработка остальных строк
        lines.stream()
                .skip(1)
                .map(line -> line.split(","))
                .filter(parts -> parts.length >= 3)
                .forEach(parts -> {
                    String word = parts[0].trim();
                    WordRow row = wordMap.get(word);
                    if (row != null) {
                        row.cefr = parts[2].trim();
                    }
                });
    }

    private static void writeSortedCSV(Map<String, WordRow> wordMap, String outputPath) throws IOException {
        List<String[]> rows = wordMap.values().stream()
                .sorted(Comparator
                        .comparing((WordRow row) -> row.cefr, Comparator.nullsLast(String::compareToIgnoreCase))
                        .thenComparing(row -> row.word, Comparator.nullsLast(String::compareToIgnoreCase)))
                .map(row -> new String[]{
                        row.word,
                        row.definition,
                        row.turkishTranslation,
                        row.exampleSentence,
                        row.partSpeech,
                        row.relatedForms,
                        row.synonyms,
                        row.antonyms,
                        row.collocations,
                        row.russianTranslation,
                        row.cefr
                })
                .collect(Collectors.toList());

        try (CSVWriter writer = new CSVWriter(new FileWriter(outputPath))) {
            writer.writeAll(rows);
        }
    }

    // Вынесенная модель
    static class WordRow {
        String word;
        String definition;
        String turkishTranslation;
        String exampleSentence;
        String partSpeech;
        String relatedForms;
        String synonyms;
        String antonyms;
        String collocations;
        String russianTranslation;
        String cefr;

        public WordRow(String word, String definition, String turkishTranslation, String exampleSentence,
                       String partSpeech, String relatedForms, String synonyms, String antonyms,
                       String collocations, String russianTranslation, String cefr) {
            this.word = word;
            this.definition = definition;
            this.turkishTranslation = turkishTranslation;
            this.exampleSentence = exampleSentence;
            this.partSpeech = partSpeech;
            this.relatedForms = relatedForms;
            this.synonyms = synonyms;
            this.antonyms = antonyms;
            this.collocations = collocations;
            this.russianTranslation = russianTranslation;
            this.cefr = cefr;
        }
    }
}
