package ma.pca.ocr_orchestrator.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecimenReadable {
    private boolean readable;

    public static final SpecimenReadable SPECIMEN_READABLE_VALID = new SpecimenReadable(true);
    public static final SpecimenReadable SPECIMEN_READABLE_NOT_VALID = new SpecimenReadable(false);

    @JsonValue
    String toValue(){
        return isReadable()?"lisible":"non lisible";
    }

    @JsonCreator
    static SpecimenReadable fromValue(String value){
        return value.equals("lisible")?SPECIMEN_READABLE_VALID:SPECIMEN_READABLE_NOT_VALID;
    }
    @Override
    public String toString() {
        return isReadable()?"lisible":"non lisible";
    }

}
