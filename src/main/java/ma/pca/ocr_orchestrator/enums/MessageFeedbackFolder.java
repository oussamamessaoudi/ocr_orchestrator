package ma.pca.ocr_orchestrator.enums;

import lombok.Getter;

public enum MessageFeedbackFolder {

    VALID_FOLDER("Dossier valide"),
    OCR_ERROR("Réponse d'OCR avec erreur: %s"),
    FOLDER_NOT_FOUND("Dossier introuvable / pas un répertoire"),
    MISSING_ELEMENT("Fichiers manquants : %s"),
    IS_NOT_SUPPORTED("Compte n'est pas supporté (%s)"),
    FILE_XXX_NOT_FOUND("Fichier %s non trouvé");

    @Getter
    private String message;

    MessageFeedbackFolder(String message) {
        this.message = message;
    }
}
