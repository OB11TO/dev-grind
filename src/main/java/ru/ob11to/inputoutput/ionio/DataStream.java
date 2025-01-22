package ru.ob11to.inputoutput.ionio;

import java.io.*;

public class DataStream {
    public static void main(String[] args) throws Exception {
        String path = "data/dataOutput.txt";
        String[] names = {"unit1", "unit2", "unit3"};
        int[] amounts = {5, 7, 2};
        boolean[] checked = {true, false, true};

        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(path));
             DataInputStream input = new DataInputStream(new FileInputStream(path))) {
            for (int i = 0; i < names.length; i++) {
                output.writeUTF(names[i]);
                output.writeInt(amounts[i]);
                output.writeBoolean(checked[i]);
            }
            while (true) {
                String name = input.readUTF();
                int amount = input.readInt();
                boolean check = input.readBoolean();
                System.out.println("Наименование: " + name
                        + ", количество: " + amount
                        + ", проверен: " + check);
            }
        } catch (EOFException e) {
            System.out.println("Достигнут конец файла");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}