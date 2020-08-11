package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:24
 */
public class AlreadyExistsException extends AccountingException {

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
