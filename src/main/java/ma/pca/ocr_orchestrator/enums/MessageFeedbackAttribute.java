package ma.pca.ocr_orchestrator.enums;

import lombok.Getter;

public enum MessageFeedbackAttribute {

    CLASS_NOT_CASTED("Cannot Cast Class"),
    MESSING_VALUE("Valeur manquante"),
    UN_CONFORMITY("Discordance"),
    CIN_PROBLEM("CIN problème"),
    NUMBER_PROBLEM("Problème de nombre"),
    SPECIMEN_PROBLEM("SPECIMEN non lisible"),
    SPECIMEN_SIGNATURE_PROBLEM("SPECIMEN signature problème"),
    ALL_OKAY("ALL OKAY");


    @Getter
    private String message;

    MessageFeedbackAttribute(String message) {
        this.message = message;
    }
}
