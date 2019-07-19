package ma.pca.ocr_orchestrator.ocr;

import lombok.Data;
import lombok.ToString;
import ma.pca.ocr_orchestrator.beans.*;

@Data
@ToString
public class Kyc {
    private AccountNumber accountNumber;
    private FirstName firstName;
    private LastName lastName;
    private CardNumber cardNumber;
    private FatherName fatherName;
    private MotherName motherName;
    private Address address;
    private InterviewDate date;
}
