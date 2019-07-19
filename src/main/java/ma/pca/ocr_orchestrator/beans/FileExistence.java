package ma.pca.ocr_orchestrator.beans;

import lombok.Data;
import ma.pca.ocr_orchestrator.config.FolderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
public class FileExistence {

    private HashMap<String, Set<String>> accountValidity;
    private Set<String> supported;

    @Autowired
    public void setFolderProperties(FolderProperties folderProperties) {
        accountValidity = new HashMap<>();
        for (String s : folderProperties.getFileExistence().split("\\|")) {
            String[] elements = s.split(":");
            String key = elements[0];
            Set<String> files = new HashSet<>(Arrays.asList(elements[1].split(";")));
            accountValidity.put(key, files);
        }
        supported = new HashSet<>(Arrays.asList(folderProperties.getSupported().split(";")));
    }
}
