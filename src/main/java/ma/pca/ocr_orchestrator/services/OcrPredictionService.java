package ma.pca.ocr_orchestrator.services;

import ma.pca.ocr_orchestrator.ocr.OcrInput;
import ma.pca.ocr_orchestrator.ocr.OcrResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OcrPredictionService {
    public OcrResponse getDataFromCin(String api, String folder){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OcrResponse> response = restTemplate.postForEntity(api,new OcrInput(folder), OcrResponse.class);
        return response.getBody();
    }
}
