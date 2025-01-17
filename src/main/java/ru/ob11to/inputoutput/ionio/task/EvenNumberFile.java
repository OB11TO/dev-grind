package ru.ob11to.inputoutput.ionio.task;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class EvenNumberFile {

    public static void main(String[] args) {
        try (var in = new FileInputStream("data/even.txt")) {
            int read;
            StringBuilder sb = new StringBuilder();
            while ((read = in.read()) != -1) {
                sb.append((char) read);
            }
            Integer[] array = Arrays.stream(sb.toString().split(System.lineSeparator()))
                    .map(Integer::valueOf)
                    .toArray(Integer[]::new);

            Arrays.stream(array)
                    .forEach(number -> {
                        if (number % 2 == 0) {
                            System.out.println(number + " – четное число");
                        } else {
                            System.out.println(number + " – нечетное число");
                        }

                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
