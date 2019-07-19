package ma.pca.ocr_orchestrator.beans;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParentsValidation extends AddressParentValidation {

    public ResultCompare isValid(String valueKyc, String valueOcr){
        if(valueOcr == null || valueOcr.isEmpty()){
            return ResultCompare.MESSING_VALUE;
        }
        Pattern pattern= Pattern.compile("\\sBENT?\\s");
        Matcher matcher = pattern.matcher(valueOcr);
        if(matcher.find(0)){
            String firstNameParent = valueOcr.substring(0 ,matcher.start()).trim();
            if(containsWithAbbreviation(valueKyc, firstNameParent, true)){
                return ResultCompare.ALL_OKAY;
            }
            else{
                return ResultCompare.UN_CONFORMITY;
            }
        }
        return ResultCompare.CIN_PROBLEM;
    }

}

