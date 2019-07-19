package ma.pca.ocr_orchestrator.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringEqualsValidation {

    public ResultCompare isValid(String element, String compareTo){
        if(compareTo == null || compareTo.isEmpty()){
            return ResultCompare.MESSING_VALUE;
        }
        if(element.trim().equalsIgnoreCase(compareTo.trim())){
            return ResultCompare.ALL_OKAY;
        }
        else{
            return ResultCompare.UN_CONFORMITY;
        }
    }
}

