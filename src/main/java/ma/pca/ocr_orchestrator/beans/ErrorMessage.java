package ma.pca.ocr_orchestrator.beans;

import lombok.Getter;
import lombok.ToString;
import ma.pca.ocr_orchestrator.enums.MessageFeedbackAttribute;

@Getter
@ToString
public class ErrorMessage {


    ErrorMessage(MessageFeedbackAttribute messageFeedbackAttribute, int priority) {
        this.messageFeedbackAttribute = messageFeedbackAttribute;
        this.priority = priority;
    }

    public static final ErrorMessage CLASS_NOT_CASTED = new ErrorMessage(MessageFeedbackAttribute.CLASS_NOT_CASTED, 0);
    public static final ErrorMessage MESSING_VALUE = new ErrorMessage(MessageFeedbackAttribute.MESSING_VALUE, 1);
    public static final ErrorMessage CIN_PROBLEM = new ErrorMessage(MessageFeedbackAttribute.CIN_PROBLEM, 1);
    public static final ErrorMessage UN_CONFORMITY = new ErrorMessage(MessageFeedbackAttribute.UN_CONFORMITY, 2);
    public static final ErrorMessage NUMBER_PROBLEM = new ErrorMessage(MessageFeedbackAttribute.NUMBER_PROBLEM, 4);
    public static final ErrorMessage SPECIMEN_PROBLEM = new ErrorMessage(MessageFeedbackAttribute.SPECIMEN_PROBLEM, 0);

    public static final ErrorMessage ALL_OKAY = new ErrorMessage(MessageFeedbackAttribute.ALL_OKAY, 0);


    private MessageFeedbackAttribute messageFeedbackAttribute;
    private int priority;
}
