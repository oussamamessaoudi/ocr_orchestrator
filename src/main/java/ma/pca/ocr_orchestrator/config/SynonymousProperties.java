package ma.pca.ocr_orchestrator.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "synonymous")
public class SynonymousProperties {
    private String parentNames;
    private String address;
    private String splitWord;
    private String splitSynonymous;
}
