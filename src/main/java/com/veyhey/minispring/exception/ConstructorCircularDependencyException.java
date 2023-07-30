package com.veyhey.minispring.exception;

public class ConstructorCircularDependencyException extends Exception{

    private final String ref;

    public String getRef() {
        return ref;
    }

    public ConstructorCircularDependencyException(String ref) {
        this.ref = ref;
    }
}
