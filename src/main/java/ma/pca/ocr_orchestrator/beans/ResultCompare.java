package ma.pca.ocr_orchestrator.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultCompare {

    public static final ResultCompare CLASS_NOT_CASTED = new ResultCompare(false, ErrorMessage.CLASS_NOT_CASTED);
    public static final ResultCompare MESSING_VALUE = new ResultCompare(false, ErrorMessage.MESSING_VALUE);
    public static final ResultCompare UN_CONFORMITY = new ResultCompare(false, ErrorMessage.UN_CONFORMITY);
    public static final ResultCompare CIN_PROBLEM = new ResultCompare(false, ErrorMessage.CIN_PROBLEM);
    public static final ResultCompare NUMBER_PROBLEM = new ResultCompare(false, ErrorMessage.NUMBER_PROBLEM);
    public static final ResultCompare SPECIMEN_PROBLEM = new ResultCompare(true, ErrorMessage.SPECIMEN_PROBLEM);

    public static final ResultCompare ALL_OKAY = new ResultCompare(true, ErrorMessage.ALL_OKAY);

    public ResultCompare(boolean valid, ErrorMessage message) {
        this.valid = valid;
        this.message = message;
    }

    private boolean valid;
    private ErrorMessage message;
    private Set ocrData;
    private Object kycData;
}

