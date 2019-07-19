package ma.pca.ocr_orchestrator.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ocr.url")
public class OcrProperties {
        private String api;
        private String pathFolder;
        private String pathRapport;
        private int numberOfFolderByRapport;

}

