package ru.ob11to.inputoutput.ionio;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class ScannerExample2 {
    public static void main(String[] args) {
        var data = "empl1@mail.ru, empl2@mail.ru, empl3@mail.ru";
        var scanner = new Scanner(new ByteArrayInputStream(data.getBytes()))
                .useDelimiter(", ");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }
}

class ScannerExample3 {
    public static void main(String[] args) throws Exception {
        var data = "A 1B FF 110";
        var file = File.createTempFile("data", null);
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
            output.write(data.getBytes());
        }
        try (var scanner = new Scanner(file).useRadix(16)) {
            while (scanner.hasNextInt()) {
                System.out.print(scanner.nextInt());
                System.out.print(" ");
            }
        }
    }
}