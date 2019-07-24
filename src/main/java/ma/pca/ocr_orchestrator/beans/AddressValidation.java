package ma.pca.ocr_orchestrator.beans;


public class AddressValidation extends AddressParentValidation {
    public ResultCompare isValid(String[] valuesKyc, String valueOcr){
        int totalFragment = 0;
        int validFragment = 0;
        if(valueOcr == null || valueOcr.isEmpty()){
            return ResultCompare.MESSING_VALUE;
        }
        for (String s : valuesKyc) {
            if(s.matches("\\d+")){
                if(!valueOcr.contains(s)){
                    return ResultCompare.NUMBER_PROBLEM;
                }
            }
            else {
                if(containsWithAbbreviation(valueOcr, s, false)){
                    validFragment++;
                }
                totalFragment++;
            }
        }
        if((double)validFragment/(double) totalFragment < 0.8 )
            return ResultCompare.UN_CONFORMITY;
        return ResultCompare.ALL_OKAY;
    }

}
