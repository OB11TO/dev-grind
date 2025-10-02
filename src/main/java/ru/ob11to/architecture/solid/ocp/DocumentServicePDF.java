package ru.ob11to.architecture.solid.ocp;

public class DocumentServicePDF implements DocumentStrategy {

    @Override
    public void process(Document documents) {
        System.out.println("Специфическая логика для обработки PDF");
    }
}