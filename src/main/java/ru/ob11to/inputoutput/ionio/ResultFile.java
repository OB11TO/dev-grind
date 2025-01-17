package ru.ob11to.inputoutput.ionio;

import java.io.FileOutputStream;
import java.io.IOException;

public class ResultFile {
    public static void main(String[] args) {

        // файл будет перезаписываться всегда
        try (FileOutputStream output = new FileOutputStream("data/dataresult.txt")) {
            output.write("Hello, world!".getBytes());
            output.write(System.lineSeparator().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}