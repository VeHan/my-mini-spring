package com.veyhey.minispring.exception;

public class BeanException extends Exception {

    public BeanException(String message) {
        super(message);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BeanException wrapper(Exception e){
        if (e instanceof BeanException) {
            return (BeanException) e;
        }
        return new BeanException(e);
    }

}
