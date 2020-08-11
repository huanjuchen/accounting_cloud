package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:28
 */
public class NotFoundException extends AccountingException {

    public NotFoundException() {
    }

    public NotFoundException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
