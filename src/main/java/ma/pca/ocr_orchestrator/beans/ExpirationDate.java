package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ExpirationDate implements Validation {
    private LocalDate expirationDate;


    @JsonValue
    String toValue(){
        return getExpirationDate().toString();
    }

    @JsonCreator
    static ExpirationDate fromValue(String value){
        try {
            return new ExpirationDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy MM dd")));
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public ResultCompare isValid(Object expirationDate) {
        return ResultCompare.MESSING_VALUE;
    }
}
