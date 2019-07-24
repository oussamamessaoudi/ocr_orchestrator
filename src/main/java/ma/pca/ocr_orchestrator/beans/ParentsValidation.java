package ma.pca.ocr_orchestrator.beans;


import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParentsValidation extends AddressParentValidation {

    public ResultCompare isValid(String valueKyc, String valueOcr){
        if(valueOcr == null || valueOcr.isEmpty()){
            return ResultCompare.MESSING_VALUE;
        }
        Pattern pattern= Pattern.compile("\\sBENT?\\s");
        Matcher matcher = pattern.matcher(valueKyc);
        if(matcher.find(0)){
            String firstNameParent = valueKyc.substring(0 ,matcher.start()).trim();

            if (containsArray(firstNameParent, valueOcr, 0)) return ResultCompare.ALL_OKAY;
            else return ResultCompare.UN_CONFORMITY;
        }
        else{
            if (containsArray(valueKyc, valueOcr, -1)) return ResultCompare.ALL_OKAY;
        }
        return ResultCompare.CIN_PROBLEM;
    }

    private boolean containsArray(String valueKyc, String valueOcr, int offset) {
        String[] s = valueKyc.split(" ");
        int lengthOfContains = 0 ;
        for (String fragment : s) {
            if (containsWithAbbreviation(valueOcr, fragment, true))
                lengthOfContains++;
        }
        return lengthOfContains >= s.length + offset;
    }

}

