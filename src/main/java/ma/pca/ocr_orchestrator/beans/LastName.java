package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@AllArgsConstructor
@ToString
public class LastName extends StringEqualsValidation implements Validation {
    private String lastName;

    @JsonValue
    String toValue() {
        return getLastName();
    }

    @JsonCreator
    static LastName fromValue(String value) {
        return new LastName(value);
    }

    @Override
    public ResultCompare isValid(Object object) {
        if (!(object instanceof LastName)) {
            return ResultCompare.CLASS_NOT_CASTED;
        }
        LastName lastName = (LastName) object;

        return super.isValid(this.getLastName(), lastName.getLastName());
    }
    @Override
    public String toString() {
        return lastName;
    }
}
