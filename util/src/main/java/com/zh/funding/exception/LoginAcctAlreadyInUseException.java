package com.zh.funding.exception;

public class LoginAcctAlreadyInUseException extends RuntimeException{
    //确保版本兼容性
    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyInUseException() {
        super();
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message,cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
