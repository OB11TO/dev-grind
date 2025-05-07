package ru.ob11to.architecture.ocp;

import java.util.Objects;

public class Document {
    private String id;
    private DocumentType type;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Document(String id, DocumentType type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return Objects.equals(id, document.id) && type == document.type && Objects.equals(content, document.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, content);
    }
}

