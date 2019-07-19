package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CardNumber extends StringEqualsValidation implements Validation {

    private String cardNumber;

    @JsonValue
    String toValue(){
        return getCardNumber();
    }

    @JsonCreator
    static CardNumber fromValue(String value){
        return new CardNumber(value);
    }

    @Override
    public ResultCompare isValid(Object object) {
        if (!(object instanceof CardNumber)) {
            return ResultCompare.CLASS_NOT_CASTED;
        }
        CardNumber lastName = (CardNumber) object;

        return super.isValid(this.getCardNumber(), lastName.getCardNumber());
    }
    @Override
    public String toString() {
        return cardNumber;
    }
}
