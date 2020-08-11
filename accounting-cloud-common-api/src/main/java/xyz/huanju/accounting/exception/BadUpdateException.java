package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:28
 */
public class BadUpdateException extends AccountingException {

    public BadUpdateException() {
    }

    public BadUpdateException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
