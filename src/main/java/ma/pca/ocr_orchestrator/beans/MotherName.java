package ma.pca.ocr_orchestrator.beans;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MotherName extends ParentsValidation implements Validation  {

    private String motherName;

    @JsonValue
    String toValue(){
        return getMotherName();
    }

    @JsonCreator
    static MotherName fromValue(String value){
        return new MotherName(value);
    }

    @Override
    public ResultCompare isValid(Object object){
        if(!(object instanceof MotherName)){
            return ResultCompare.CLASS_NOT_CASTED;
        }
        MotherName motherName = (MotherName) object;

        return super.isValid(this.getMotherName().toUpperCase(), motherName.getMotherName().toUpperCase());
    }

    @Override
    public String toString() {
        return motherName;
    }
}
