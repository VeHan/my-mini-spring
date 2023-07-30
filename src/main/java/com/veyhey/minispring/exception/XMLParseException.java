package com.veyhey.minispring.exception;

public class XMLParseException extends Exception{

    public XMLParseException(String message) {
        super(message);
    }

    public XMLParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLParseException(Throwable cause) {
        super(cause);
    }
}
