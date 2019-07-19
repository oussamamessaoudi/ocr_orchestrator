package ma.pca.ocr_orchestrator.ocr;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import ma.pca.ocr_orchestrator.beans.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class KycFactory {

    public static final HashMap<String, FieldProps> PARSING_HELPER;
    private static final HashMap<String, FieldProps> PARSING_HELPER_ALL_SIMILITUDE;

    static{
        PARSING_HELPER = new HashMap<>();
        PARSING_HELPER.put("N° pièce d'identité:", new FieldProps("CardNumber", CardNumber.class));
        PARSING_HELPER.put("Nom:", new FieldProps("LastName", LastName.class));
        PARSING_HELPER.put("Prénom:", new FieldProps("FirstName", FirstName.class));
        PARSING_HELPER.put("Nom du père:", new FieldProps("FatherName", FatherName.class));
        PARSING_HELPER.put("Nom de la mère:", new FieldProps("MotherName", MotherName.class));
        PARSING_HELPER.put("Adresse personnelle: Rue:", new FieldProps("Address", Address.class));
    }
    static {
        PARSING_HELPER_ALL_SIMILITUDE = new HashMap<>(PARSING_HELPER);
        PARSING_HELPER_ALL_SIMILITUDE.put("N° Compte:", new FieldProps("AccountNumber", AccountNumber.class));
    }


    public static Kyc parseKycFromPdf(File file) throws Exception {
        Kyc kyc;

        try {
            PdfReader pdfReader = new PdfReader(file.getAbsolutePath());
            PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
            SimpleTextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            StringBuilder pdfText = new StringBuilder();

            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                TextExtractionStrategy textExtractionStrategy = parser.processContent(i, strategy);
                pdfText.append(textExtractionStrategy.getResultantText());
            }
            String[] lines = pdfText.toString().split("\\n");
            pdfReader.close();
            Class<?> kycClass = Class.forName("ma.pca.ocr_orchestrator.ocr.Kyc");

            kyc = (Kyc) kycClass.getConstructor().newInstance();
            for (String line : lines) {
                for (String kycDocKey : PARSING_HELPER_ALL_SIMILITUDE.keySet()){
                    if (line.startsWith(kycDocKey)) {
                        FieldProps field = PARSING_HELPER_ALL_SIMILITUDE.get(kycDocKey);
                        Method method = kycClass.getMethod(String.format("set%s", field.getName()), field.getClassName());
                        Object newInstance = field.getClassName().getConstructor(String.class).newInstance(line.substring(kycDocKey.length()));
                        method.invoke(kyc, newInstance);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error Parsing Kyc FILE");
        }

        return kyc;
    }

}
