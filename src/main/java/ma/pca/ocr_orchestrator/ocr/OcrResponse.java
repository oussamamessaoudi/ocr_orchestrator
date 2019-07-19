package ma.pca.ocr_orchestrator.ocr;

import lombok.Data;
import lombok.ToString;
import ma.pca.ocr_orchestrator.beans.SpecimenSignature;

@Data
@ToString
public class OcrResponse {
    private Code code;
    private String message;
    private Cin prediction;
    private String error;
    private SpecimenSignature specimen_signature;
}
