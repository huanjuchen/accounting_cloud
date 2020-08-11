package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:27
 */
public class BadDeleteException extends AccountingException {

    public BadDeleteException() {
    }

    public BadDeleteException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
