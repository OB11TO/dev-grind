package ru.ob11to.inputoutput.ionio;

import java.io.CharArrayReader;
import java.util.Scanner;

public class ScannerExample1 {
    public static void main(String[] args) {
        var ls = System.lineSeparator();
        var data = String.join(ls,
                "1 A 2",
                "3 4 B",
                "C 5 6"
        );
        var scanner = new Scanner(new CharArrayReader(data.toCharArray()));
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                System.out.print(scanner.nextInt());
                System.out.print(" ");
            } else {
                scanner.next();
            }
        }
    }
}