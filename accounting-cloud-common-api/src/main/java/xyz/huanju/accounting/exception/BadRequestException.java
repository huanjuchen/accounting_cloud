package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:27
 */
public class BadRequestException extends AccountingException {
    public BadRequestException() {
    }

    public BadRequestException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
