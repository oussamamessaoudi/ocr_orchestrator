package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecimenSignature {
    private final boolean exists;

    public static final SpecimenSignature SPECIMEN_SIGNATURE_VALID = new SpecimenSignature(true);
    public static final SpecimenSignature SPECIMEN_SIGNATURE_NOT_VALID = new SpecimenSignature(false);

    @JsonValue
    String toValue(){
        return isExists()?"oui":"non";
    }

    @JsonCreator
    static SpecimenSignature fromValue(String value){
        return value.equals("Oui")?SPECIMEN_SIGNATURE_VALID:SPECIMEN_SIGNATURE_NOT_VALID;
    }

    @Override
    public String toString() {
        return isExists()?"Oui":"Non";
    }
}
