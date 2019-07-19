package ma.pca.ocr_orchestrator.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountNumber {
    private String accountNumber;


    public boolean isValidAccount(String packAccount) {
        if (accountNumber == null) return false;
        return accountNumber.trim().startsWith(packAccount);
    }

    public String getPackType() {
        return accountNumber.trim().substring(0, 5);
    }
}
