package xyz.huanju.accounting.exception;

/**
 * @author HuanJu
 * @date 2020/8/8 23:31
 */
public class RedisConnectException extends AccountingException {

    public RedisConnectException() {
    }

    public RedisConnectException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
