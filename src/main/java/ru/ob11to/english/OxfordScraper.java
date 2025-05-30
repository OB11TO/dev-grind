package ru.ob11to.english;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OxfordScraper {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> words = readWordsFromFile("oxford_sorted.txt");
        List<Map<String, String>> results = new ArrayList<>();

        int counter = 1;

        for (String word : words) {
            System.out.printf("[%d/%d] Обрабатывается: %s%n", counter++, words.size(), word);

            try {
                String url = "https://www.oxfordlearnersdictionaries.com/definition/english/" + word;
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(5000)
                        .get();

                String headword = doc.selectFirst("h1.headword") != null
                        ? doc.selectFirst("h1.headword").text() : word;
                String partOfSpeech = doc.selectFirst("span.pos") != null
                        ? doc.selectFirst("span.pos").text() : "N/A";
                String cefr = doc.selectFirst("span.cef-level") != null
                        ? doc.selectFirst("span.cef-level").text() : "N/A";

                Elements senses = doc.select("li.sense");
                for (Element sense : senses) {
                    String definition = sense.selectFirst("span.def") != null
                            ? sense.selectFirst("span.def").text() : "";

                    List<String> examples = new ArrayList<>();
                    Elements exBlocks = sense.select("ul.examples li");
                    for (Element ex : exBlocks) {
                        examples.add(ex.text());
                    }

                    Map<String, String> entry = new LinkedHashMap<>();
                    entry.put("Word", headword);
                    entry.put("Part of Speech", partOfSpeech);
                    entry.put("CEFR Level", cefr);
                    entry.put("Definition", definition);
                    entry.put("Examples", String.join(" | ", examples));

                    results.add(entry);
                }

                Thread.sleep(1500); // задержка между запросами

            } catch (Exception e) {
                System.err.println("Ошибка при обработке слова: " + word);
                e.printStackTrace();
            }
        }

        writeCSV(results, "oxford_3000_output.csv");
        System.out.println("✅ CSV сохранён: oxford_3000_output.csv");
    }

    private static List<String> readWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                words.add(line.trim());
            }
        }
        return words;
    }

    private static void writeCSV(List<Map<String, String>> data, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), StandardCharsets.UTF_8));

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Word", "Part of Speech", "CEFR Level", "Definition", "Examples"));

        for (Map<String, String> entry : data) {
            csvPrinter.printRecord(entry.get("Word"),
                    entry.get("Part of Speech"),
                    entry.get("CEFR Level"),
                    entry.get("Definition"),
                    entry.get("Examples"));
        }

        csvPrinter.flush();
        csvPrinter.close();
    }
}
