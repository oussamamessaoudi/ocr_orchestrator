package ma.pca.ocr_orchestrator.ocr;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Code {
    private boolean valid;
    @JsonValue
    boolean toValue(){
        return isValid();
    }

    @JsonCreator
    static Code fromValue(String value){
        return new Code("0".equals(value));
    }
}
