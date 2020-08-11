package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:29
 */
public class UnAuthException extends AccountingException {
    public UnAuthException() {
    }

    public UnAuthException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
