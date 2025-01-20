package ru.ob11to.inputoutput.ionio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

public class UsageEncoding {
    public String readFile(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.lines()
                    .map(string -> string + System.lineSeparator())
                    .forEach(builder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void writeDataInFileCoding(String path, String data) {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            writer.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Хороший пример, чтобы передавать сразу список, а не только одну строку, как выше. Меньше открываем ресурс.
    public void writeDataInFile(String path, List<String> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            data.forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "./data/task/text.txt";
        UsageEncoding encoding = new UsageEncoding();
        String string = encoding.readFile(path);
        System.out.println("Данные из файла: ");
        System.out.println(string);
    }

}