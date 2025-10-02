package ru.ob11to.architecture.solid.ocp;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DocumentHandler {

    private final Map<DocumentType, DocumentStrategy> documentTypeDocumentStrategyMap;

    public DocumentHandler() {
        this.documentTypeDocumentStrategyMap = new EnumMap<>(DocumentType.class);
        initializeHandlers();
    }

    private void initializeHandlers() {
        documentTypeDocumentStrategyMap.put(DocumentType.XML, new DocumentServiceXML());
        documentTypeDocumentStrategyMap.put(DocumentType.DOCX, new DocumentServiceDOCX());
        documentTypeDocumentStrategyMap.put(DocumentType.PDF, new DocumentServicePDF());
    }

    public void processDocuments(List<Document> documents) {
        for (Document document : documents) {
            documentHandler(document);
        }
    }

    public void documentHandler(Document document) {
        DocumentStrategy handler = documentTypeDocumentStrategyMap.get(document.getType());
        if (handler != null) {
            handler.process(document);
        } else {
            throw new IllegalArgumentException("No handler found for document type: " + document.getType());
        }
    }

    public static void main(String[] args) {
        DocumentHandler documentHandler = new DocumentHandler();
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("1", DocumentType.DOCX, "DOCX =)"));
        documents.add(new Document("2", DocumentType.PDF, "PDF =)"));
        documents.add(new Document("3", DocumentType.XML, "XML =)"));
        documentHandler.processDocuments(documents);
    }
}
