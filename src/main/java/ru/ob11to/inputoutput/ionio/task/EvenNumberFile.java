package ru.ob11to.inputoutput.ionio.task;

import java.io.FileOutputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (var out = new FileOutputStream("data/task/even.txt")) {
            int size = 10;
            String newLine;
            for (int i = 1; i <= size; i++) {
                // Внутренний цикл для второго числа (вертикальная ось)
                for (int j = 1; j <= size; j++) {
                    // Формирование строки таблицы умножения: i * j = результат
                    newLine = "%d * %d = %d".formatted(i, j, i * j);
                    out.write(newLine.getBytes());
                    out.write(System.lineSeparator().getBytes()); // Переход на новую строку после каждого произведения
                }
                out.write(System.lineSeparator().getBytes()); // Дополнительная строка для разделения строк таблицы
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

