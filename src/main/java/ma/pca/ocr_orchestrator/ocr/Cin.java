package ma.pca.ocr_orchestrator.ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ma.pca.ocr_orchestrator.beans.*;


import java.util.Set;

@Data
public class Cin {
    @JsonProperty("Prenom")
    private Set<FirstName> firstNameSet;
    @JsonProperty("Nom")
    private Set<LastName> lastNameSet;
    @JsonProperty("CIN")
    private Set<CardNumber> cardNumberSet;
    @JsonProperty("Date de naissance")
    private Set<BirthDay> birthDaySet;
    @JsonProperty("Nom Pere")
    private Set<FatherName> fatherNameSet;
    @JsonProperty("Nom Mere")
    private Set<MotherName> motherNameSet;
    @JsonProperty("Date expiration")
    private Set<ExpirationDate> expirationDateSet;
    @JsonProperty("Adresse")
    private Set<Address> addressSet;
}
