package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class BirthDay  implements Validation {

    private LocalDate localDate;

    @JsonValue
    String toValue(){
        return getLocalDate().toString();
    }

    @JsonCreator
    static BirthDay fromValue(String value){
        try {
            return new BirthDay(LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy MM dd")));
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public ResultCompare isValid(Object birthDay) {
        return ResultCompare.MESSING_VALUE;
    }
}
