package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.XMLParseException;

public class ArgumentValue {

    private final Type type;
    private final String name;
    private final Object value;
    private final String ref;

    public ArgumentValue(String type, String name, String value, String ref) throws XMLParseException {
        this.type = Type.parseType(type);
        this.name = name;
        this.value = this.type.convertToRealType(value);
        this.ref = ref;
    }

    public Class getType() {
        return type.getClazz();
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getRef() {
        return ref;
    }
}
