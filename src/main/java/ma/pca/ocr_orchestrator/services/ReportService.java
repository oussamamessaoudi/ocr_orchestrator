package ma.pca.ocr_orchestrator.services;


import lombok.extern.java.Log;
import ma.pca.ocr_orchestrator.beans.ResultFolder;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
@Log
public class ReportService {

    public void reporting(Map<String, ResultFolder> resultFolders, String pathWhereToWrite) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report OCR");
        AtomicInteger rowNum = new AtomicInteger(0);

        resultFolders.forEach((s, resultFolder) -> {
            if (resultFolder.getResultCompare() != null) {
                IntStream.range(rowNum.get(), rowNum.get() + resultFolder.getResultCompare().size()).forEach(sheet::createRow);
                rowNum.addAndGet(resultFolder.getResultCompare().size());
            } else {
                sheet.createRow(rowNum.getAndIncrement());
            }
        });
        sheet.createRow(rowNum.get());

        sheet.getRow(0).createCell(0).setCellValue("Dossier");
        sheet.getRow(0).createCell(1).setCellValue("Validité");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
        sheet.getRow(0).createCell(2).setCellValue("FeedBack");
        sheet.getRow(0).createCell(4).setCellValue("Kyc");
        sheet.getRow(0).createCell(5).setCellValue("Ocr");
        rowNum.set(1);
        resultFolders.forEach((folderName, resultFolder) -> {

            sheet.getRow(rowNum.get()).createCell(0).setCellValue(folderName);
            sheet.getRow(rowNum.get()).createCell(1).setCellValue(resultFolder.isValid());
            if (resultFolder.getResultCompare() != null && resultFolder.getResultCompare().size() != 0) {
                sheet.addMergedRegion(new CellRangeAddress(rowNum.get(), rowNum.get() + resultFolder.getResultCompare().size() - 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(rowNum.get(), rowNum.get() + resultFolder.getResultCompare().size() - 1, 1, 1));
                resultFolder.getResultCompare().forEach((attribute, resultCompare) -> {
                    sheet.getRow(rowNum.get()).createCell(2).setCellValue(attribute);
                    sheet.getRow(rowNum.get()).createCell(3).setCellValue(resultCompare.getMessage().getMessageFeedbackAttribute().getMessage());
                    sheet.getRow(rowNum.get()).createCell(4).setCellValue(resultCompare.getKycData().toString());
                    sheet.getRow(rowNum.get()).createCell(5).setCellValue(resultCompare.getOcrData().toString());
                    rowNum.incrementAndGet();
                });
            } else {
                sheet.addMergedRegion(new CellRangeAddress(rowNum.get(), rowNum.get(), 2, 5));
                sheet.getRow(rowNum.getAndIncrement()).createCell(2).setCellValue(resultFolder.getMessage());

            }
        });
        FileOutputStream out;
        try {
            File file = new File(pathWhereToWrite);
            if (file.exists())
                file.delete();

            if (file.createNewFile()) {
                out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
                log.info("Reporting");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
