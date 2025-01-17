package ru.ob11to.datastructure.assertj;

public class SimpleModel {

    private String name = "";

    public String getName() {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return name;
    }

    public void setName(String word, int number) {
        if (word.length() != number) {
            throw new IllegalArgumentException(
                    String.format("this word: %s has length not equal to : %s", word, number)
            );
        }
        this.name = word;
    }
}

