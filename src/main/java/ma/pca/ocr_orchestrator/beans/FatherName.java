package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FatherName extends ParentsValidation implements Validation {
    private String fatherName;

    @JsonValue
    String toValue(){
        return getFatherName();
    }

    @JsonCreator
    static FatherName fromValue(String value){
        return new FatherName(value);
    }

    @Override
    public ResultCompare isValid(Object object){
        if(!(object instanceof FatherName)){
            return ResultCompare.CLASS_NOT_CASTED;
        }
        FatherName fatherName = (FatherName) object;

        return super.isValid(this.getFatherName().toUpperCase(), fatherName.getFatherName().toUpperCase());
    }
    @Override
    public String toString() {
        return fatherName;
    }
}
