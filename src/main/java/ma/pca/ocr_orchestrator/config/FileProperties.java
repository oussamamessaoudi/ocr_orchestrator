package ma.pca.ocr_orchestrator.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "files.name")
public class FileProperties {
    private String kyc;
    private String cin;
    private String convention;
    private String photo;
    private String signature;
    private String deposit;

}

