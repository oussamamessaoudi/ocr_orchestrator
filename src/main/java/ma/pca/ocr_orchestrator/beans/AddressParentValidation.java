package ma.pca.ocr_orchestrator.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AddressParentValidation {
    private static Synonymous allSynonymous;

    @Autowired
    public void setAllSynonymous(Synonymous allSynonymous) {
        AddressParentValidation.allSynonymous = allSynonymous;
    }
    boolean containsWithAbbreviation(String big, String small, boolean isParent){
        Set<Set<String>> synonymousType= isParent ? allSynonymous.getSynonymousParent():allSynonymous.getSynonymousAddress();
        Optional<Set<String>> synonymous = synonymousType.stream().filter(names -> names.contains(small)).findFirst();
        return synonymous.map(strings -> strings.stream().anyMatch(big::contains)).orElseGet(() -> big.contains(small));
    }
}
