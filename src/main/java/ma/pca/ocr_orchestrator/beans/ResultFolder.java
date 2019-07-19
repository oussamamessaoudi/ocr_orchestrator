package ma.pca.ocr_orchestrator.beans;

import lombok.Data;
import lombok.ToString;

import java.util.Map;


@Data
@ToString
public class ResultFolder {
    private Map<String, ResultCompare> resultCompare;
    private boolean valid;
    private String Message;

}
