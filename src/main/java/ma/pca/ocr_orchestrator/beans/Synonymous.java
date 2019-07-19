package ma.pca.ocr_orchestrator.beans;


import lombok.Data;
import lombok.Getter;
import ma.pca.ocr_orchestrator.config.SynonymousProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
public class Synonymous {
    @Getter
    private final Set<Set<String>> synonymousParent = new HashSet<>();
    private final Set<Set<String>> synonymousAddress = new HashSet<>();
    private SynonymousProperties synonymousProperties;

    @Autowired
    public void setSynonymousProperties(SynonymousProperties synonymousProperties) {
        this.synonymousProperties = synonymousProperties;
        gettingSynonymous(synonymousProperties.getParentNames(), synonymousParent);
        gettingSynonymous(synonymousProperties.getAddress(), synonymousAddress);
    }

    private void gettingSynonymous(String line, Set<Set<String>> synonymousType) {
        String[] synonymousNames = line.split(synonymousProperties.getSplitSynonymous());
        for (String s : synonymousNames) {
            Set<String> synonymous = new HashSet<>();
            String[] names = s.split(synonymousProperties.getSplitWord());
            Collections.addAll(synonymous, names);
            synonymousType.add(synonymous);
        }
    }

}
