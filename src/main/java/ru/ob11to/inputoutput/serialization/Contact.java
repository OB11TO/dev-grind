package ru.ob11to.inputoutput.serialization;

import java.io.*;
import java.nio.file.Files;

public class Contact implements Serializable {

    @Serial
    private static final long serialVersionUID = 5780672321457505738L;

    private final String phone;

    public Contact(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" + ", phone='" + phone + '\'' + '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact = new Contact("+7 (111) 111-11-11");

        /* Запись объекта во временный файл, который удалится системой */
        File tempFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ObjectOutputStream oos =
                     new ObjectOutputStream(fos)) {
            oos.writeObject(contact);
        }

        /* Чтение объекта из файла */
        try (FileInputStream fis = new FileInputStream(tempFile);
             ObjectInputStream ois =
                     new ObjectInputStream(fis)) {
            final Contact contactFromFile = (Contact) ois.readObject();
            System.out.println(contactFromFile);
        }
    }
}

