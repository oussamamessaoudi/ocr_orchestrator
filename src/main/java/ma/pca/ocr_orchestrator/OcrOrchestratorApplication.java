package ma.pca.ocr_orchestrator;

import ma.pca.ocr_orchestrator.beans.ParentsValidation;
import ma.pca.ocr_orchestrator.services.TreatmentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OcrOrchestratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OcrOrchestratorApplication.class, args);
        TreatmentService treatmentService = context.getBean(TreatmentService.class);
        treatmentService.doBatch();
        //System.out.println(new ParentsValidation().isValid(" AMINE EL OUSSAMA SIMO SIMOO", "EL BEN AMINE OUSSAMA SAMIR"));
    }

}
