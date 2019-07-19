package ma.pca.ocr_orchestrator.beans;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@AllArgsConstructor
public class FirstName extends StringEqualsValidation implements Validation {
    private String firstName;



    @JsonValue
    String toValue(){
        return getFirstName();
    }

    @JsonCreator
    static FirstName fromValue(String value){
        return new FirstName(value);
    }

    @Override
    public ResultCompare isValid(Object object){
        if(!(object instanceof FirstName)){
            return ResultCompare.CLASS_NOT_CASTED;
        }
        FirstName firstName = (FirstName) object;

        return super.isValid(this.getFirstName(), firstName.getFirstName());
    }

    @Override
    public String toString() {
        return firstName;
    }
}

