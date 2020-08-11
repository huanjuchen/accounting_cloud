package xyz.huanju.accounting.exception;

import java.io.Serializable;

/**
 * @author HuanJu
 * @date 2020/8/8 23:21
 */
public class AccountingException extends RuntimeException implements Serializable {

    private Integer errorCode;

    private String message;

    public AccountingException() {
    }

    public AccountingException(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
