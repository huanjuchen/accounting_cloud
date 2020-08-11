package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:24
 */
public class BadCreateException extends AccountingException {

    public BadCreateException() {
    }

    public BadCreateException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
