package ma.pca.ocr_orchestrator.services;

import lombok.extern.java.Log;
import ma.pca.ocr_orchestrator.beans.FileExistence;
import ma.pca.ocr_orchestrator.beans.ResultCompare;
import ma.pca.ocr_orchestrator.beans.ResultFolder;
import ma.pca.ocr_orchestrator.beans.Validation;
import ma.pca.ocr_orchestrator.config.FileProperties;
import ma.pca.ocr_orchestrator.config.OcrProperties;
import ma.pca.ocr_orchestrator.enums.MessageFeedbackFolder;
import ma.pca.ocr_orchestrator.ocr.Cin;
import ma.pca.ocr_orchestrator.ocr.Kyc;
import ma.pca.ocr_orchestrator.ocr.KycFactory;
import ma.pca.ocr_orchestrator.ocr.OcrResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.String.format;

@Service
@Log
public class TreatmentService {


    private OcrProperties ocrProperties;
    private FileProperties fileProperties;
    private OcrPredictionService ocrPredictionService;
    private ReportService reportService;
    private FileExistence fileExistence;



    public void doBatch() {
        log.info("Start Batch");
        File fullFolder = new File(ocrProperties.getPathFolder());
        Map<String, ResultFolder> resultFolders = new HashMap<>();
        if (fullFolder.isDirectory()) {
            File[] folders = fullFolder.listFiles();
            assert folders != null;
            log.info(String.format("Numbers of folders : %d",folders.length));
            log.info("--------------------------------------------------------");
            int numberOfReport = 1;
            for (int i = 0; i < folders.length; i++) {
                File folder = folders[i];
                String folderName = folder.getName();

                log.info(format("start folder(%s) : %d/%d", folderName, i+1,folders.length));
                long start = System.currentTimeMillis();
                ResultFolder resultFolder = launchComparison(folder);
                log.info(String.format("folder result : %s", resultFolder));
                resultFolders.put(folderName, resultFolder);
                long laps = System.currentTimeMillis() - start;
                log.info(format("end folder(%s) : %d/%d; timeLaps : %d ms",folderName, i+1,folders.length, laps));
                log.info("--------------------------------------------------------");
                if((i+1) % ocrProperties.getNumberOfFolderByRapport() == 0){
                    reportService.reporting(resultFolders, String.format(ocrProperties.getPathRapport(), numberOfReport++));
                    resultFolders = new HashMap<>();
                }
            }
            if(!resultFolders.isEmpty())
                reportService.reporting(resultFolders, String.format(ocrProperties.getPathRapport(), numberOfReport));

        }

    }


    public ResultFolder launchComparison(File folder) {
        ResultFolder resultFolder = new ResultFolder();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            assert files != null;
            Optional<File> kycFile = Arrays.stream(files).filter(f -> fileProperties.getKyc().equals(f.getName())).findFirst();
            if (kycFile.isPresent()) {
                Kyc kyc;
                try {
                    kyc = KycFactory.parseKycFromPdf(kycFile.get());
                    log.info(String.format("Kyc : %s", kyc.toString()));
                    String idSupported = null;
                    for (String s : fileExistence.getSupported()) {
                        if (kyc.getAccountNumber().isValidAccount(s)) {
                            idSupported = s;
                            break;
                        }
                    }
                    if(idSupported != null){
                        Set<File> filesNotExists = new HashSet<>();

                        fileExistence.getAccountValidity().get(idSupported).forEach(s -> {
                           File fileTemp = new File(String.format("%s/%s",folder.getAbsoluteFile(), s));
                           if(!fileTemp.exists()) filesNotExists.add(fileTemp);
                        });
                        if(filesNotExists.isEmpty()){
                            OcrResponse ocrResponse = ocrPredictionService.getDataFromCin(ocrProperties.getApi(), folder.getAbsolutePath());
                            log.info(String.format("Ocr : %s", ocrResponse.toString()));
                            if (ocrResponse.getCode().isValid()) {
                                Map<String, ResultCompare> resultCompare = compare(kyc, ocrResponse.getPrediction());
                                if(!ocrResponse.getSpecimen_signature().isExists()){
                                    resultCompare.put("SpecimenSignature", ResultCompare.SPECIMEN_SIGNATURE_PROBLEM);
                                }
                                if(!ocrResponse.getSpecimen_lisibility().isReadable()){
                                    resultCompare.put("SpecimenReadability", ResultCompare.SPECIMEN_PROBLEM);
                                }
                                if (resultCompare.isEmpty()) {
                                    resultFolder.setValid(true);
                                    resultFolder.setMessage(MessageFeedbackFolder.VALID_FOLDER.getMessage());
                                } else {
                                    resultFolder.setValid(false);
                                    resultFolder.setResultCompare(resultCompare);
                                }
                                return resultFolder;
                            } else {
                                resultFolder.setValid(false);
                                resultFolder.setMessage(format(MessageFeedbackFolder.OCR_ERROR.getMessage(), ocrResponse.getError()));
                                return resultFolder;
                            }
                        }
                        else {
                            resultFolder.setValid(false);
                            resultFolder.setMessage(String.format(MessageFeedbackFolder.MISSING_ELEMENT.getMessage(), filesNotExists.stream().map(File::getName).toArray()));
                            return resultFolder;
                        }

                    }
                    else{
                        resultFolder.setValid(false);
                        resultFolder.setMessage(String.format(MessageFeedbackFolder.IS_NOT_SUPPORTED.getMessage(), kyc.getAccountNumber().getPackType()));
                        return resultFolder;
                    }

                } catch (Exception e) {
                    resultFolder.setValid(false);
                    resultFolder.setMessage(e.getClass().getName());
                    return resultFolder;
                }

            } else {
                resultFolder.setValid(false);
                resultFolder.setMessage(String.format(MessageFeedbackFolder.FILE_XXX_NOT_FOUND.getMessage(), "KYC"));
                return resultFolder;
            }
        } else {
            resultFolder.setValid(false);
            resultFolder.setMessage(MessageFeedbackFolder.FOLDER_NOT_FOUND.getMessage());
            return resultFolder;
        }
    }


    private Map<String, ResultCompare> compare(Kyc kyc, Cin cin) {
        Map<String, ResultCompare> resultCompareAttributes = new HashMap<>();

        KycFactory.PARSING_HELPER.values().forEach(fieldProps -> {
            ResultCompare resultCompare = compare(kyc, cin, fieldProps.getName());
            if (resultCompare != null)
                resultCompareAttributes.put(fieldProps.getName(), resultCompare);
        });

        return resultCompareAttributes;
    }

    public ResultCompare compare(Kyc kyc, Cin cin, String attributeName) {
        String formatCin = format("get%sSet", attributeName);
        String formatKyc = format("get%s", attributeName);
        try {
            Method methodCin = cin.getClass().getMethod(formatCin);
            Method methodKyc = kyc.getClass().getMethod(formatKyc);
            Validation kycData = (Validation) methodKyc.invoke(kyc);
            ResultCompare resultCompare = new ResultCompare();
            Set<Validation> ocrData = (Set<Validation>) methodCin.invoke(cin);
            boolean anyMatch = ocrData.stream().anyMatch(validation -> {
                try {
                    ResultCompare valid = kycData.isValid(validation);
                    if (resultCompare.getMessage() == null ||
                            (!valid.isValid() && valid.getMessage().getPriority() > resultCompare.getMessage().getPriority())
                    ) {
                        resultCompare.setValid(valid.isValid());
                        resultCompare.setMessage(valid.getMessage());
                    }
                    return valid.isValid();
                } catch (Exception e) {
                    return false;
                }
            });

            if (!anyMatch) {
                resultCompare.setKycData(kycData);
                resultCompare.setOcrData(ocrData);
                return resultCompare;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    @Autowired
    public void setOcrPredictionService(OcrPredictionService ocrPredictionService) {
        this.ocrPredictionService = ocrPredictionService;
    }

    @Autowired
    public void setOcrProperties(OcrProperties ocrProperties) {
        this.ocrProperties = ocrProperties;
    }

    @Autowired
    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }
    @Autowired
    public void setFileExistence(FileExistence fileExistence) {
        this.fileExistence = fileExistence;
    }
}
