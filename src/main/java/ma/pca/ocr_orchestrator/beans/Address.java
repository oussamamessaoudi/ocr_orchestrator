package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@AllArgsConstructor
@ToString
public class Address extends AddressValidation implements Validation {
    private String address;

    @JsonValue
    String toValue(){
        return getAddress();
    }

    @JsonCreator
    static Address fromValue(String value){
        return new Address(value);
    }

    @Override
    public ResultCompare isValid(Object object) {
        if (!(object instanceof Address)) {
            return ResultCompare.CLASS_NOT_CASTED;
        }
        Address address = (Address) object;

        return super.isValid(this.getAddress().split(" "), address.getAddress());
    }
    @Override
    public String toString() {
        return address;
    }
}
