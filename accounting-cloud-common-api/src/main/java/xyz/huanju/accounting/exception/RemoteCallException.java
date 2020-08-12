package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/12 0:21
 */
public class RemoteCallException extends AccountingException {

    public RemoteCallException() {
    }

    public RemoteCallException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
