package ma.pca.ocr_orchestrator.services;

import ma.pca.ocr_orchestrator.beans.ResultCompare;
import ma.pca.ocr_orchestrator.ocr.Cin;
import ma.pca.ocr_orchestrator.ocr.Kyc;
import ma.pca.ocr_orchestrator.ocr.KycFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class TreatmentServiceTest {
    @Autowired
    TreatmentService treatmentService;

    @Test
    public void parseKyc() throws Exception {
        KycFactory.parseKycFromPdf(new File("C:/Users/u958287/Desktop/contrat_KYC.pdf.pdf"));
    }

    @Test
    public void doBatch() {
        new OcrPredictionService().getDataFromCin("http://10.29.10.21:8000/Ocr/", "C:\\Users\\ChaabiFirstRobot\\Desktop\\TestOcr\\4d0f424380084a5c");
    }

    @Test
    public void compare() throws Exception {
        Kyc kyc = KycFactory.parseKycFromPdf(new File("C:/Users/u958287/Desktop/contrat_KYC.pdf.pdf"));
        Cin cin = new OcrPredictionService().getDataFromCin("http://10.29.10.21:8000/Ocr/", "C:\\Users\\ChaabiFirstRobot\\Desktop\\TestOcr\\4d0f424380084a5c").getPrediction();


        ResultCompare resultCompare = new TreatmentService().compare(kyc, cin, "FirstName");

    }

    @Test
    public void reporting() {

        new TreatmentService().doBatch();
    }
}