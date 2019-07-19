package ma.pca.ocr_orchestrator.beans;

import lombok.Data;

@Data
public class FieldProps {
    private String name;
    private Class className;

    public FieldProps(String name, Class className) {
        this.name = name;
        this.className = className;
    }
}
